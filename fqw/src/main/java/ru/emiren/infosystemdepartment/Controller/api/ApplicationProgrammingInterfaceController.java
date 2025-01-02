package ru.emiren.infosystemdepartment.Controller.api;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.infosystemdepartment.Service.api.ApiService;

import java.io.*;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApplicationProgrammingInterfaceController {

    private final ApiService apiService;

    @Autowired
    public ApplicationProgrammingInterfaceController(ApiService apiService) {this.apiService = apiService;}


    @PostMapping("v2/upload_file")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String fileId) {
        return apiService.handleFileUpload(file, fileId);
    }

    @PostMapping("/v1/upload_data_to_sql")
    public ResponseEntity<?> handleDataUpload(@PathVariable("message") String message) {
        return apiService.handleDataUpload(message);
    }


    @PostMapping("/v2/check_file_availability/{id}")
    public ResponseEntity<?> checkFileAvailability(@PathVariable("id") String id ) {
        return apiService.checkFileAvailability(id);
    }

    @GetMapping("/v2/download_file/{id}")
    public ResponseEntity<String> downloadFile(@PathVariable("id") String id, HttpServletResponse response) {
        return apiService.downloadFile(id, response);
    }

    @GetMapping("/v1/download_protocols")
    public String downloadProtocols(HttpServletResponse response) throws IOException {
        return apiService.downloadProtocols(response);
    }

    @PostMapping("/v1/upload-data")
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request){
        return apiService.uploadDataAndProceedToModels(request);
    }

}

