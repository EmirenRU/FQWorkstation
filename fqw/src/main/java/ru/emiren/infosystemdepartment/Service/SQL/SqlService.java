package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface SqlService {

    public CompletableFuture<String> getLecturersAsync(HttpServletRequest request,
                                                       Model model);
    public String viewLecturer(Model model);
    public String createLectureForm(Model model, String year, HttpServletRequest request);
    public String getLecturers(HttpServletRequest request,
                               Model model);
    public void refreshData();

    String getDetailPage(HttpServletRequest request, Model model, String id);

    String getDepartmentNameByStudentNumber(Long studNumber);

    String getOrientationCodeWithNameByStudentNumber(Long studNumber);

    boolean saveDataFromProtocol(Map<String, Object> dataMap);
}
