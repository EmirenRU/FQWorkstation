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
     *
     * @param file
     * @param fileId
     * @return a ResponseEntity with a status
     */
    @PostMapping("/upload_file")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String fileId) {
        log.info("The file size is: {}", file.getSize());
        return apiService.handleFileUpload(file, fileId);
    }

    /**
     * returns a download status of file with ID
     *
     * @param id
     * @return a ResponseEntity with a status
     */
    @PostMapping("/check_file_availability/{id}")
    public ResponseEntity<?> checkFileAvailability(@PathVariable("id") String id ) {
        return apiService.checkFileAvailability(id);
    }

    /**
     * Download the file from the server
     *
     * @param id // HashID
     * @param response Client's data
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
