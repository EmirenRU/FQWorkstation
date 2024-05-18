package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.io.FileOutputStream;
import java.io.IOException;
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
        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);
        model.addAttribute("years_selector", years);
        date = LocalDate.now();
        model.addAttribute("date", date);
        return "lecturers";
    }

    @GetMapping("lecturers/{year}")
    public String CreateLectureForm(Model model, @PathVariable String year){
        System.out.println(year + " " + year.getClass());

        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);
        model.addAttribute("years_selector", years);
        model.addAttribute("specificLecturer", lecturerService.createDummyLecturer());

        List<StudentLecturersDTO> list =  studentLecturersService.findAllAndSortedByDate(year);

        if (list.isEmpty()){
            return "redirect:/sql/lecturers/";
        }

        model.addAttribute("studentLecturers_container", list);
        return "lecturers";
    }

    @GetMapping("/api/v1/download_protocols")
    public String downloadProtocols(Model model,HttpServletResponse response) throws IOException {
        XWPFDocument doc = wordService.generateWordDocument();
        doc.write(new FileOutputStream("ddd.docx"));
//        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
//        response.setHeader("Content-Disposition", "attachment; filename=\"protocols.docx\"");
//        doc.write(response.getOutputStream());
        doc.close();
        return "lecturers";
    }

    @PostMapping("lecturers")
    public String getLecturerers( HttpServletRequest request,
                                 Model model){

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
        model.addAttribute("years_selector", years);

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
