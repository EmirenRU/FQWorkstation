package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SqlService {

    public CompletableFuture<String> getLecturersAsync(HttpServletRequest request, Model model);

    @Async
    CompletableFuture<ResponseEntity<String>> receiveLecturers(HttpServletRequest request);

    @Async
    CompletableFuture<ResponseEntity<String>> receiveThemes(HttpServletRequest request);

    public String viewLecturer(Model model);
    public String createLectureForm(Model model, String year, HttpServletRequest request);
    public String getLecturers(HttpServletRequest request,
                               Model model);
    String getDetailPage(HttpServletRequest request, Model model, String id);
    String getDepartmentNameByStudentNumber(Long studNumber);
    String getOrientationCodeWithNameByStudentNumber(Long studNumber);
    ResponseEntity<String> saveDataFromProtocol(Map<String, Object> dataMap);

    ResponseEntity<Map<String, String>> findDepartmentAndOrientationByStudNumber(String studNumber);
    void refreshData();
}
