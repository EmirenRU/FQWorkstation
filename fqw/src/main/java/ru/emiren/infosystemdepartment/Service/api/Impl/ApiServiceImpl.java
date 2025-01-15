package ru.emiren.infosystemdepartment.Service.api.Impl;

import com.deepoove.poi.util.PoitlIOUtils;
import com.deepoove.poi.xwpf.NiceXWPFDocument;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import net.lingala.zip4j.model.FileHeader;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.infosystemdepartment.Controller.Protocol.FunctionsController;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.Temporal.FileHolder;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Service.Deserialization.DeserializationService;
import ru.emiren.infosystemdepartment.Service.Download.DownloadService;
import ru.emiren.infosystemdepartment.Service.File.FileService;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;
import ru.emiren.infosystemdepartment.Service.api.ApiService;

import java.io.*;
import java.text.DateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class ApiServiceImpl implements ApiService {

    @Override
    public String downloadProtocols(HttpServletResponse response) throws IOException {
        downloadService.generateAndSendFile(data, response);
        return "redirect:/functions";
    }

    @Override
    public ResponseEntity<?> handleFileUpload(MultipartFile file, String fileId) {
        log.info("{}", file.getName());
        Map<String, String> headers = new HashMap<>();
        log.info("Received file upload with ID: {}", fileId);

        if (!fileHolder.containsDocument(fileId)) {
            try (InputStream is = file.getInputStream();
                 ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
                log.info("Processing file: {}", file.getOriginalFilename());

                List<List<String>> temp_data = wordService.getListOfDataFromFile(is);
                NiceXWPFDocument processedDocument = wordService.generateWordDocument(temp_data);

                log.info("Document generated successfully for file ID: {}", fileId);
                processedDocument.write(baos);
                byte[] docBytes = baos.toByteArray();

                fileHolder.storeDocument(fileId, docBytes);

                headers.put("status", "200");
                headers.put("id", fileId);
                PoitlIOUtils.closeQuietly(processedDocument);

                return ResponseEntity.ok(headers);
            } catch (IOException ex) {
                log.error("Error processing file upload: {}", ex.getMessage());
                headers.put("status", "500");
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(headers);
            }
        } else {
            headers.put("status", "200");
            headers.put("id", fileId);
            return ResponseEntity.status(HttpStatus.OK).body(headers);
        }
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<?>> handleDataUpload(String message) {
        Map<String, String> headers = new HashMap<>();
        headers.put("message", message);
        log.info("Message received: {}", message);

        // TODO Write more safe query

        try {
            if (message.toUpperCase().contains("SELECT")){
                headers.put("result", String.valueOf(jdbcTemplate.queryForList(message)));
            } else if (message.toUpperCase().contains("INSERT")){
                headers.put("result", "INSERT");
            } else if (message.toUpperCase().contains("UPDATE")){
                headers.put("result", "UPDATE");
            } else if (message.toUpperCase().contains("DELETE")){
                headers.put("result", "DELETE");
            } else {
                headers.put("result", "ERROR");
            }
        }  catch (BadSqlGrammarException e) {
            log.error("SQL Syntax Error: " + e.getMessage());
            headers.put("error", e.getMessage());
        } catch (DataAccessException e) {
            log.error("Database Access Error: " + e.getMessage());
            headers.put("error", e.getMessage());
        } catch (Exception e) {
            log.error("Unexpected Error: " + e.getMessage());
            headers.put("error", e.getMessage());
        }
        if (headers.containsKey("error")) {
            headers.put("status", "500");
            return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.BAD_REQUEST).body(headers));
        }
        headers.put("status", "200");
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(headers));
    }

    @Override
    public ResponseEntity<?> checkFileAvailability(String id) {
        log.info(id);
        if (fileHolder.containsDocument(id)) {
            return ResponseEntity.status(HttpStatus.OK).body("200");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not Found");
        }
    }

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
                 BufferedOutputStream bos = new BufferedOutputStream(os);
            ) {
                log.info("In OutputStream for {}", id);
//                document.write(bos);
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

    @Override
    public String generateAndSendFile(HttpServletResponse response) throws IOException {
        return "";
    }

    @Override
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request) {
        MultipartFile file = request.getFile("data");

        return ResponseEntity.status(HttpStatus.OK).body("Proceed");
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<?>> receiveLecturers(HttpServletRequest request) {
        List<StudentLecturersDTO> res = studentLecturersService.getAllStudentLecturers();
        Map<String, String> headers = new HashMap<>();
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).header(headers.toString()).body(res));
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<?>> receiveThemes(HttpServletRequest request) {
        List<FQWDTO> res = fqwService.getAllFQW();
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(res));
    }

    public enum FileData {
        FQW("fqw"),
        GOV("gos"),
        REV("rev"),
        COM("com"),
        PRO("pro");

        private final String data;

        FileData(String data) {
            this.data = data;
        }
        public String getData() {
            return data;
        }
    }

    public enum FileTypes {
        Excel(".xlsx"),
        Word(".doc"),
        PDF(".pdf"),
        DOCX(".docx");

        private final String extension;

        FileTypes(String extension) {
            this.extension = extension;
        }

        public String getExtension() {
            return extension;
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
            String consRP = row.get(6); //idk what it is
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

    private final LecturerRepository lecturerRepository;
    private final StudentService studentService;
    private final DepartmentService departmentService;
    private final LecturerService lecturerService;
    private final OrientationService orientationService;
    private final ProtectionService protectionService;
    private final StudentLecturersService studentLecturersService;
    private final FQWService fqwService;
    private final FileService fileService;
    private final DeserializationService deserializationService;
    private final DownloadService downloadService;
    private final WordService wordService;

    private final FunctionsController functionsController;

    private final Map<String, Map<String, List<FileHeader>>> fileMap = initFileMap();

    private DateFormat dateFormat;

    List<List<String>> data;

    private final JdbcTemplate jdbcTemplate;

    FileHolder fileHolder = new FileHolder();

    @Autowired
    ApiServiceImpl(StudentService studentService,
                   DepartmentService departmentService,
                   LecturerService lecturerService,
                   OrientationService orientationService,
                   ProtectionService protectionService,
                   StudentLecturersService studentLecturersService,
                   LecturerRepository lecturerRepository,
                   FQWService fqwService,
                   FileService fileService,
                   DeserializationService deserializationService,
                   DownloadService downloadService,
                   WordService wordService, FunctionsController functionsController,
                   @Qualifier("sqlJdbcTemplate") JdbcTemplate jdbcTemplate,
                   DateFormat dateFormat
    ){
        this.jdbcTemplate = jdbcTemplate;
        this.studentService = studentService;
        this.departmentService = departmentService;
        this.lecturerService = lecturerService;
        this.orientationService = orientationService;
        this.protectionService = protectionService;
        this.studentLecturersService = studentLecturersService;
        this.lecturerRepository = lecturerRepository;
        this.fqwService = fqwService;
        this.fileService = fileService;
        this.deserializationService = deserializationService;
        this.downloadService = downloadService;
        this.wordService = wordService;

        this.functionsController = functionsController;
        this.dateFormat = dateFormat;
    }
}
