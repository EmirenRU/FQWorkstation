package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

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

    List<LecturerDTO> lecturerDTOS;
    List<OrientationDTO> orientationDTOS;
    List<DepartmentDTO> departmentDTOS;
    List<FQWDTO> fqwdtos;

    DateTimeFormatter dateTimeFormatter;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
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
        lecturerDTOS = lecturerService.getAllLecturer();
        departmentDTOS = departmentService.getAllDepartments();
        orientationDTOS = orientationService.getAllOrientations();
        fqwdtos = fqwService.getAllFQW();
        dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    }

    @GetMapping("lecturers")
    public String CreateLectureForm(Model model){
        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);

        date = LocalDate.now();
        wordService.generateWordDocument();
        model.addAttribute("date", date);
        return "lecturers";
    }

    // @RequestParam("lecturer") Long lecturerId,

//    @RequestParam("lecturer") Long lecturerId,
//
//                                 @RequestParam("department") String departmentCode,
    //                                 @RequestParam("orientation") List<String> orientationCode,
    @PostMapping("lecturers")
    public String getLecturerers( HttpServletRequest request,
                                 Model model){

//        List<String> orientationCodes = Arrays.asList(request.getParameterValues("orientation"));
        Long lecturerId = Long.valueOf(request.getParameter("lecturer"));
        String orientationCode = request.getParameter("orientation");
        String departmentCode = request.getParameter("department");
        String theme = request.getParameter("themes");
        String strDate =request.getParameter("date");
        LocalDate date = null;

        if (!strDate.isEmpty()){
            date = LocalDate.parse(strDate);
        }

        // Init models for selectors
        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);

//        model.addAttribute("studentLecturers_container", studentLecturersService.findAllAndSortedByLecturerName());
        model.addAttribute("studentLecturers_container", studentLecturersService.findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(orientationCode, departmentCode, date, theme, lecturerId));


        if (lecturerId == -1){
            model.addAttribute("specificLecturer", lecturerService.createDummyLecturer());
            return "lecturers";
        }



        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client

        // Check if lectorDto is in
        LecturerDTO lecturerDTO = lecturerService.findByLecturerId(lecturerId);

        List<StudentDTO> students = studentService.findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(lecturerId, orientationCode, departmentCode);

//        model.addAttribute("students_container", studentService.findAllStudentByLecturerId(lecturerId));
//        model.addAttribute("lecturers_container", Arrays.asList(lecturerDTO));

        model.addAttribute("specificLecturer", lecturerDTO);

        return "lecturers";
    }


//    @GetMapping("lecturers/${year}")
//    public String CreateLecturerForm(Model model,
//                                     @NotNull @Param("year") LocalDate date){
//
//    }

    @GetMapping("Students")
    public String CreateStudentForm(Model model){

        return "students";
    }
}
