package ru.emiren.protocol.Service.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

public interface ApiService {
    public String downloadProtocols(HttpServletResponse response) throws IOException;
    public ResponseEntity<?> handleFileUpload(MultipartFile file, String fileId);
    public ResponseEntity<?> checkFileAvailability(String id);
    public ResponseEntity<String> downloadFile(String id, HttpServletResponse response);
    public String generateAndSendFile(HttpServletResponse response) throws IOException;
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request);
}
