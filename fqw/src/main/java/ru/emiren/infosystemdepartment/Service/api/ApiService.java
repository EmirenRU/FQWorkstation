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
    public CompletableFuture<ResponseEntity<?>> handleDataUpload(String message);
    public ResponseEntity<String> uploadDataAndProceedToModels(MultipartHttpServletRequest request);

    ResponseEntity<String> returnJsFile(String s);
}
