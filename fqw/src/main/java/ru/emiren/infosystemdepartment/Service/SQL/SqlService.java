package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.Payload.SelectorSqlPayload;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SqlService {

    CompletableFuture<String> getLecturersAsync(HttpServletRequest request, Model model);
    CompletableFuture<ResponseEntity<String>> getLecturersAsync(Map<String, Object> data);

    CompletableFuture<ResponseEntity<String>> receiveLecturers();
    CompletableFuture<ResponseEntity<String>> receiveThemes(HttpServletRequest request);

    String viewLecturer(Model model);
    String createLectureForm(Model model, String year, HttpServletRequest request);
    String getLecturers(HttpServletRequest request,
                               Model model);
    String getDetailPage(HttpServletRequest request, Model model, String id);
    String getDepartmentNameByStudentNumber(Long studNumber);
    String getOrientationCodeWithNameByStudentNumber(Long studNumber);
    ResponseEntity<String> saveDataFromProtocol(Map<String, Object> dataMap);

    ResponseEntity<Map<String, String>> findDepartmentAndOrientationByStudNumber(String studNumber);
    void refreshData();

    SelectorSqlPayload receiveSelectors();
}
