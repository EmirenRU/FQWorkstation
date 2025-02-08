package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SqlService {

    public CompletableFuture<String> getLecturersAsync(HttpServletRequest request, Model model);
    public String viewLecturer(Model model);
    public String createLectureForm(Model model, String year, HttpServletRequest request);
    public String getLecturers(HttpServletRequest request,
                               Model model);
    String getDetailPage(HttpServletRequest request, Model model, String id);
    String getDepartmentNameByStudentNumber(Long studNumber);
    String getOrientationCodeWithNameByStudentNumber(Long studNumber);
    ResponseEntity<String> saveDataFromProtocol(Map<String, Object> dataMap);

    ResponseEntity<Map<String, String>> findDepartmentAndOrientationByStudNumber(String studNumber);
}
