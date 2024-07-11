package ru.emiren.infosystemdepartment.Controller.SQL;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.Kafka.Impl.KafkaConsumerServiceImpl;
import ru.emiren.infosystemdepartment.Service.Kafka.KafkaConsumerService;
import ru.emiren.infosystemdepartment.Service.Kafka.KafkaProducerService;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.Word.WordService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/sql")
@Lazy
public class SqlController {

    private final LecturerRepository lecturerRepository;
    private final StudentService studentService;
    private final DepartmentService departmentService;
    private final LecturerService lecturerService;
    private final OrientationService orientationService;
    private final ProtectionService protectionService;
    private final StudentLecturersService studentLecturersService;
    private final FQWService fqwService;
    private final WordService wordService;
    private final YearRepository yearRepository;

    List<LecturerDTO> lecturerDTOS;
    List<OrientationDTO> orientationDTOS;
    List<DepartmentDTO> departmentDTOS;
    List<FQWDTO> fqwdtos;
    List<Year> years;

    DateTimeFormatter dateTimeFormatter;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;

    @Autowired
    private KafkaProducerService kafkaProducer;

    @Autowired
    private KafkaConsumerService kafkaConsumer;

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
                         WordService wordService,
                         KafkaProducerService kafkaProducer,
                         KafkaConsumerService kafkaConsumer){

        this.studentService = studentService;
        this.departmentService = departmentService;
        this.lecturerService = lecturerService;
        this.orientationService = orientationService;
        this.protectionService = protectionService;
        this.studentLecturersService = studentLecturersService;
        this.lecturerRepository = lecturerRepository;
        this.fqwService = fqwService;
        this.wordService = wordService;
        this.yearRepository = yearRepository;
        this.kafkaProducer = kafkaProducer;
        this.kafkaConsumer = kafkaConsumer;

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
    public String viewLecturer(Model model, HttpServletRequest request,
                               @RequestParam(value = "lecturerId", required = false) Long lecturerId,
                               @RequestParam(value = "flag", required = false) Boolean flag){
        List<StudentLecturersDTO> l = kafkaConsumer.getList();
        if (!l.isEmpty()){
            model.addAttribute("studentLecturers_container", l);
        }
        if (lecturerId != null) {
            if (lecturerId == -1) {
                model.addAttribute("specificLecturer", lecturerService.createDummyLecturer());
            } else if (lecturerId >= 0) {
                LecturerDTO lecturerDTO = lecturerService.findByLecturerId(lecturerId);
                request.setAttribute("specificLecturer", lecturerDTO);
            }
        }
        if (flag != null && flag){
            model.addAttribute("flag", true);
        }

        model.addAttribute("lecturers_selector", lecturerDTOS);
        model.addAttribute("departments_selector", departmentDTOS);
        model.addAttribute("orientations_selector", orientationDTOS);
        model.addAttribute("themes_selector", fqwdtos);
        model.addAttribute("years_selector", years);
        model.addAttribute("archiveFlag", true);
        return "lecturers";
    }

    @GetMapping("lecturers/{year}")
    public String CreateLectureForm(Model model, @PathVariable String year, HttpServletRequest request){

        List<StudentLecturersDTO> l =  studentLecturersService.findAllAndSortedByDate(year);
//        kafkaProducer.sendMessageToSqlTopic(l);



        if (l.isEmpty()){
            return "redirect:/sql/lecturers";
        }

        request.setAttribute("flag", true);
        request.setAttribute("specificLecturer", lecturerService.createDummyLecturer());
        request.setAttribute("studentLecturers_container", l);
        return "forward:/sql/lecturers/view";
    }



    @PostMapping("lecturers")
    public String getLecturers(HttpServletRequest request,
                               Model model,
                               RedirectAttributes redirectAttributes){


        Long lecturerId = Long.valueOf(request.getParameter("lecturer"));
        String orientationCode = request.getParameter("orientation");
        String departmentCode = request.getParameter("department");
        String theme = request.getParameter("themes");
        String strDate =request.getParameter("date");
        LocalDate date = null;

        if (!strDate.isEmpty()){
            date = LocalDate.parse(strDate);
        }

        kafkaProducer.sendMessageToSqlTopic(studentLecturersService.findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(orientationCode, departmentCode, date, theme, lecturerId));

        redirectAttributes.addAttribute("lecturerId", lecturerId);
        redirectAttributes.addAttribute("flag", true);

        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client



        return "redirect:/sql/lecturers/view";
    }
}
