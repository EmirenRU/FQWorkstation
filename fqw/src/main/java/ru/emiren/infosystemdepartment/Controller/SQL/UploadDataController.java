package ru.emiren.infosystemdepartment.Controller.SQL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Util.DateUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
@Slf4j
public class UploadDataController {

    private final StudentService studentService;
    private final LecturerService lecturerService;
    private final OrientationService orientationService;
    private final CommissionerService commissionerService;
    private final StudentLecturersService studentLecturersService;
    private final ReviewerService reviewerService;
    private final DepartmentService departmentService;
    private final ProtectionService protectionService;
    private final FQWService fqwService;
    private final ProtocolService protocolService;
    private final ProtectionCommissionerService protectionCommissionerService;
    private final ProtocolQuestionService protocolQuestionService;
    private final QuestionService questionService;

    @Autowired
    public UploadDataController(StudentService studentService,
                                LecturerService lecturerService,
                                OrientationService orientationService,
                                CommissionerService commissionerService,
                                StudentLecturersService studentLecturersService,
                                ReviewerService reviewerService,
                                DepartmentService departmentService,
                                ProtectionService protectionService,
                                FQWService fqwService,
                                ProtocolService protocolService,
                                ProtectionCommissionerService protectionCommissionerService, ProtocolQuestionService protocolQuestionService, QuestionService questionService) {
        this.studentService = studentService;
        this.lecturerService = lecturerService;
        this.orientationService = orientationService;
        this.commissionerService = commissionerService;
        this.studentLecturersService = studentLecturersService;
        this.reviewerService = reviewerService;
        this.departmentService = departmentService;
        this.protectionService = protectionService;
        this.fqwService = fqwService;
        this.protocolService = protocolService;
        this.protectionCommissionerService = protectionCommissionerService;
        this.protocolQuestionService = protocolQuestionService;
        this.questionService = questionService;
    }

    /**
     * Handle for upload data form.
     * I recommend to not touch.
     * @param request
     * @return a redirection to the same page
     */
    @PostMapping("/api/add-data")
    public String addData(@RequestBody Map<String, String> request){
        log.info(request.toString());
        /*
        name=a, studnum=13, citizenship=a, education=a,
        clasifyer=a, code=02.03.01, naming=a,
        studdate=2025-02-04T13:36:18.888Z,
        teachers_name=a, degree=a, postion=a, department_name=a, is_supervisor=true, is_consultant=false, subject=a, originality=1, review=a, volume=a, FQWSupervisor=a, critique=a, assessment=a, protocolVolume=a, ReviewerName=a, ReviewerDegree=a, ReviewerDuty=a, comissionerName1=a, comissionerName2=b, comissionerName3=c, comissionerUniversity1=a, comissionerUniversity2=b, comissionerUniversity3=c, comissionerDepartment1=a, comissionerDepartment2=b, comissionerDepartment3=c, comissionerQuestion1=a, comissionerQuestion2=b, comissionerQuestion3=c}
         */
        log.info(request.get("isScientificSupervisor")+ " " + request.get("dateOfProtection"));

        Date dateOfProtection = DateUtil.stringToDate(request.get("dateOfProtection"));

        Student student = createStudent(request);
        Lecturer lecturer = createLecturer(request);
        StudentLecturers sl = createStudentLecturers(request, student, lecturer);
        Reviewer reviewer = createReviewer(request);
        Department department = createDepartment(request);
        Orientation orientation = createOrientation(request);
        FQW fqw = createFQW(request, reviewer);
        Protocol protocol = createProtocol(request);
        Protection protection = createProtection(orientation, dateOfProtection);
        Commissioner commissioner1 = createCommissioner(request, "1");
        Commissioner commissioner2 = createCommissioner(request, "2");
        Commissioner commissioner3 = createCommissioner(request, "3");
        ProtectionCommissioner pc1 = createProtectionCommissioner(protection, commissioner1);
        ProtectionCommissioner pc2 = createProtectionCommissioner(protection, commissioner2);
        ProtectionCommissioner pc3 = createProtectionCommissioner(protection, commissioner3);
        Question q1 = createQuestion(request, "1", commissioner1);
        Question q2 = createQuestion(request, "2", commissioner3);
        Question q3 = createQuestion(request, "3", commissioner2);
        ProtocolQuestion pq1 = createProtocolQuestion(request, protocol, q1);
        ProtocolQuestion pq2 = createProtocolQuestion(request, protocol, q2);
        ProtocolQuestion pq3 = createProtocolQuestion(request, protocol, q3);

        protection.getCommissioners().addAll(List.of(pc1, pc2, pc3));
        protocol.getQuestions().addAll(List.of(pq1, pq2, pq3));
        protocol.setStudent(student);

        student.setDepartment(department);
        student.setOrientation(orientation);
        student.setFqw(fqw);
        student.getLecturers().add(sl);
        lecturer.setDepartment(department);
        lecturer.getStudents().add(sl);
        lecturer.setDepartment(department);
        orientation.setProtection(List.of(protection));
        orientation.getProtection().add(protection);

        protection.setOrientation(orientation);
        fqw.setReviewer(reviewer);

        // TODO rewrite the mappers
        studentService.saveStudent(student);
        lecturerService.saveLecturer(lecturer);
        studentLecturersService.saveStudentLecturers(sl);
        reviewerService.saveReviewer(reviewer);
        departmentService.saveDepartment(department);
        orientationService.saveOrientation(orientation);
        fqwService.saveFqw(fqw);
        commissionerService.saveCommissioner(commissioner1);
        commissionerService.saveCommissioner(commissioner2);
        commissionerService.saveCommissioner(commissioner3);
        protocolService.saveProtocol(protocol);
        protectionService.saveProtection(protection);
        protectionCommissionerService.saveProtectionCommissioner(pc1);
        protectionCommissionerService.saveProtectionCommissioner(pc2);
        protectionCommissionerService.saveProtectionCommissioner(pc3);

        return "redirect:/upload-data-form";
    }



