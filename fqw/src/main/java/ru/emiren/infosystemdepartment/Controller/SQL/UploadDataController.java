package ru.emiren.infosystemdepartment.Controller.SQL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Util.DateUtil;

import java.time.Instant;
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
        log.info(request.get("is_supervisor")+ " " + request.get("studdate"));

        Date dateOfProtection = Date.from(Instant.now());
//        Date dateOfProtection = DateUtil.stringToDate(request.get("dateOfProtection"));

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
        Student student = null;
        if (request.get("studnum") != null) {
            Long studNum = Long.parseLong(request.get("studNum"));
            student = studentService.findStudentByStudNum(studNum);
        }
        if (student == null) {
            student = new Student();

            String name = request.get("name");
            if (name != null) {
                student.setName(name);
            } else {
                log.warn("Student name is null");
                student.setName("");
            }

            String studNumStr = request.get("studNum");
            if (studNumStr != null) {
                try {
                    student.setStud_num(Long.parseLong(studNumStr));
                } catch (NumberFormatException e) {
                    log.warn("Student number must be a valid long");
                    student.setStud_num(0L);
                }
            } else {
                log.warn("Student number is null");
                student.setStud_num(0L);
            }

            String citizenship = request.get("citizenship");
            if (citizenship != null) {
                student.setCitizenship(citizenship);
            } else {
                log.warn("Citizenship is null");
                student.setCitizenship("");
            }

            String loe = request.get("loe");
            if (loe != null) {
                student.setLoe(loe);
            } else {
                log.warn("LOE is null");
                student.setLoe("");
            }

            String classifier = request.get("classifier");
            if (classifier != null) {
                student.setClassifier(classifier);
            } else {
                log.warn("Classifier is null");
                student.setClassifier("");
            }
        }
        return student;
    }


    private Lecturer createLecturer(Map<String, String> request) {
        Lecturer lecturer = null;
        if (request.get("lecturerName") != null) {
            lecturer = lecturerService.findByLecturerName(request.get("lecturerName"));
        }
        if (lecturer == null) {
            lecturer = new Lecturer();

            String lecturersName = request.get("lecturersName");
            if (lecturersName != null) {
                lecturer.setName(lecturersName);
            } else {
                log.warn("Lecturer's name is null");
                lecturer.setName("");
            }

            String academicPosition = request.get("academicPosition");
            if (academicPosition != null) {
                lecturer.setAcademicDegree(academicPosition);
            } else {
                log.warn("Academic position is null");
                lecturer.setAcademicDegree("");
            }

            String position = request.get("position");
            if (position != null) {
                lecturer.setPosition(position);
            } else {
                log.warn("Position is null");
                lecturer.setPosition("");
            }
        }
        return lecturer;
    }


    private StudentLecturers createStudentLecturers(Map<String, String> request, Student student, Lecturer lecturer) {
        StudentLecturers studentLecturers = studentLecturersService
                .findStudentLecturersByStudentStudNumAndLecturerName(student.getStud_num(),
                                                                     lecturer.getName());
        if (studentLecturers == null) {
            studentLecturers = new StudentLecturers();
            studentLecturers.setStudent(student);
            studentLecturers.setLecturer(lecturer);
            studentLecturers.setIsScientificSupervisor(Boolean.parseBoolean(request.get("isScientificSupervisor")));
            studentLecturers.setIsConsultant(Boolean.parseBoolean(request.get("isConsultant")));

            if (!student.getLecturers().contains(studentLecturers)) {
                student.getLecturers().add(studentLecturers);
            }
            if (!lecturer.getStudents().contains(studentLecturers)) {
                lecturer.getStudents().add(studentLecturers);
            }
        }

        if (!student.getLecturers().contains(studentLecturers)) {
            student.getLecturers().add(studentLecturers);
        }
        if (!lecturer.getStudents().contains(studentLecturers)) {
            lecturer.getStudents().add(studentLecturers);
        }
        if (!studentLecturers.getLecturer().equals(lecturer)) {
            studentLecturers.setLecturer(lecturer);
        }
        if (!studentLecturers.getStudent().equals(student)) {
            studentLecturers.setStudent(student);
        }
        return studentLecturers;
    }

    private Reviewer createReviewer(Map<String, String> request) {
        Reviewer reviewer = reviewerService.findReviewerByName(request.get("reviewerName"));
        if (reviewer == null) {
            reviewer = new Reviewer();

            String reviewerName = request.get("reviewerName");
            if (reviewerName != null) {
                reviewer.setName(reviewerName);
            } else {
                log.warn("Reviewer name is null");
                reviewer.setName("");
            }

            String reviewerPos = request.get("reviewerPos");
            if (reviewerPos != null) {
                reviewer.setPosition(reviewerPos);
            } else {
                log.warn("Reviewer position is null");
                reviewer.setPosition("");
            }

            String reviewerAD = request.get("reviewerAD");
            if (reviewerAD != null) {
                reviewer.setAcademicDegree(reviewerAD);
            } else {
                log.warn("Reviewer academic degree is null");
                reviewer.setAcademicDegree("");
            }
        }
        return reviewer;
    }


    private Department createDepartment(Map<String, String> request) {
        Department department = departmentService.findDepartmentByName(request.get("departmentName"));
        if (department == null) {
            department = new Department();

            String departmentName = request.get("departmentName");
            if (departmentName != null) {
                department.setName(departmentName);
            } else {
                log.warn("Department name is null");
                department.setName("");
            }
        }
        return department;
    }


    private Orientation createOrientation(Map<String, String> request) {
        Orientation orientation = orientationService.findByCode(request.get("orientationCode"));
        if (orientation == null) {
            orientation = new Orientation();

            String orientationCode = request.get("orientationCode");
            if (orientationCode != null) {
                orientation.setCode(orientationCode);
            } else {
                log.warn("Orientation code is null");
                orientation.setCode("");
            }

            String orientationName = request.get("orientationName");
            if (orientationName != null) {
                orientation.setName(orientationName);
            } else {
                log.warn("Orientation name is null");
                orientation.setName("");
            }
        }
        return orientation;
    }


    private FQW createFQW(Map<String, String> request, Reviewer reviewer) {
        FQW fqw = fqwService.findByName(request.get("themeName"));
        if (fqw == null) {
            fqw = new FQW();
            String themeName = request.get("themeName");
            if (themeName != null) {
                fqw.setName(themeName);
            } else {
                log.warn("Theme name cannot be null");
                fqw.setName("");
            }
            String uniquenessStr = request.get("uniqueness");
            if (uniquenessStr != null) {
                try {
                    fqw.setUniqueness(Float.parseFloat(uniquenessStr));
                } catch (NumberFormatException e) {
                    log.warn("Uniqueness must be a valid float");
                    fqw.setUniqueness(0.0f); // Set a default value if needed
                }
            } else {
                log.warn("Uniqueness is null");
                fqw.setUniqueness(0.0f);
            }

            String feedback = request.get("feedback");
            if (feedback != null) {
                fqw.setFeedback(feedback);
            } else {
                log.warn("Feedback is null");
                fqw.setFeedback("");
            }

            String volume = request.get("volume");
            if (volume != null) {
                fqw.setVolume(volume);
            } else {
                log.warn("Volume is null");
                fqw.setVolume("");
            }

            if (reviewer != null) {
                fqw.setReviewer(reviewer);
            } else {
                log.warn("Reviewer is null");
                fqw.setReviewer(null);
            }
        }
        return fqw;
    }

    private Protocol createProtocol(Map<String, String> request) {
        Protocol protocol = protocolService.findByStudentNum(Long.parseLong(request.get("studNum")));
        if (protocol == null) {
            protocol = new Protocol();

            String volumeStr = request.get("volume");
            if (volumeStr != null) {
                try {
                    protocol.setVolume(Integer.parseInt(volumeStr));
                } catch (NumberFormatException e) {
                    log.warn("Volume must be a valid integer");
                    protocol.setVolume(0);
                }
            } else {
                log.warn("Volume is null");
                protocol.setVolume(0);
            }

            String headOfTheFQW = request.get("headOfTheFQW");
            if (headOfTheFQW != null) {
                protocol.setHeadOfTheFQW(headOfTheFQW);
            } else {
                log.warn("Head of the FQW is null");
                protocol.setHeadOfTheFQW("");
            }

            String review = request.get("review");
            if (review != null) {
                protocol.setReview(review);
            } else {
                log.warn("Review is null");
                protocol.setReview("");
            }

            String gradeStr = request.get("grade");
            if (gradeStr != null) {
                try {
                    protocol.setGrade(Integer.parseInt(gradeStr));
                } catch (NumberFormatException e) {
                    log.warn("Grade must be a valid integer");
                    protocol.setGrade(0);
                }
            } else {
                log.warn("Grade is null");
                protocol.setGrade(0);
            }

            if (headOfTheFQW != null) {
                protocol.setHeadOfTheFQW(headOfTheFQW);
            } else {
                log.warn("Head of the FQW is null (second check)");
                protocol.setHeadOfTheFQW("");
            }
        }
        return protocol;
    }

    private Protection createProtection(Orientation orientation, Date dateOfProtection) {
        Protection protection = new Protection();

        if (orientation != null) {
            protection.setOrientation(orientation);
        } else {
            log.warn("Orientation is null");
            protection.setOrientation(null);
        }

        if (dateOfProtection != null) {
            String yearStr = DateUtil.getYear(dateOfProtection);
            try {
                protection.setDateOfProtection(Integer.parseInt(yearStr));
            } catch (NumberFormatException e) {
                log.warn("Year must be a valid integer");
                protection.setDateOfProtection(0);
            }
        } else {
            log.warn("Date of protection is null");
            protection.setDateOfProtection(0);
        }

        return protection;
    }


    private Commissioner createCommissioner(Map<String, String> request, String commissionerNumber) {
        String commissionerName = request.get("commissionerName" + commissionerNumber);
        String commissionerUniv = request.get("commissionerUniversity" + commissionerNumber);
        String commissionerDep = request.get("commissionerDepartment" + commissionerNumber);

        Commissioner commissioner = commissionerService.findByName(commissionerName);
        if (commissioner == null) {
            commissioner = new Commissioner();

            if (commissionerName != null) {
                commissioner.setName(commissionerName);
            } else {
                log.warn("Commissioner name is null");
                commissioner.setName("");
            }

            if (commissionerUniv != null) {
                commissioner.setUniversity(commissionerUniv);
            } else {
                log.warn("Commissioner university is null");
                commissioner.setUniversity("");
            }

            if (commissionerDep != null) {
                commissioner.setDepartment(commissionerDep);
            } else {
                log.warn("Commissioner department is null");
                commissioner.setDepartment("");
            }
        }
        return commissioner;
    }


    private ProtectionCommissioner createProtectionCommissioner(Protection protection, Commissioner commissioner) {
        ProtectionCommissioner pc = new ProtectionCommissioner();

        if (protection != null) {
            pc.setProtection(protection);
        } else {
            log.warn("Protection is null");
        }
        if (commissioner != null) {
            pc.setCommissioner(commissioner);
        } else {
            log.warn("Commissioner is null");
        }

        return pc;
    }


    private Question createQuestion(Map<String, String> request, String questionNumber, Commissioner commissioner) {
        Question questionObject = questionService.findQuestion(request.get("question" + questionNumber));
        if (questionObject == null) {
            questionObject = new Question();

            String questionText = request.get("question" + questionNumber);
            if (questionText != null) {
                questionObject.setQuestion(questionText);
            } else {
                log.warn("Question text is null");
                questionObject.setQuestion("");
            }

            if (commissioner != null) {
                questionObject.setQuestioner(commissioner.getName());
            } else {
                log.warn("Commissioner in question is null");
                questionObject.setQuestioner("");
            }
        }

        return questionObject;
    }


    private ProtocolQuestion createProtocolQuestion(Map<String, String> request, Protocol protocol, Question question) {
        ProtocolQuestion protocolQuestion = protocolQuestionService.findByQuestionAndProtocolStudent(question.getQuestion(), protocol.getStudent().getStud_num());
        if (protocolQuestion == null) {
            protocolQuestion = new ProtocolQuestion();

            if (question != null) {
                protocolQuestion.setQuestion(question);
            } else {
                log.warn("Question is null");
                protocolQuestion.setQuestion(null);
            }

            if (protocol != null) {
                protocolQuestion.setProtocol(protocol);
                protocol.getQuestions().add(protocolQuestion);
            } else {
                log.warn("Protocol is null");
                protocolQuestion.setProtocol(null);
            }

            if (question != null) {
                question.getProtocolQuestion().add(protocolQuestion);
            } else {
                log.warn("Question is null when adding to protocolQuestion");
            }
        }

        return protocolQuestion;
    }

}
