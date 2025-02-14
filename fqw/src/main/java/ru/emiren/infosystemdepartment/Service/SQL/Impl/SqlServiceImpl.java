package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SqlServiceImpl implements SqlService {
    private final ProtocolQuestionService protocolQuestionService;
    private final QuestionService questionService;
    private final ProtocolService protocolService;
    private final StudentService studentService;
    private final DepartmentService departmentService;
    private final LecturerService lecturerService;
    private final OrientationService orientationService;
    private final StudentLecturersService studentLecturersService;
    private final FQWService fqwService;
    private final Gson gson;
    List<LecturerDTO> lecturerDTOS;
    List<OrientationDTO> orientationDTOS;
    List<DepartmentDTO> departmentDTOS;
    List<FQWDTO> fqwdtos;

    DateTimeFormatter dateTimeFormatter;
    private static final String lecturerTemplate = "lecturer";

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
    public SqlServiceImpl(StudentService studentService,
                          DepartmentService departmentService,
                          LecturerService lecturerService,
                          OrientationService orientationService,
                          ProtectionService protectionService,
                          StudentLecturersService studentLecturersService,
                          FQWService fqwService,
                          ProtocolQuestionService protocolQuestionService,
                          QuestionService questionService,
                          ProtocolService protocolService,
                          Gson gson){
        this.studentService             = studentService;
        this.departmentService          = departmentService;
        this.lecturerService            = lecturerService;
        this.orientationService         = orientationService;
        this.studentLecturersService    = studentLecturersService;
        this.fqwService                 = fqwService;

        lecturerDTOS       = lecturerService.getConnectedLecturers();
        departmentDTOS     = departmentService.getAllDepartments();
        orientationDTOS    = orientationService.getAllOrientations();
        fqwdtos            = fqwService.getAllFQW();
        dateTimeFormatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.protocolQuestionService = protocolQuestionService;
        this.questionService = questionService;
        this.protocolService = protocolService;
        this.gson = gson;
    }


    /**
     * transporting all data from SL to SqlPayload for React transaction
     * @return a CompletableFuture (Async) with ResponseEntity's header and body json
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<String>> receiveLecturers() {
        List<SqlPayload> res = studentLecturersService.getAllStudentLecturers();
        Map<String, String> headers = new HashMap<>();
        Gson gsonBeautify = new GsonBuilder().setPrettyPrinting().create();
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).header(headers.toString()).body(gsonBeautify.toJson(res)));
    }

    /**
     * receive all FQW data
     *
     * @param request
     * @return a CompletableFuture (Async) with ResponseEntity's header and body json
     */
    @Async
    @Override
    public CompletableFuture<ResponseEntity<String>> receiveThemes(HttpServletRequest request) {
        List<FQWDTO> res = fqwService.getAllFQW();
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(res.toString()));
    }



    @Override
    public String viewLecturer(Model model) {
        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);

        log.info("View Lecturer model prepared asynchronously.");
        return lecturerTemplate;
    }

    @Override
    @Async
    public CompletableFuture<String> getLecturersAsync(HttpServletRequest request, Model model) {
        String[] lecturerParams = request.getParameterValues("lecturer"); // Long
        List<Long> lecturerIds = (lecturerParams != null) ? Arrays.stream(lecturerParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] orientationParams = request.getParameterValues("orientation");
        List<String> orientationCodes = (orientationParams != null) ? Arrays.asList(orientationParams) : List.of("-1");
        String[] departmentParams = request.getParameterValues("department"); // Long
        List<Long> departmentCode = (departmentParams != null) ? Arrays.stream(departmentParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] themeParams = request.getParameterValues("themes");
        List<Long> theme = (themeParams != null) ? Arrays.stream(themeParams).map(Long::parseLong).toList() : List.of((long) -1);
        String strDateFrom = request.getParameter("from");
        String strDateTo = request.getParameter("to");

        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerIds,
                orientationParams,
                departmentCode,
                theme,
                strDateFrom,
                strDateTo);

        Integer dateFrom = null;
        Integer dateTo = null;

        if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()){
            dateFrom = Integer.valueOf(strDateFrom);
            dateTo   = Integer.valueOf(strDateTo);
        }

        log.info("date from and to: {} and {}", dateFrom, dateTo );

