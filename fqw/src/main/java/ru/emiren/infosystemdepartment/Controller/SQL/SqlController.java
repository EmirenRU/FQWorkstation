package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.groovy.util.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

@Controller
@RequestMapping("/sql")
@Slf4j
public class SqlController {

    StudentService studentService;
    DepartmentService departmentService;
    LecturerService lecturerService;
    OrientationService orientationService;
    ProtectionService protectionService;
    StudentLecturersService studentLecturersService;
    FQWService fqwService;
    WordService wordService;
    YearRepository yearRepository;

    List<LecturerDTO> lecturerDTOS;
    List<OrientationDTO> orientationDTOS;
    List<DepartmentDTO> departmentDTOS;
    List<FQWDTO> fqwdtos;
    List<Year> years;

    DateTimeFormatter dateTimeFormatter;

    @DateTimeFormat(pattern = "yyyy")
    private LocalDate date;

    private final List<String> params = new ArrayList<>(
            List.of("lecturers_selector", "departments_selector",
                    "orientations_selector", "themes_selector",
                    "years_selector", "specificLecturer",
                    "studentLecturers_container"
                    )
    );

    @Autowired
    public SqlController(StudentService studentService,
                         DepartmentService departmentService,
                         LecturerService lecturerService,
                         OrientationService orientationService,
                         ProtectionService protectionService,
                         StudentLecturersService studentLecturersService,
                         FQWService fqwService,
                         YearRepository yearRepository,
                         WordService wordService){
        this.studentService             = studentService;
        this.departmentService          = departmentService;
        this.lecturerService            = lecturerService;
        this.orientationService         = orientationService;
        this.protectionService          = protectionService;
        this.studentLecturersService    = studentLecturersService;
        this.fqwService                 = fqwService;
        this.wordService                = wordService;
        this.yearRepository             = yearRepository;
        
        lecturerDTOS       = lecturerService.getAllLecturer();
        departmentDTOS     = departmentService.getAllDepartments();
        orientationDTOS    = orientationService.getAllOrientations();
        years              = yearRepository.findAll();
        fqwdtos            = fqwService.getAllFQW();
        dateTimeFormatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @GetMapping("lecturers")
    public String CreateLectureForm(Model model){
        return "forward:lecturers/view";
    }

    @GetMapping("lecturers/view")
    public String viewLecturer(Model model){

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        return "lecturers";
    }

    @GetMapping("/upload-data-form")
    public String UploadDataForm(Model model){

        return "uploadDataForm";
    }

    @GetMapping("lecturers/{year}")
    public String CreateLectureForm(Model model, @PathVariable String year, HttpServletRequest request){

        List<StudentLecturersDTO> list =  studentLecturersService.findAllAndSortedByDate(year);

        if (list.isEmpty()){
            return "redirect:/sql/lecturers";
        }

        request.setAttribute("flag", true);
        request.setAttribute(params.get(5), lecturerService.createDummyLecturer());
        request.setAttribute(params.get(6), list);
        return "forward:/sql/lecturers/view";
    }

    @PostMapping("lecturers")
    public String getLecturers(HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes){


        Long lecturerId = Long.parseLong(request.getParameter("lecturer"));
        String[] orientationParams = request.getParameterValues("orientation");
        List<String> orientationCodes = (orientationParams != null) ? Arrays.asList(orientationParams) : List.of("-1");
        Long departmentCode = Long.valueOf(request.getParameter("department"));
        String theme = request.getParameter("themes");
        String strDateFrom =request.getParameter("date-from");
        String strDateTo =request.getParameter("date-to");

        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerId,
                orientationParams,
                departmentCode,
                theme,
                strDateFrom,
                strDateTo);

        java.time.Year dateFrom = null;
        java.time.Year dateTo = null;

        if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()){
            dateFrom = java.time.Year.parse(strDateFrom);
            dateTo   = java.time.Year.parse(strDateTo);
        }

        log.info("date from and to: {} and {}", dateFrom, dateTo );

        List<StudentLecturersDTO> res = studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
                orientationCodes,
                departmentCode,
                dateFrom,
                dateTo,
                theme,
                lecturerId
        );

        model.addAttribute(params.get(6), res);
        log.info("The result is {}", res);

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);
        model.addAttribute(params.get(4), years);

        model.addAttribute("flag", true);

        if (lecturerId == -1){
            model.addAttribute(params.get(5), lecturerService.createDummyLecturer());
            return "lecturers";
        }

        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client

        LecturerDTO lecturerDTO = lecturerService.findDtoByLecturerId(lecturerId);

        model.addAttribute(params.get(5), lecturerDTO);

        return "lecturers";
    }
}
