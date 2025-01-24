package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;

import java.util.concurrent.CompletableFuture;

@Controller
@RequestMapping("/sql")
@Slf4j
public class SqlController {
    private final SqlService sqlService;


    @Autowired
    public SqlController(SqlService sqlService) { this.sqlService = sqlService; }

    /**
     * Only redirect to necessary uri
     * @param model
     * @return a redirect to fqw page
     */
    @GetMapping("lecturers")
    public String CreateLectureForm(Model model){
        return "forward:lecturers/view";
    }

    /**
     * Create a table page with information and selectors for FQW
     * @param model
     * @return a page with table
     */
    @GetMapping("lecturers/view")
    public String viewLecturer(Model model){
        return sqlService.viewLecturer(model);
    }

    /**
     * Create a page for upload-form
     * @param model
     * @return a page with upload form
     */
    @GetMapping("/upload-data-form")
    public String uploadDataForm(Model model){
        return "uploadDataForm";
    }

    /**
     * Return FQW data table with selected year (archive feature)
     * @param model
     * @param year
     * @param request
     * @return a page with year
     * @deprecated
     */
    @Deprecated(forRemoval = true)
    @GetMapping("lecturers/{year}")
    public String createLectureForm(Model model, @PathVariable String year, HttpServletRequest request){
        return sqlService.createLectureForm(model, year, request);
    }

    /**
     * Create a page with table and selectors for SQL FQW
     * @param request
     * @param model
     * @return a page
     */
    @PostMapping("lecturers")
    public String getLecturers(HttpServletRequest request, Model model){
        return sqlService.getLecturers(request, model);
    }

    /**
     * Not fully implemented, because Frontend colleague does not check messages and does not do
     * @param request
     * @param model
     * @param id
     * @return a page with sql detail
     */
    @GetMapping("/view/detail/{id}")
    public String getStudentDetails(HttpServletRequest request, Model model, @PathVariable String id){
        return sqlService.getDetailPage(request, model, id);
    }

//    @PostMapping("lecturers")
//    public String getLecturers(HttpServletRequest request, Model model) {
//        return sqlService.getLecturers(request, model);
//    }
}
