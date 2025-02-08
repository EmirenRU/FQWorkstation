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
import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api")
public class ApplicationProgrammingInterfaceController {

    private final ApiService apiService;

    @Autowired
    public ApplicationProgrammingInterfaceController(ApiService apiService) {
        this.apiService = apiService;
    }

    /**
     * Receive the SqlPayload for React by request
     *
     * @param request contains a required data
     * @return a ResponseEntity with a status and FQW in Body
     */
    @GetMapping("/v1/receive_lecturers")
    public CompletableFuture<ResponseEntity<String>> receiveLecturers(HttpServletRequest request) {
        return apiService.receiveLecturers(request).thenApply( reply -> {
            log.info("receive lecturers response");
            return reply;
        });
    }

    /**
     * Receive the FQW by a request from JS
     *
     * @param request contains a required data
     * @return a ResponseEntity with a status and FQW in Body
     */
    @GetMapping("/sql/receive_fqw")
    public CompletableFuture<ResponseEntity<?>> receiveFqw(HttpServletRequest request) {
        return apiService.receiveThemes(request).thenApply(res -> {
            log.info("receive fqw response");
            return res;
        });
    }

    /**
     * handle a message from console
     *
     * @param request contains a required data
     * @param response output for a client
     * @param message data from terminal
     * @return a ResponseEntity with a status
     */
    @PostMapping("/v1/upload_data_to_sql")
    public CompletableFuture<ResponseEntity<?>> handleDataUpload(HttpServletRequest request, HttpServletResponse response, @RequestBody String message) {
        log.info("{}, {}", request.getAttribute("message"), message);
        return apiService.handleDataUpload(message).thenApply(
                res -> {
                    log.info("has completed handling data upload with message {}", message);
                    return res;
                });
    }

    /**
     * Uploads a file to process to generate a protocol
     *
     * @param file
     * @param fileId
     * @return a ResponseEntity with a status
     */
    @PostMapping("/v2/upload_file")
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
    @PostMapping("/v2/check_file_availability/{id}")
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
    @GetMapping("/v2/download_file/{id}")
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
    @GetMapping("/v1/download_protocols")
    public String downloadProtocols(HttpServletResponse response) throws IOException {
        return apiService.downloadProtocols(response);
    }

    /**
     * Transfer from client to sevice to handle all CRUD operations in message
     *
     * @param request a Client's Data
     * @return a RequestEntity with Status of operation
     * @deprecated use {@link #handleDataUpload(HttpServletRequest, HttpServletResponse, String)} instead
     */
    @Deprecated(forRemoval = true)
    @PostMapping("/v1/upload-data")
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request){
        return apiService.uploadDataAndProceedToModels(request);
    }



}

