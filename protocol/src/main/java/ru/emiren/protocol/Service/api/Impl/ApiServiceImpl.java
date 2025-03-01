package ru.emiren.protocol.Service.api.Impl;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.model.FileHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.protocol.Controller.ProtocolController;
import ru.emiren.protocol.DTO.Temporal.FileHolder;
import ru.emiren.protocol.Service.Deserialization.DeserializationService;
import ru.emiren.protocol.Service.File.FileService;
import ru.emiren.protocol.Service.Word.WordService;
import ru.emiren.protocol.Service.api.ApiService;
import ru.emiren.protocol.Service.Download.DownloadService;

import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {
    private final DownloadService downloadService;
    private final WordService wordService;
    private final RestTemplate restTemplate;

    @Getter
    @AllArgsConstructor
    public enum FileData {
        FQW("fqw"),
        GOV("gos"),
        REV("rev"),
        COM("com"),
        PRO("pro");
        private final String data;
    }

    @Getter
    @AllArgsConstructor
    public enum FileTypes {
        Excel(".xlsx"),
        Word(".doc"),
        PDF(".pdf"),
        DOCX(".docx");
        private final String extension;
    }


    private final Map<String, Map<String, List<FileHeader>>> fileMap = initFileMap();

    private final DateFormat dateFormat;

    List<List<String>> data;

    FileHolder fileHolder = new FileHolder();

    @Autowired
    ApiServiceImpl(
            FileService fileService,
            DownloadService downloadService,
            WordService wordService,
            DateFormat dateFormat,
            ResourceLoader resourceLoader, RestTemplate restTemplate){
        this.downloadService = downloadService;
        this.wordService = wordService;

        this.dateFormat = dateFormat;
        this.restTemplate = restTemplate;
    }

    /**
     * Don't mind it
     * @return a dictionary with file extensions
     */
    private Map<String, Map<String, List<FileHeader>>> initFileMap() {
        Map<String, Map<String, List<FileHeader>>> map = new HashMap<>();
        Arrays.stream(FileData.values()).forEach(fileData -> {
            Map<String, List<FileHeader>> typeMap = new HashMap<>();
            if (fileData == FileData.PRO) {
                typeMap.put(FileTypes.PDF.getExtension(), new ArrayList<>());
            } else {
                typeMap.put(FileTypes.Word.getExtension(), new ArrayList<>());
                typeMap.put(FileTypes.Excel.getExtension(), new ArrayList<>());
            }
            map.put(fileData.getData(), typeMap);
        });
        return map;
    }

    /**
     * Generates and send a protocol file to the client
     *
     * @param response a client's Servlet
     * @return a redirect to the function page
     */
    @Override
    public String downloadProtocols(HttpServletResponse response)  {
        downloadService.generateAndSendFile(data, response);
        return "redirect:/functions";
    }

    /**
     * Handles the upload of a file and processes it.
     *
     * @param file a decree to process
     * @param fileId hashed file to simple string as ID
     * @return a ResponseEntity containing the status of the upload.
     */
    @Override
    public ResponseEntity<String> handleFileUpload(MultipartFile file, String fileId) {
        log.info("{}", file.getName());
        Map<String, String> headers = new HashMap<>();
        log.info("Received file upload with ID: {}", fileId);

        if (!fileHolder.containsDocument(fileId)) {
            try (InputStream is = file.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                log.info("Processing file: {}", file.getOriginalFilename());

                NiceXWPFDocument processedDocument = wordService.generateWordDocument(wordService.getListOfDataFromFile(is));

                log.info("Document generated successfully for file ID: {}", fileId);
                processedDocument.write(baos);
                byte[] docBytes = baos.toByteArray();

                fileHolder.storeDocument(fileId, docBytes);

                headers.put("status", "200");
                headers.put("id", fileId);
                PoitlIOUtils.closeQuietly(processedDocument);

                return ResponseEntity.status(HttpStatus.OK).body(headers.toString());
            } catch (IOException ex) {
                log.error("Error processing file upload: {}", "Something went wrong");
                headers.put("status", "500");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(headers.toString());
            }
        } else {
            headers.put("status", "200");
            headers.put("id", fileId);
            return ResponseEntity.status(HttpStatus.OK).body(headers.toString());
        }
    }

    /**
     * Check if file is ready by id
     *
     * @param id hashed file as ID
     * @return a ResponseEntity with a status of readiness
     */
    @Override
    public ResponseEntity<String> checkFileAvailability(String id) {
        log.info(id);
        if (fileHolder.containsDocument(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("200");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not Found");
        }
    }

    /**
     * On call returns a download process of file
     *
     * @param id
     * @param response
     * @return a ResponseEntity with status
     */
    @Override
    public ResponseEntity<String> downloadFile(String id, HttpServletResponse response) {
        log.info("DownloadWordFile is activated with id: {}", id );
        Date date = new Date();
        if (fileHolder.containsDocument(id)) {
            byte[] document = fileHolder.getDocument(id);
            log.info(document.toString());

            response.setContentType("application/octet-stream");
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                    ContentDisposition.builder("attachment")
                            .filename("protocols-" + dateFormat.format(date) + ".docx")
                            .build().toString());
            try (OutputStream os = response.getOutputStream();
                 BufferedOutputStream bos = new BufferedOutputStream(os)
            ) {
                log.info("In OutputStream for {}", id);
                bos.write(document);
                log.info("Out OutputStream");
                return ResponseEntity.ok("200");
            } catch (IOException e) {
                log.warn(e.getMessage());
                response.setStatus(HttpStatus.BAD_REQUEST.value());
            } finally {
                try {
                    response.flushBuffer();
                } catch (IOException e) {
                    log.warn(e.getMessage());
                }
            }
        }
        return ResponseEntity.badRequest().build();
    }

    private void parseDataFromWordToSqlDatabase(List<List<String>> data) {
        // I am not sure about liquidity of data
        for (List<String> row : data) {
            int Num = Integer.parseInt(row.get(0));
//            String nameInRod = row.get(1);
            long studNum = Long.parseLong(row.get(2));
            String theme = row.get(3);
            String jobAndPost = row.get(4);
            String SupervisionsName = row.get(5);
            String consRP = row.get(6); //I don't know what it is
            String Reviewer = row.get(7); //Рец-нт?
            String reviewersJobAndPost = row.get(8);
            String C = row.get(9);
            // second Num = 10
            String questioner1 = row.get(11);
            String question1 = row.get(12);
            String questioner2 = row.get(13);
            String question2 = row.get(14);
            String questioner3 = row.get(15);
            String question3 = row.get(16);
            // third num = 17
            int score = Integer.parseInt(row.get(18)); // Оценка
            String name = row.get(19); // ФИО студента без род.пад
            String SpecialOpinions = row.get(20);
            // Score Rus + Euro Score = 21
            // Start Time of FQW = 22
            // Length of FQW in time = 23
            String language = row.get(24);




        }
    }
}
