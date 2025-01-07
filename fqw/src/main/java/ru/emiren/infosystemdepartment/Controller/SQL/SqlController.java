package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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


//    @GetMapping("lecturers/view")
//    public String viewLecturer(Model model){
//        return sqlService.viewLecturer(model);
//    }

    @GetMapping("lecturers/view")
    public CompletableFuture<String> viewLecturer(Model model){
        return sqlService.viewLecturerAsync(model)
                .thenApply(
                result-> {
                    log.info("Async viewLecturer is complete");
                    return result;
                }
        );
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
    public CompletableFuture<String> getLecturers(HttpServletRequest request, Model model){
        return sqlService.getLecturersAsync(request, model).thenApply( result -> {
            log.info("Async getLecturers is complete");
            return result;
        }); }

//    @PostMapping("lecturers")
//    public String getLecturers(HttpServletRequest request, Model model) {
//        return sqlService.getLecturers(request, model);
//    }
}
