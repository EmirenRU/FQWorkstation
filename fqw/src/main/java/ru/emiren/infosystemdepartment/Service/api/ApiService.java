package ru.emiren.infosystemdepartment.Service.api;

import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<String> handleFileUpload(MultipartFile file, String fileId);
    public CompletableFuture<ResponseEntity<?>> handleDataUpload(String message);
    public ResponseEntity<String> checkFileAvailability(String id);
    public ResponseEntity<String> downloadFile(String id, HttpServletResponse response);
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request);
    CompletableFuture<ResponseEntity<String>> receiveLecturers(HttpServletRequest request);
    CompletableFuture<ResponseEntity<String>> receiveThemes(HttpServletRequest request);

    ResponseEntity<String> returnJsFile(String s);
}
