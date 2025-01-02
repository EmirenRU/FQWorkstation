package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.ui.Model;

public interface SqlService {


    public String viewLecturer(Model model);
    public String createLectureForm(Model model, String year, HttpServletRequest request);
    public String getLecturers(HttpServletRequest request,
                               Model model);
}
