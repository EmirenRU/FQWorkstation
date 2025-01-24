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
        this.protectionCommissionerService = protectionCommissionerService;
    }

    @PostMapping("/api/add-data")
    public String addData(@RequestBody Map<String, String> request){
        log.info(request.toString());
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
        ProtectionCommissioner pc1 = createProtectionCommissioner(request, protection, commissioner1, "1");
        ProtectionCommissioner pc2 = createProtectionCommissioner(request, protection, commissioner2, "2");
        ProtectionCommissioner pc3 = createProtectionCommissioner(request, protection, commissioner3, "3");

        protection.setCommissioners(List.of(pc1, pc2, pc3));

        student.setDepartment(department);
        student.setOrientation(orientation);
        student.setFqw(fqw);
        student.setLecturers(List.of(sl));
        lecturer.setDepartment(department);
        lecturer.setStudents(List.of(sl));
        orientation.setProtection(List.of(protection));


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


    private static String studNameStr = "studName";

    private static Student createStudent(Map<String, String> request) {
        String studName = request.get(studNameStr);
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

    private Protocol createProtocol(Map<String, String> request) {
        Protocol protocol = new Protocol();

        Student student = studentService.findStudentByName(request.get(studNameStr));
        if (student == null) {
            student = new Student();
            student.setName(request.get(studNameStr));
            studentService.saveStudent(student);
        }

        FQW fqw = fqwService.getFQW(request.get("themeName"));
        if (fqw == null) {
            fqw = new FQW();
            fqw.setName(request.get("fqwName"));
            fqwService.saveFqw(fqw);
        }

        protocol.setStudent(student);
        protocol.setVolume(Integer.parseInt(request.get("volume")));
        protocol.setHeadOfTheFQW(request.get("headOfTheFQW"));
        protocol.setReview(request.get("review"));
        protocol.setGrade(Integer.parseInt(request.get("grade")));
        protocol.setFqw(fqw);
        return protocol;
    }

    private static Protection createProtection(Orientation orientation, Date dateOfProtection) {
        Protection protection = new Protection();

        String yearStr = DateUtil.getYear(dateOfProtection);

        protection.setOrientation(orientation);
        protection.setDateOfProtection(
                Integer.parseInt(yearStr)
        );

        return protection;
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

        return pc;
    }
}
