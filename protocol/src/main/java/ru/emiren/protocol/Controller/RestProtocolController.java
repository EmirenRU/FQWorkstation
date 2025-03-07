package ru.emiren.protocol.Controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.emiren.protocol.Service.api.ApiService;

import java.io.IOException;

@RestController
@RequestMapping("/api/protocol")
@Slf4j
public class RestProtocolController {

    private final ApiService apiService;

    public RestProtocolController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Uploads a file to process to generate a protocol
     * @param file a MultiparFile from JavaScript's fetch
     * @param fileId a hash ID of a file
     * @return a ResponseEntity with a status
     */
    @PostMapping("/upload_file")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String fileId) {
        log.info("The file size is: {}", file.getSize());
        return apiService.handleFileUpload(file, fileId);
    }

    @PostMapping("/upload_file_with_template")
    public ResponseEntity<?> handleFileUploadWithTemplate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("template") MultipartFile template,
            @RequestParam("id") String fileId){
        log.info("In handleFileUploadWithTemplate");
        log.info("The protocol file size and name are: {}; {}", file.getSize(), file.getName());
        log.info("The template file size and name are: {}; {}", template.getSize(), template.getName());
        return apiService.handleFileUploadWithTemplate(file, template, fileId);
    }

    @PostMapping("/download-template/{id}")
    public ResponseEntity<?> downloadTemplate(@PathVariable("id") String hashId, HttpServletResponse response) {
        log.info("In Download Template with hashId: {}", hashId);
        return apiService.downloadTemplate(hashId, response);
    }

    /**
     * returns a download status of file with ID
     * @param id a hash ID of a file
     * @return a ResponseEntity with a status
     */
    @PostMapping("/check_file_availability/{id}")
    public ResponseEntity<?> checkFileAvailability(@PathVariable("id") String id ) {
        return apiService.checkFileAvailability(id);
    }

    /**
     * Download the file from the server
     * @param id hash ID of a file
     * @param response Client's Response Servlet
     * @return a ResponseEntity with a status
     */
    @GetMapping("/download_file/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable("id") String id, HttpServletResponse response) {
        return apiService.downloadFile(id, response);
    }

    /**
     * Download the file from the server
     *
     * @param response a
     * @return a status
     * @deprecated use {@link #downloadFile(String, HttpServletResponse)} instead
     */
    @Deprecated(forRemoval = true)
    @GetMapping("/download_protocols")
    public String downloadProtocols(HttpServletResponse response) throws IOException {
        return apiService.downloadProtocols(response);
    }
}
