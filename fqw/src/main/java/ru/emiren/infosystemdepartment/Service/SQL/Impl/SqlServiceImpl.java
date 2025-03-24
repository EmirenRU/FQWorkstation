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
import ru.emiren.infosystemdepartment.DTO.Payload.SelectorSqlPayload;
import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.*;
import ru.emiren.infosystemdepartment.Mapper.DepartmentMapper;
import ru.emiren.infosystemdepartment.Mapper.FQWMapper;
import ru.emiren.infosystemdepartment.Mapper.LecturerMapper;
import ru.emiren.infosystemdepartment.Mapper.OrientationMapper;
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
    private final DecreeService decreeService;
    private final Gson gson;
    private List<LecturerDTO> lecturerDTOS;
    private List<OrientationDTO> orientationDTOS;
    private List<DepartmentDTO> departmentDTOS;
    private List<FQWDTO> fqwdtos;
    private List<SqlPayload> sqlDataPayload;


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
                          ProtocolService protocolService, DecreeService decreeService,
                          Gson gson){
        this.studentService             = studentService;
        this.departmentService          = departmentService;
        this.lecturerService            = lecturerService;
        this.orientationService         = orientationService;
        this.studentLecturersService    = studentLecturersService;
        this.fqwService                 = fqwService;
        this.decreeService              = decreeService;


        dateTimeFormatter  = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        this.protocolQuestionService = protocolQuestionService;
        this.questionService = questionService;
        this.protocolService = protocolService;
        this.gson = gson;
        updateSqlDataPayload();
    }


    /**
     * transporting all data from SL to SqlPayload for React transaction
     * @return a CompletableFuture (Async) with ResponseEntity's header and body json
     * @deprecated not required anymore
     */
    @Async
    @Override
    @Deprecated(forRemoval = true)
    public CompletableFuture<ResponseEntity<String>> receiveLecturers() {
        List<SqlPayload> res = studentLecturersService.getAllStudentLecturers();
        Map<String, String> headers = new HashMap<>(Map.of("Content-Type", "application/json"));
        Gson gsonBeautify = new GsonBuilder().setPrettyPrinting().create();
        return CompletableFuture.completedFuture(ResponseEntity.status(HttpStatus.OK)
                                                                .header(headers.toString())
                                                                .body(gsonBeautify.toJson(res)));
    }

    /**
     * receive all FQW data
     *
     * @param request a client's headers and request
     * @return a CompletableFuture (Async) with ResponseEntity's header and body json
     * @deprecated not required anymore
     */
    @Async
    @Override
    @Deprecated(forRemoval = true)
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
                    .theme(sl.getStudent().getFqw().getDecree().getTheme())
                    .numberOfDecree(sl.getStudent().getFqw().getDecree().getNumberOfDecree())
                    .build();
        }).toList();

        log.info("The result is {} empty", res.isEmpty());
        log.info("The result is {}", res);
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
    public SelectorSqlPayload receiveSelectors() {
        return new SelectorSqlPayload(
                departmentDTOS.stream().map(DepartmentMapper::mapToSelector).toList(),
                orientationDTOS.stream().map(OrientationMapper::mapToSelector).toList(),
                lecturerDTOS.stream().map(LecturerMapper::mapToSelector).toList(),
                fqwdtos.stream().map(FQWMapper::mapToSelector).toList());
    }

    @Override
    public ResponseEntity<String> getAllDataAsPayload() {
        return ResponseEntity.status(HttpStatus.OK).body(gson.toJson(sqlDataPayload));
    }

    @Scheduled(cron = "0 */2 * * * *")
    @Override
    @Async
    public void updateSqlDataPayload(){
        log.info("Updating SQL data payload...");
         sqlDataPayload = studentLecturersService.getAllStudentLecturers();
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
//    @Transactional(propagation = Propagation.REQUIRES_NEW)
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
            if (!orientationParse.trim().equals("?") && orientationParse.length() > 8) {
                code = orientationParse.substring(0, 8);
                name = orientationParse.substring(9).trim();
            }
            Long studNum = (!data.get("StudNum").toString().equals("?")) ? Long.valueOf(data.get("StudNum").toString()) : 0l;
            log.info("1");
            Orientation orientation = code.equals("?") ? null : orientationService.getOrientation(code);
            if (orientation == null) {
                orientation = new Orientation();
                orientation.setCode(code);
                orientation.setName(name);
                orientationService.saveOrientation(orientation);
            }
            log.info("2");

            Decree decree = decreeService.findDecreeByThemeAndNumberOfDecreeAndStudNum((Long) data.get("studNum"), (String) data.get("Theme"), (String) data.get("NumberOfDecree"));
            if (decree == null && !((String) data.get("Theme")).trim().isEmpty()) {
                decree = new Decree();
                decree.setTheme((String) data.get("Theme"));
                decree.setNumberOfDecree((String) data.get("NumberOfDecree"));
                decree.setStudNum(studNum);
                decreeService.saveDecree(decree);
            }


            FQW fqw = fqwService.getFqwByName((String) data.get("Theme"));
            if (fqw == null && !((String) data.get("Theme")).trim().isEmpty()) {
                fqw = new FQW();
                fqw.setDecree(decree);
                fqw.setFeedback((String) data.get("IndividualOpinion"));
                fqwService.saveFqw(fqw);

            }

            log.info("3");


            Student student = studentService.findStudentByStudNum(studNum);
            if (student == null) {
                student = new Student();
                Long id = studentService.getMaxId();
                if (id != null) {student.setId(id +1);}
                student.setStud_num(studNum);
                student.setName((String) data.get("FullName"));
                student.setCitizenship((String) data.get("Citizenship"));
                if (fqw != null &&!fqw.getDecree().getTheme().isEmpty() && fqw.getDecree().getTheme() != null) {
                    student.setFqw(fqw);
                }
                studentService.saveStudent(student);
            }
            log.info("4");

            String headOfSU = "";
            String[] suNames = ((String)data.get("SuName")).replaceAll(".\n", ".,").replaceAll("\n", "").replaceAll(";", ",").split(",");
            if (suNames.length >= 1) {
                String[] datOr = ((String) data.get("SuData")).split(";");
                for (int i = 0; i < suNames.length; i++) {
                    if (i == 0){
                        headOfSU = suNames[i];
                    }
                    Lecturer lecturer = lecturerService.findByLecturerName(suNames[i].trim());
                    if (lecturer == null) {
                        lecturer = new Lecturer();
                        Long id = lecturerService.getMaxId();
                        log.info("id is {}", id);

                        lecturer.setName(suNames[i].trim());
                        List<String> dat;
                        if (datOr.length > 1) {
                            dat = List.of(String.valueOf(datOr[i]).split(","));
                        } else {
                            dat = List.of(String.valueOf(data.get("SuData")).split(","));
                        }
                        log.info("dat is {}", dat);
                        String adPdep = "";
                        String pos = "";
                        if (dat.size() == 2) {
                            adPdep = dat.getFirst().trim();
                            pos = dat.getLast().replaceAll(adPdep, ",").trim();
                        } else if (dat.size() == 3) {
                            log.info("dat: {}", dat.stream().toList());
                            adPdep = dat.getFirst().trim();
                            pos = (dat.getLast()).trim();
                        } else if (dat.size() == 1) {
                            adPdep = "";
                            pos = "";
                        }


                        if (pos != null)
                            lecturer.setPosition(pos.trim());
                        if (adPdep != null)
                            lecturer.setAcademicDegree(adPdep.trim());
                        if (suNames[i].contains("Консультант") || suNames[i].contains("консультант ")){
                            lecturer.setName(suNames[i].replace("консультант", "").replace("Консультант ", "").trim());
                            lecturer.setPosition("Консультант");
                        }
                        log.info("pre-saving the lecturer: {} {}", lecturer.getName(), lecturer.getPosition());
                        lecturerService.saveLecturer(lecturer);
                    }

                    StudentLecturers studentLecturers = studentLecturersService.findStudentLecturersByStudentStudNum(student.getStud_num(), lecturer.getName());
                    if (studentLecturers == null) {
                        studentLecturers = new StudentLecturers();
                        Long id = studentLecturersService.getMaxId();
                        if (id != null) {studentLecturers.setId(id +1);}
                        studentLecturers.setLecturer(lecturer);
                        studentLecturers.setStudent(student);
                        studentLecturers.setIsScientificSupervisor(true);
                        studentLecturersService.saveStudentLecturers(studentLecturers);
                    }
                }
            }



//            student.getLecturers().add(studentLecturers);
//            lecturer.getStudents().add(studentLecturers);

            log.info("p");

            Protocol protocol = protocolService.findByStudentNum(student.getStud_num());
            if (protocol == null) {
                protocol = new Protocol();
                Long id = protocolService.getMaxId();
                if (id != null) {protocol.setId(id +1);}
                protocol.setStudent(student);
                fqw = fqwService.findByName((String) data.get("Theme"));
                if (fqw != null && !fqw.getDecree().getTheme().trim().isEmpty() && fqw.getDecree().getTheme() != null) {
                    protocol.setFqw(fqw);
                }
                protocol.setHeadOfTheFQW(headOfSU);
                int scoreNum;
                if (data.get("Score") != null && !((String) data.get("Score")).isEmpty()) {
                    String scoreDouble = data.get("Score").toString().substring(0, 3).trim(); // [0; 100]
                    if (scoreDouble.contains(".")) {
                        String score = scoreDouble.substring(0, scoreDouble.indexOf("."));
                        scoreNum = Integer.parseInt(score);
                    } else {
                        scoreNum = -1;
                    }
                    protocol.setGrade(scoreNum);
                }
                protocol.setLanguage((String) data.get("Language"));
                protocolService.saveProtocol(protocol);
            }
            log.info("q");


            Long id = questionService.getMaxId();
            if (id == null){ id = 0l; }
            Question question1 = questionService.findQuestion((String) data.get("Question1"));
            if (question1 == null && !(((String) data.get("Question1")).trim().isEmpty()) && ((String) data.get("Question1")) != null){
                question1 = new Question();
                id++;
                question1.setId(id);
                question1.setQuestioner((String) data.get("Questioner1"));
                question1.setQuestion((String) data.get("Question1"));
                questionService.saveQuestion(question1);

            }

            Question question2 = questionService.findQuestion((String) data.get("Question2"));
            if (question2 == null && !(((String) data.get("Question2")).trim().isEmpty()) && ((String) data.get("Question2")) != null) {
                question2 = new Question();
                id++;
                question2.setId(id);
                question2.setQuestioner((String) data.get("Questioner2"));
                question2.setQuestion((String) data.get("Question2"));
                questionService.saveQuestion(question2);
            }

            Question question3 = questionService.findQuestion((String) data.get("Question3"));
            if (question3 == null && !(((String) data.get("Question3")).trim().isEmpty()) && ((String) data.get("Question3")) != null) {
                question3 = new Question();
                id++;
                question3.setId(id);
                question3.setQuestioner((String) data.get("Questioner3"));
                question3.setQuestion((String) data.get("Question3"));
                questionService.saveQuestion(question3);
            }
            log.info("pq");


            if (question1 != null) {
                ProtocolQuestion pq1 = protocolQuestionService.findByQuestionAndProtocolStudent(question1.getQuestion(), protocol.getStudent().getStud_num(), question1.getQuestioner());
                id = protocolQuestionService.getMaxId();
                if (id == null) {
                    id = 0l;
                }
                if (pq1 == null && question1 != null && question1.getQuestion() != null) {
                    pq1 = new ProtocolQuestion();
                    id++;
                    pq1.setId(id);
                    pq1.setProtocol(protocol);
                    pq1.setQuestion(question1);
                    question1.getProtocolQuestion().add(pq1);
                    protocol.getQuestions().add(pq1);
                    protocolQuestionService.saveProtocolQuestion(pq1);
                }
            }
            if (question2 != null) {
                ProtocolQuestion pq2 = protocolQuestionService.findByQuestionAndProtocolStudent(question2.getQuestion(), protocol.getStudent().getStud_num(), question2.getQuestioner());
                if (pq2 == null && question2 != null && question2.getQuestion() != null) {
                    pq2 = new ProtocolQuestion();
                    id++;
                    pq2.setId(id);
                    pq2.setProtocol(protocol);
                    pq2.setQuestion(question2);
                    question2.getProtocolQuestion().add(pq2);
                    protocol.getQuestions().add(pq2);
                    protocolQuestionService.saveProtocolQuestion(pq2);
                }
            }
            if (question3 != null) {
                ProtocolQuestion pq3 = protocolQuestionService.findByQuestionAndProtocolStudent(question3.getQuestion(), protocol.getStudent().getStud_num(), question3.getQuestioner());
                if (pq3 == null && question3 != null && question3.getQuestion() != null) {
                    pq3 = new ProtocolQuestion();
                    id++;
                    pq3.setId(id);
                    pq3.setProtocol(protocol);
                    pq3.setQuestion(question3);
                    question3.getProtocolQuestion().add(pq3);
                    protocol.getQuestions().add(pq3);
                    protocolQuestionService.saveProtocolQuestion(pq3);
                }
            }
            log.info("end");


            log.info("Saving has ended");
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