    private Student createStudent(Map<String, String> request) {
        Student student = studentService.findStudentByStudNum(Long.parseLong(request.get("studNum")));
        if (student == null) {
            student = new Student();
            student.setName(request.get("name"));
            student.setStud_num(Long.parseLong(request.get("studNum")));
            student.setCitizenship(request.get("citizenship"));
            student.setLoe(request.get("loe"));
            student.setClassifier(request.get("classifier"));
        }
        return student;
    }

    private Lecturer createLecturer(Map<String, String> request) {
        Lecturer lecturer = lecturerService.findByLecturerName(request.get("lecturerName"));
        if (lecturer == null) {
            lecturer = new Lecturer();
            lecturer.setName(request.get("lecturersName"));
            lecturer.setAcademicDegree(request.get("academicPosition"));
            lecturer.setPosition(request.get("position"));
        }
        return lecturer;
    }

    private StudentLecturers createStudentLecturers(Map<String, String> request, Student student, Lecturer lecturer) {
        StudentLecturers studentLecturers = studentLecturersService.findStudentLecturersByStudentStudNumAndLecturerName(student.getStud_num(), lecturer.getName());
        if (studentLecturers == null) {
            studentLecturers = new StudentLecturers();
            studentLecturers.setStudent(student);
            studentLecturers.setLecturer(lecturer);
            studentLecturers.setIsScientificSupervisor(Boolean.parseBoolean(request.get("isScientificSupervisor")));
            studentLecturers.setIsConsultant(Boolean.parseBoolean(request.get("isConsultant")));
        }


        student.getLecturers().add(studentLecturers);
        lecturer.getStudents().add(studentLecturers);

        studentLecturers.setLecturer(lecturer);
        studentLecturers.setStudent(student);
        return studentLecturers;
    }

    private Reviewer createReviewer(Map<String, String> request) {
        Reviewer reviewer = reviewerService.findReviewerByName(request.get("reviewerName"));
        if (reviewer == null) {
            reviewer = new Reviewer();
            reviewer.setName(request.get("reviewerName"));
            reviewer.setPosition(request.get("reviewerPos"));
            reviewer.setAcademicDegree(request.get("reviewerAD"));
        }
        return reviewer;
    }

