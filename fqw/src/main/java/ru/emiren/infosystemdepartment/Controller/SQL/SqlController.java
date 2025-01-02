package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Mapper.SQL.StudentLecturersMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.Buffer;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

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
    public String getLecturers(HttpServletRequest request, Model model){ return sqlService.getLecturers(request, model); }
}
