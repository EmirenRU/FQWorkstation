package ru.emiren.protocol.Service.api.Impl;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import ru.emiren.protocol.DTO.Temporal.FileHolder;
import ru.emiren.protocol.Service.Word.WordService;
import ru.emiren.protocol.Service.api.ApiService;
import ru.emiren.protocol.Service.Download.DownloadService;

import java.io.*;
import java.text.DateFormat;
import java.util.*;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {
    private final DownloadService downloadService;
    private final WordService wordService;

    private final DateFormat dateFormat;
    private ClassPathResource classPathResource;

    private List<List<String>> data;

    private FileHolder fileHolder = new FileHolder();
    private static byte[] bytes;

    @Autowired
    ApiServiceImpl(
            @Qualifier("defaultTemplateResource") ClassPathResource loader,
            DownloadService downloadService,
            WordService wordService,
            DateFormat dateFormat,
            RestTemplate restTemplate){
        this.downloadService = downloadService;
        this.wordService = wordService;

        this.dateFormat = dateFormat;
        this.classPathResource = loader;

        try (InputStream is = classPathResource.getInputStream();
        ByteArrayOutputStream tempBaos = new ByteArrayOutputStream()){
            FileCopyUtils.copy(is, tempBaos);
            log.info("Successful copied into baos");
            bytes = tempBaos.toByteArray();
        } catch (IOException e) {

            log.warn(e.getMessage());
        }
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
        return handleFileUploadInternal(file, null, fileId);
    }

    @Override
    public ResponseEntity<String> handleFileUploadWithTemplate(MultipartFile file, MultipartFile template, String fileId) {
        return handleFileUploadInternal(file, template, fileId);
    }


    /**
     * A handler for downloading template file
     * @param hashId a hash of file
     * @param response HttpServletResponse entity to send data to a client
     * @return a ResponseEntity that contains header and template in body to a client
     */
    @Override
    public ResponseEntity<?> downloadTemplate(String hashId, HttpServletResponse response) {
        if (fileHolder.containsDocument(hashId)) {
            if (fileHolder.getTemplate(hashId) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(fileHolder.getTemplate(hashId));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(bytes);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(hashId);
    }

    /**
     * Check if file is ready by id
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

    private ResponseEntity<String> handleFileUploadInternal(MultipartFile file, MultipartFile template, String fileId) {
        log.info("{}", file.getName());
        Map<String, String> headers = new HashMap<>();
        log.info("Received file upload with ID: {}", fileId);

        if (!fileHolder.containsDocument(fileId)) {
            try (InputStream is = file.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {

                log.info("Processing file: {}", file.getOriginalFilename());

                NiceXWPFDocument processedDocument;
                if (template != null) {
                    File templateFile = new File(System.getProperty("java.io.tmpdir"), template.getOriginalFilename());
                    processedDocument = wordService.generateWordDocument(wordService.getListOfDataFromFile(is), templateFile);
                } else {
                    processedDocument = wordService.generateWordDocument(wordService.getListOfDataFromFile(is));
                }

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
