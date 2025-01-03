package ru.emiren.infosystemdepartment.Service.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface ApiService {
    public String downloadProtocols(HttpServletResponse response) throws IOException;
    public ResponseEntity<?> handleFileUpload(MultipartFile file, String fileId);
    public CompletableFuture<ResponseEntity<?>> handleDataUpload(String message);
    public ResponseEntity<?> checkFileAvailability(String id);
    public ResponseEntity<String> downloadFile(String id, HttpServletResponse response);
    public String generateAndSendFile(HttpServletResponse response) throws IOException;
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request);
}