    private Department createDepartment(Map<String, String> request) {
        Department department = departmentService.findDepartmentByName(request.get("departmentName"));
        if (department == null) {
            department = new Department();
            department.setName(request.get("departmentName"));
        }
        return department;
    }

    private Orientation createOrientation(Map<String, String> request) {
        Orientation orientation = orientationService.findByCode(request.get("orientationCode"));
        if (orientation == null) {
            orientation = new Orientation();
            orientation.setCode(request.get("orientationCode"));
            orientation.setName(request.get("orientationName"));
        }
        return orientation;
    }

    private FQW createFQW(Map<String, String> request, Reviewer reviewer) {
        FQW fqw = fqwService.findByName(request.get("themeName"));
        if (fqw == null) {
            fqw = new FQW();
            fqw.setName(request.get("themeName"));
            fqw.setUniqueness(Float.parseFloat(request.get("uniqueness")));
            fqw.setFeedback(request.get("feedback"));
            fqw.setVolume(request.get("volume"));
            fqw.setReviewer(reviewer);
        }
        return fqw;
    }

    private Protocol createProtocol(Map<String, String> request) {
        Protocol protocol = protocolService.findByStudentNum(Long.parseLong(request.get("studNum")));
        if (protocol == null) {
            protocol = new Protocol();
            protocol.setVolume(Integer.parseInt(request.get("volume")));
            protocol.setHeadOfTheFQW(request.get("headOfTheFQW"));
            protocol.setReview(request.get("review"));
            protocol.setGrade(Integer.parseInt(request.get("grade")));
            protocol.setHeadOfTheFQW(request.get("headOfTheFQW"));
        }
        return protocol;
    }

    private Protection createProtection(Orientation orientation, Date dateOfProtection) {
        Protection protection = new Protection();
        String yearStr = DateUtil.getYear(dateOfProtection);
        protection.setOrientation(orientation);
        protection.setDateOfProtection(Integer.parseInt(yearStr));
        return protection;
    }

    private Commissioner createCommissioner(Map<String, String> request, String commissionerNumber) {
        String commissionerName = request.get("commissionerName" + commissionerNumber);
        String commissionerUniv = request.get("commissionerUniversity" + commissionerNumber);
        String commissionerDep = request.get("commissionerDepartment" + commissionerNumber);


        Commissioner commissioner = commissionerService.findByName(commissionerName);
        if (commissioner == null){
            commissioner = new Commissioner();
            commissioner.setName(commissionerName);
            commissioner.setUniversity(commissionerUniv);
            commissioner.setDepartment(commissionerDep);
        }
        return commissioner;
    }

    private ProtectionCommissioner createProtectionCommissioner(Protection protection, Commissioner commissioner) {
        ProtectionCommissioner pc = new ProtectionCommissioner();
        pc.setProtection(protection);
        pc.setCommissioner(commissioner);
        return pc;
    }

    private Question createQuestion(Map<String, String> request, String questionNumber, Commissioner commissioner ) {
        Question questionObject = questionService.findQuestion(request.get("question" + questionNumber));
        if (questionObject == null) {
            questionObject = new Question();
            questionObject.setQuestion(request.get("question" + questionNumber));
            questionObject.setQuestioner(commissioner.getName());
        }

        return questionObject;
    }

    private ProtocolQuestion createProtocolQuestion(Map<String, String> request, Protocol protocol, Question question) {
        ProtocolQuestion protocolQuestion = protocolQuestionService.findByQuestionAndProtocolStudent(question.getQuestion(), protocol.getStudent().getStud_num());
        if (protocolQuestion == null) {
            protocolQuestion = new ProtocolQuestion();
            protocolQuestion.setQuestion(question);
            protocolQuestion.setProtocol(protocol);
            protocol.getQuestions().add(protocolQuestion);
            question.getProtocolQuestion().add(protocolQuestion);

        }
        return protocolQuestion;
    }
}
