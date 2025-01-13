package ru.emiren.protocol.Controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.emiren.protocol.Service.api.ApiService;

import java.io.IOException;

@RestController
@RequestMapping("/api/protocol")
public class RestProtocolController {

    private final ApiService apiService;

    public RestProtocolController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/upload_file")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String fileId) {
        return apiService.handleFileUpload(file, fileId);
    }

    @PostMapping("/check_file_availability/{id}")
    public ResponseEntity<?> checkFileAvailability(@PathVariable("id") String id ) {
        return apiService.checkFileAvailability(id);
    }

    @GetMapping("/download_file/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable("id") String id, HttpServletResponse response) {
        return apiService.downloadFile(id, response);
    }

    @GetMapping("/download_protocols")
    public String downloadProtocols(HttpServletResponse response) throws IOException {
        return apiService.downloadProtocols(response);
    }
}
