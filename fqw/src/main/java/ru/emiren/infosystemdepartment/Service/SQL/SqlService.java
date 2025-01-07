package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

import java.util.concurrent.CompletableFuture;

public interface SqlService {

    public CompletableFuture<String> getLecturersAsync(HttpServletRequest request,
                                                       Model model);
    public String viewLecturer(Model model);
    public CompletableFuture<String> viewLecturerAsync(Model model);
    public String createLectureForm(Model model, String year, HttpServletRequest request);
    public String getLecturers(HttpServletRequest request,
                               Model model);
    public void refreshData();
}
