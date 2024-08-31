package ru.emiren.infosystemdepartment.Controller.SQL;

import com.google.gson.Gson;
import groovy.util.logging.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Util.DateUtil;

import java.time.LocalDate;
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
    private final YearService yearService;
    private final YearStudentService yearStudentService;
    private final ProtectionCommissionerService protectionCommissionerService;


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
                                YearService yearService,
                                YearStudentService yearStudentService,
                                ProtectionCommissionerService protectionCommissionerService) {
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
        this.yearService = yearService;
        this.yearStudentService = yearStudentService;
        this.protectionCommissionerService = protectionCommissionerService;
    }






    @PostMapping("/api/add-data")
    public String AddData(@RequestParam Map<String, String> request){//        BufferedReader reader = request.getReader();

        System.out.println(request.get("isScientificSupervisor"));

        Date dateOfProtection = DateUtil.stringToDate(request.get("dateOfProtection"));

        Student student = createStudent(request);
        Lecturer lecturer = createLecturer(request);
        StudentLecturers sl = createStudentLecturers(request, student, lecturer);
        Reviewer reviewer = createReviewer(request);
        Department department = createDepartment(request);
        Orientation orientation = createOrientation(request);
        FQW fqw = createFQW(request, reviewer);
        Protocol protocol = createProtocol(request);
        Protection protection = createProtection(request, orientation, dateOfProtection);
        Year year = createYear(request, dateOfProtection);
        YearStudent ys = createYearStudent(year, student);
        Commissioner commissioner1 = createCommissioner(request, "1");
        Commissioner commissioner2 = createCommissioner(request, "2");
        Commissioner commissioner3 = createCommissioner(request, "3");
        ProtectionCommissioner pc1 = createProtectionCommissioner(request, protection, commissioner1, "1");
        ProtectionCommissioner pc2 = createProtectionCommissioner(request, protection, commissioner2, "2");
        ProtectionCommissioner pc3 = createProtectionCommissioner(request, protection, commissioner3, "3");

        protection.setCommissioners(List.of(pc1, pc2, pc3));

        student.setDepartment(department);
        student.setOrientation(orientation);
        student.setYearStudents(List.of(ys));
        student.setFqw(fqw);
        student.setLecturers(List.of(sl));
        lecturer.setDepartment(department);
        lecturer.setStudents(List.of(sl));
        orientation.setProtection(List.of(protection));
        year.setStudents(List.of(ys));
        student.setYearStudents(List.of(ys));


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
        yearService.saveYear(year);
        yearStudentService.saveYearStudent(ys);
        protectionCommissionerService.saveProtectionCommissioner(pc1);
        protectionCommissionerService.saveProtectionCommissioner(pc2);
        protectionCommissionerService.saveProtectionCommissioner(pc3);



        return "redirect:/upload-data-form";
    }




    private static Student createStudent(Map<String, String> request) {
        String studName = request.get("studName");
        Long studNum = Long.parseLong(request.get("studNum"));
        String citizenship = request.get("citizenship");
        String loe = request.get("loe");
        String classifier = request.get("classifier");

        Student student = new Student();
        student.setName(studName);
        student.setStud_num(studNum);
        student.setCitizenship(citizenship);
        student.setLoe(loe);
        student.setClassifier(classifier);

        return student;
    }

    private static Lecturer createLecturer(Map<String, String> request) {
        String lecturersName = request.get("lecturersName");
        String academicPosition = request.get("academicPosition");
        String position = request.get("position");
        String departmentName = request.get("departmentName");

        Lecturer lecturer = new Lecturer();
        lecturer.setName(lecturersName);
        lecturer.setAcademicDegree(academicPosition);
        lecturer.setPosition(position);

        Department department = new Department();
        department.setName(departmentName);
        lecturer.setDepartment(department);

        return lecturer;
    }

    private static StudentLecturers createStudentLecturers(Map<String, String> request, Student student, Lecturer lecturer) {
        boolean isScientificSupervisor = Boolean.parseBoolean(request.get("isScientificSupervisor"));
        boolean isConsultant = Boolean.parseBoolean(request.get("isConsultant"));

        StudentLecturers sl = new StudentLecturers();
        sl.setStudent(student);
        sl.setLecturer(lecturer);
        sl.setIsScientificSupervisor(isScientificSupervisor);
        sl.setIsConsultant(isConsultant);

        lecturer.setStudents(List.of(sl));

        return sl;
    }

    private static Reviewer createReviewer(Map<String, String> request) {
        String reviewerName = request.get("reviewerName");
        String reviewerAD = request.get("reviewerAD");
        String reviewerPos = request.get("reviewerPos");

        Reviewer reviewer = new Reviewer();
        reviewer.setName(reviewerName);
        reviewer.setPosition(reviewerPos);
        reviewer.setAcademicDegree(reviewerAD);

        return reviewer;
    }

    private static Department createDepartment(Map<String, String> request) {
        String departmentName = request.get("departmentName");

        Department department = new Department();
        department.setName(departmentName);

        return department;
    }

    private static Orientation createOrientation(Map<String, String> request) {
        String orientationCode = request.get("orientationCode");
        String orientationName = request.get("orientationName");

        Orientation orientation = new Orientation();
        orientation.setCode(orientationCode);
        orientation.setName(orientationName);

        return orientation;
    }

    private static FQW createFQW(Map<String, String> request, Reviewer reviewer) {
        String themeName = request.get("themeName");
        float uniqueness = Float.parseFloat(request.get("uniqueness"));
        String feedback = request.get("feedback");
        String volume = request.get("volume");

        FQW fqw = new FQW();
        fqw.setName(themeName);
        fqw.setUniqueness(uniqueness);
        fqw.setFeedback(feedback);
        fqw.setVolume(volume);
        fqw.setReviewer(reviewer);

        return fqw;
    }

    private static Protocol createProtocol(Map<String, String> request) {
        Protocol protocol = new Protocol();

        protocol.setFioStudent(request.get("studName"));
        protocol.setVolume(Integer.parseInt(request.get("volume")));
        protocol.setHeadOfTheFQW(request.get("headOfTheFQW"));
        protocol.setReview(request.get("review"));
        protocol.setGrade(Integer.parseInt(request.get("grade")));
        protocol.setFqwName(request.get("themeName"));
        return protocol;
    }

    private static Protection createProtection(Map<String, String> request, Orientation orientation, Date dateOfProtection) {
        Protection protection = new Protection();

        String yearStr = DateUtil.getYear(dateOfProtection);
        String month = DateUtil.getMonth(dateOfProtection);
        String day = DateUtil.getDay(dateOfProtection);

        protection.setOrientation(orientation);
        protection.setDateOfProtection(LocalDate.of(
                Integer.parseInt(yearStr), Integer.parseInt(month), Integer.parseInt(day)
        ));

        return protection;
    }

    private static Year createYear(Map<String, String> request, Date dateOfProtection) {
        Year year = new Year();
        String yearStr = DateUtil.getYear(dateOfProtection);
        year.setYear(yearStr);
        return year;
    }

    private static YearStudent createYearStudent(Year year, Student student) {
        YearStudent ys = new YearStudent();
        ys.setYear(year);
        ys.setStudent(student);
        return ys;
    }

    private static Commissioner createCommissioner(Map<String, String> request, String commissionerNumber) {
        String commissionerName = request.get("commissionerName" + commissionerNumber);
        String commissionerUniv = request.get("commissionerUniv" + commissionerNumber);
        String commissionerDep = request.get("commissionerDep" + commissionerNumber);

        Commissioner commissioner = new Commissioner();
        commissioner.setName(commissionerName);
        commissioner.setUniversity(commissionerUniv);
        commissioner.setDepartment(commissionerDep);

        return commissioner;
    }

    private static ProtectionCommissioner createProtectionCommissioner(Map<String, String> request, Protection protection, Commissioner commissioner, String questionNumber) {
        String question = request.get("question" + questionNumber);

        ProtectionCommissioner pc = new ProtectionCommissioner();
        pc.setProtection(protection);
        pc.setCommissioner(commissioner);
        pc.setQuestion(question);

        return pc;
    }
}