//        String stringForQuery = theme.stream()
//                .map(word -> "%" + word + "%").collect(Collectors.joining("|"));

        List<StudentLecturersDTO> res = studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
                orientationCodes,
                departmentCode,
                dateFrom,
                dateTo,
                theme,
                lecturerIds
        );

        model.addAttribute(params.get(6), res);
        log.info("The result is {} empty", res.isEmpty());

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);

        model.addAttribute("flag", true);

        if (lecturerIds.getFirst() == -1){
            model.addAttribute(params.get(5), lecturerService.createDummyLecturer());
            log.info("Async getLecturer task has completed");
            return CompletableFuture.completedFuture(lecturerTemplate);
        }

        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client
        log.info("Async getLecturer task has completed");
        return CompletableFuture.completedFuture(lecturerTemplate);
    }

    @Override
    @Async
    public CompletableFuture<ResponseEntity<String>> getLecturersAsync(Map<String, Object> data) {
        log.info("Async getLecturer task");
        log.info("Request: {}", data);
        List<String> lecturerParams = (List<String>) data.get("lecturer"); // Long
        List<Long> lecturerIds = (lecturerParams != null && !lecturerParams.isEmpty()) ? lecturerParams.stream().map(Long::parseLong).toList() : List.of((long) -1);

        List<String> orientationParams = (List<String>) data.get("orientation");
        List<String> orientationCodes = (orientationParams != null && !orientationParams.isEmpty()) ? orientationParams : List.of("-1");

        List<String> departmentParams = (List<String>) data.get("department"); // Long
        List<Long> departmentCode = (departmentParams != null && !departmentParams.isEmpty()) ? departmentParams.stream().map(Long::parseLong).toList() : List.of((long) -1);

        List<String> themeParams = (List<String>) data.get("themes");
        List<Long> theme = (themeParams != null && !themeParams.isEmpty()) ? themeParams.stream().map(Long::parseLong).toList() : List.of((long) -1);

        String strDateFrom = (String) data.get("from");
        String strDateTo = (String) data.get("till");

        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerIds,
                orientationParams,
                departmentCode,
                theme,
                strDateFrom,
                strDateTo);

        Integer dateFrom = null;
        Integer dateTo = null;

        if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()){
            dateFrom = Integer.valueOf(strDateFrom);
            dateTo   = Integer.valueOf(strDateTo);
        }

        log.info("date from and to: {} and {}", dateFrom, dateTo );

