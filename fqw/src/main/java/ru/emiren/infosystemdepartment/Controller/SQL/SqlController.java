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

    @GetMapping("lecturers")
    public String CreateLectureForm(Model model){
        return "forward:lecturers/view";
    }

    @GetMapping("lecturers/view")
    public String viewLecturer(Model model){
        return sqlService.viewLecturer(model);
    }

    @GetMapping("/upload-data-form")
    public String uploadDataForm(Model model){
        return "uploadDataForm";
    }

    @GetMapping("lecturers/{year}")
    public String createLectureForm(Model model, @PathVariable String year, HttpServletRequest request){
        return sqlService.createLectureForm(model, year, request);
    }

    @PostMapping("lecturers")
    public String getLecturers(HttpServletRequest request, Model model){
        return sqlService.getLecturers(request, model);
    }

    @GetMapping("/view/detail/{id}")
    public String getStudentDetails(HttpServletRequest request, Model model, @PathVariable String id){
        return sqlService.getDetailPage(request, model, id);
    }

//    @PostMapping("lecturers")
//    public String getLecturers(HttpServletRequest request, Model model) {
//        return sqlService.getLecturers(request, model);
//    }
}
