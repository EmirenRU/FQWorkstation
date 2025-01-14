package ru.emiren.infosystemdepartment.Controller.API;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import ru.emiren.infosystemdepartment.Service.api.ApiService;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApplicationProgrammingInterfaceController {

    private final ApiService apiService;

    @Autowired
    public ApplicationProgrammingInterfaceController(ApiService apiService) {this.apiService = apiService;}

    @GetMapping("/v1/receive_lecturers")
    public CompletableFuture<ResponseEntity<?>> receiveLecturers(HttpServletRequest request) {
        return apiService.receiveLecturers(request).thenApply( reply -> {
            log.info("receive lecturers response");
            return reply;
        });
    }

    @PostMapping("v2/upload_file")
    public ResponseEntity<?> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") String fileId) {
        log.info("The file size is: {}", file.getSize());
        return apiService.handleFileUpload(file, fileId);
    }

    @PostMapping("/v1/upload_data_to_sql")
    public CompletableFuture<ResponseEntity<?>> handleDataUpload(HttpServletRequest request, HttpServletResponse response, @RequestBody String message) {
        log.info("{}, {}", request.getAttribute("message"), message);
        return apiService.handleDataUpload(message).thenApply(
                res -> {
                    log.info("has completed handling data upload with message {}", message);
                    return res;
                });
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