//        String stringForQuery = theme.stream()
//                .map(word -> "%" + word + "%").collect(Collectors.joining("|"));

        List<StudentLecturersDTO> res = studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartmentIds(
                orientationCodes,
                departmentCode,
                dateFrom,
                dateTo,
                theme,
                lecturerIds
        );
        log.info("The result is {} empty", res.isEmpty());
        List<SqlPayload> payloads = res.stream().map(sl -> {
            log.info("In map function with data: {}", sl.getLecturer().getName().toString());
            return SqlPayload.builder()
                    .id(sl.getId())
                    .fullLecturerName(sl.getLecturer().getName())
                    .academicDegree(sl.getLecturer().getAcademicDegree())
                    .position(sl.getLecturer().getPosition())
                    .department(sl.getLecturer().getDepartment().getName())
                    .fullStudentName(sl.getStudent().getName())
                    .studNum(sl.getStudent().getStud_num())
                    .citizenship(sl.getStudent().getCitizenship())
                    .theme(sl.getStudent().getFqw().getName())
                    .build();
        }).toList();

        log.info("The result is {} empty", res.isEmpty());

        log.info("Async getLecturer task has completed");
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK).body(gson.toJson(payloads)));
    }

    @Override
    public String createLectureForm(Model model, String year, HttpServletRequest request) {
        List<StudentLecturersDTO> list =  studentLecturersService.findAllAndSortedByDate(year);

        if (list.isEmpty()){
            return "redirect:/sql/lecturers";
        }

        request.setAttribute("flag", true);
        request.setAttribute(params.get(5), lecturerService.createDummyLecturer());
        request.setAttribute(params.get(6), list);
        return "forward:/sql/lecturers/view";
    }

    @Override
    public String getLecturers(HttpServletRequest request, Model model) {
        String[] lecturerParams = request.getParameterValues("lecturer"); // Long
        List<Long> lecturerIds = (lecturerParams != null) ? Arrays.stream(lecturerParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] orientationParams = request.getParameterValues("orientation");
        List<String> orientationCodes = (orientationParams != null) ? Arrays.asList(orientationParams) : List.of("-1");
        String[] departmentParams = request.getParameterValues("department"); // Long
        List<Long> departmentCode = (departmentParams != null) ? Arrays.stream(departmentParams).map(Long::parseLong).toList() : List.of((long) -1);
        String[] themeParams = request.getParameterValues("themes");
        List<Long> theme = (themeParams != null) ? Arrays.stream(themeParams).map(Long::parseLong).toList() : List.of((long) -1);
        String strDateFrom =request.getParameter("from");
        String strDateTo =request.getParameter("to");

        log.info("lecturer: {} orientation: {} department: {} theme: {} DateFrom: {} DateTo: {}",
                lecturerIds,
                orientationParams,
                departmentCode,
                theme,
                strDateFrom,
                strDateTo);

        Integer dateFrom = null;
        Integer dateTo = null;
        if (strDateFrom != null && strDateTo != null) {
            if (!strDateFrom.isEmpty() && !strDateTo.isEmpty()) {
                dateFrom = Integer.valueOf(strDateFrom);
                dateTo = Integer.valueOf(strDateTo);
            }
        }

        log.info("date from and to: {} and {}", dateFrom, dateTo );

        String stringForQuery = String.join("||",
                theme.stream()
                        .map(word -> "%" + word + "%").collect(Collectors.toList())
        ); // TODO convert list to sql format string for comparisons

        List<StudentLecturersDTO> res = studentLecturersService.findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
                orientationCodes,
                departmentCode,
                dateFrom,
                dateTo,
                theme,
                lecturerIds
        );

        model.addAttribute(params.get(6), res);
        log.info("The result is {} empty", res.isEmpty());

        model.addAttribute(params.getFirst(), lecturerDTOS);
        model.addAttribute(params.get(1), departmentDTOS);
        model.addAttribute(params.get(2), orientationDTOS);
        model.addAttribute(params.get(3), fqwdtos);

        model.addAttribute("flag", true);

        if (lecturerIds.getFirst() == -1){
            model.addAttribute(params.get(5), lecturerService.createDummyLecturer());
            return lecturerTemplate;
        }
        // TODO make one or ALL
        // todo or suggestion: EXCEL API to SAVE Object to Repository
        // TODO api for android client

        return lecturerTemplate;
    }

    @Override
    @Scheduled(fixedRate = 60000) // 60 * 1000 = 1 min
    public void refreshData(){
        log.info("Refreshing data...");
        lecturerDTOS = lecturerService.getConnectedLecturers();
        departmentDTOS = departmentService.getAllDepartments();
        orientationDTOS = orientationService.getAllOrientations();
        fqwdtos = fqwService.getAllFQW();
        log.info("Data refreshed successfully.");
    }

    @Override
    public String getDetailPage(HttpServletRequest request, Model model, String id) {
        StudentLecturersDTO res = studentLecturersService.findStudentLecturersDTOById(Long.valueOf(id));

        model.addAttribute("detail", res);
        return "detail";
    }

    @Override
    public String getDepartmentNameByStudentNumber(Long studNumber) {
        return departmentService.findDepartmentByStudentNumber(studNumber);
    }

    @Override
    public String getOrientationCodeWithNameByStudentNumber(Long studNumber) {
        return orientationService.OrientationCodeWithNameByStudentNumber(studNumber);
    }

    // Студент (FullName, studNum, language) // FQW (theme), Lecturer (SuData=position+academicPos, SuName),

    @Override
    public ResponseEntity<String> saveDataFromProtocol(Map<String, Object> data) {
        /*
            + 1	    FullName Student.fullName
            + 2	    StudNum Student.studNum
            + 3	    Theme       FQW.name
            + 4	    SuData      Lecturer.?
            + 5     SuName      Lecturer.name
            +- 11	Questioner1 Question.questioner
            +- 12	Question1   Question.question
            +- 13	Questioner2 Question.questioner
            +- 14	Question2   Question.question
            +- 15	Questioner3 Question.questioner
            +- 16	Question3   Question.question
            + 21	Score       Protocol.grade
            + 20	IndividualOpinion FQW.feedback
            + 24	Language    Protocol.language
            + 29    Department  Department.name
            + 30    Orientation Orientation (code(2,1,2,1,2 = 8) + " " + name
            + 31    Citizenship Student.citizenship
            ? 32    Program
         */
        try {
            log.info("Data contains: {}", data);
            String code = "?";
            String name = "?";
            String orientationParse = data.get("Orientation").toString();
            if (!orientationParse.contains("?") && orientationParse.length() > 8) {
                code = orientationParse.substring(0, 8);
                name = orientationParse.substring(9).trim();
            }
            Orientation orientation = code.equals("?") ? null : orientationService.getOrientation(code);
            if (orientation == null) {
                orientation = new Orientation();
                orientation.setCode(code);
                orientation.setName(name);
            }


            FQW fqw = fqwService.getFqwByName((String) data.get("Theme"));
            fqw.setName((String) data.get("Theme"));
            fqw.setFeedback((String) data.get("IndividualOpinion"));

            Student student = studentService.findStudentByStudNum((Long) data.get("StudNum"));
            if (student == null) {
                student = new Student();
                student.setStud_num((Long) data.get("StudNum"));
                student.setName((String) data.get("FullName"));
                student.setCitizenship((String) data.get("Citizenship"));
            }
            student.setFqw(fqw);

            String[] names = (String[]) data.get("Names").toString().split(";");
            if (names.length > 1) {
                for (String s : names) {

                }
            }
            Lecturer lecturer = lecturerService.findByLecturerName((String) data.get("SuName"));
            if (lecturer == null) {
                lecturer = new Lecturer();
                lecturer.setName((String) data.get("SuName"));
                List<String> dat = List.of(String.valueOf(data.get("SuData")).split(","));
                String adPdep = "";
                String pos = "";
                if (dat.size() == 2) {
                    adPdep = dat.getFirst();
                    pos = dat.getLast();
                } else if (dat.size() == 3) {
                    adPdep = dat.getFirst();
                    pos = dat.get(1) + dat.getLast();
                }
                lecturer.setPosition(pos);
                lecturer.setAcademicDegree(adPdep);
            }

            StudentLecturers studentLecturers = studentLecturersService.findStudentLecturersByStudentStudNum(student.getStud_num());
            if (studentLecturers == null) {
                studentLecturers = new StudentLecturers();
                studentLecturers.setLecturer(lecturer);
                studentLecturers.setStudent(student);
                studentLecturers.setIsScientificSupervisor(true);
            }

            student.getLecturers().add(studentLecturers);
            lecturer.getStudents().add(studentLecturers);

            Protocol protocol = new Protocol();
            protocol.setStudent(student);
            protocol.setFqw(fqw);
            protocol.setHeadOfTheFQW(lecturer.getName());
            protocol.setGrade(data.get("Score").equals("?") ? -1 : Integer.parseInt(data.get("Score").toString().substring(0, 3).trim()));
            protocol.setLanguage((String) data.get("Language"));

            Question question1 = new Question();
            question1.setQuestioner((String) data.get("Questioner1"));
            question1.setQuestion((String) data.get("Question1"));

            Question question2 = new Question();
            question2.setQuestioner((String) data.get("Questioner2"));
            question2.setQuestion((String) data.get("Question1"));

            Question question3 = new Question();
            question3.setQuestioner((String) data.get("Questioner3"));
            question3.setQuestion((String) data.get("Question3"));

            ProtocolQuestion pq1 = new ProtocolQuestion();
            pq1.setProtocol(protocol);
            pq1.setQuestion(question1);
            question1.getProtocolQuestion().add(pq1);
            protocol.getQuestions().add(pq1);

            ProtocolQuestion pq2 = new ProtocolQuestion();
            pq2.setProtocol(protocol);
            pq2.setQuestion(question2);
            question2.getProtocolQuestion().add(pq2);
            protocol.getQuestions().add(pq2);

            ProtocolQuestion pq3 = new ProtocolQuestion();
            pq3.setProtocol(protocol);
            pq3.setQuestion(question3);
            question3.getProtocolQuestion().add(pq3);
            protocol.getQuestions().add(pq3);

            Lecturer finalLecturer = lecturer;
            Student finalStudent = student;
            StudentLecturers finalStudentLecturers = studentLecturers;
            CompletableFuture.runAsync(() -> {
                fqwService.saveFqw(fqw);
                lecturerService.saveLecturer(finalLecturer);
                studentService.saveStudent(finalStudent);
                studentLecturersService.saveStudentLecturers(finalStudentLecturers);
                questionService.saveQuestion(question1);
                questionService.saveQuestion(question2);
                questionService.saveQuestion(question3);
                protocolService.saveProtocol(protocol);
                protocolQuestionService.saveProtocolQuestion(pq1);
                protocolQuestionService.saveProtocolQuestion(pq2);
                protocolQuestionService.saveProtocolQuestion(pq3);
            });
            return ResponseEntity.status(HttpStatus.OK).body("OK");
        } catch (Exception e) {
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> findDepartmentAndOrientationByStudNumber(String studNumber) {
        String dep = departmentService.findDepartmentByStudentNumber(Long.valueOf(studNumber));
        String ori = orientationService.OrientationCodeWithNameByStudentNumber(Long.valueOf(studNumber));
        if (ori == null) {
            ori = "";
        } if (dep == null) {
            dep = "";
        }
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("Department", dep,
                "Orientation", ori));
    }
}
