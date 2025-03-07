package ru.emiren.protocol.Service.api;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

public interface ApiService {
    String downloadProtocols(HttpServletResponse response) throws IOException;

    ResponseEntity<?> handleFileUpload(MultipartFile file, String fileId);
    ResponseEntity<?> handleFileUploadWithTemplate(MultipartFile file, MultipartFile template, String fileId);

    ResponseEntity<?> checkFileAvailability(String id);

    ResponseEntity<String> downloadFile(String id, HttpServletResponse response);
    ResponseEntity<?> downloadTemplate(String hashId, HttpServletResponse response);
}
