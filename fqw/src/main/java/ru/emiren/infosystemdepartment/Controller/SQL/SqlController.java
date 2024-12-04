package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
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
import java.util.List;

@Controller
@RequestMapping("/sql")
public class SqlController {

    private final LecturerRepository lecturerRepository;
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

    @Autowired
    public SqlController(StudentService studentService,
                         DepartmentService departmentService,
                         LecturerService lecturerService,
                         OrientationService orientationService,
                         ProtectionService protectionService,
                         StudentLecturersService studentLecturersService,
                         LecturerRepository lecturerRepository,
                         FQWService fqwService,
                         YearRepository yearRepository,
                         WordService wordService){
        this.studentService             = studentService;
        this.departmentService          = departmentService;
        this.lecturerService            = lecturerService;
        this.orientationService         = orientationService;
        this.protectionService          = protectionService;
        this.studentLecturersService    = studentLecturersService;
        this.lecturerRepository         = lecturerRepository;
        this.fqwService                 = fqwService;
        this.wordService                = wordService;
        this.yearRepository             = yearRepository;
        lecturerDTOS = lecturerService.getAllLecturer();
        departmentDTOS = departmentService.getAllDepartments();
        orientationDTOS = orientationService.getAllOrientations();
        years = yearRepository.findAll();
        fqwdtos = fqwService.getAllFQW();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @GetMapping("lecturers")
    public String CreateLectureForm(Model model){
        return "forward:lecturers/view";
    }

    @GetMapping("lecturers/view")
    public String viewLecturer(Model model){
        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);
        model.addAttribute("years_selector", years);
        model.addAttribute("archiveFlag", true);
        return "lecturers";
    }

    @GetMapping("/upload-data-form")
    public String UploadDataForm(Model model){
//        model.addAttribute();
        
        return "uploadDataForm";
    }

    @GetMapping("lecturers/{year}")
    public String CreateLectureForm(Model model, @PathVariable String year, HttpServletRequest request){

        List<StudentLecturersDTO> list =  studentLecturersService.findAllAndSortedByDate(year);

        if (list.isEmpty()){
            return "redirect:/sql/lecturers";
        }

        request.setAttribute("flag", true);
        request.setAttribute("specificLecturer", lecturerService.createDummyLecturer());
        request.setAttribute("studentLecturers_container", list);
        return "forward:/sql/lecturers/view";
    }



    @PostMapping("lecturers")
    public String getLecturers(HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes){


        Long lecturerId = Long.valueOf(request.getParameter("lecturer"));
        String orientationCode = request.getParameter("orientation");
        Long departmentCode = Long.valueOf(request.getParameter("department"));
        String theme = request.getParameter("themes");
        String strDateFrom =request.getParameter("date-from");
        String strDateTo =request.getParameter("date-to");

        java.time.Year dateFrom = null;
        java.time.Year dateTo = null;

        if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()){
            dateFrom = java.time.Year.parse(strDateFrom);
            dateTo   = java.time.Year.parse(strDateTo);
        }

//        redirectAttributes.addAttribute("studentLecturers_container", studentLecturersService.findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(orientationCode, departmentCode, date, theme, lecturerId));
//        redirectAttributes.addAttribute("flag", true);


        model.addAttribute("studentLecturers_container",
                studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(orientationCode,
                                                                                                            departmentCode,
                                                                                                            dateFrom,
                                                                                                            dateTo,
                                                                                                    theme, lecturerId));

        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);
        model.addAttribute("years_selector", years);

        model.addAttribute("flag", true);

        if (lecturerId == -1){
            model.addAttribute("specificLecturer", lecturerService.createDummyLecturer());
//            redirectAttributes.addAttribute("specificLecturer", lecturerService.createDummyLecturer());
            return "lecturers";
        }

        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client

        LecturerDTO lecturerDTO = lecturerService.findDtoByLecturerId(lecturerId);

        model.addAttribute("specificLecturer", lecturerDTO);

        return "lecturers";
    }
}
