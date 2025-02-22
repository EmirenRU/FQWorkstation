package ru.emiren.infosystemdepartment.Controller.SQL;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Util.DateUtil;

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
    private final DecreeService decreeService;

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
                                ProtectionCommissionerService protectionCommissionerService, ProtocolQuestionService protocolQuestionService, QuestionService questionService, DecreeService decreeService) {
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
        this.decreeService = decreeService;
    }

    /**
     * Handle for upload data form.
     * I recommend to not touch.
     * @param request
     * @return a redirection to the same page
     */
    @PostMapping("/api/add-data")
    public ResponseEntity<String> addData(@RequestBody Map<String, String> request){
        log.info(request.toString());
        Integer dateOfProtection = DateUtil.parseYear(request.get("dateOfProtection"));

        log.info("Saving Department");
        Department department = createDepartment(request);
        log.info("Saving Orientation");
        Orientation orientation = createOrientation(request);
        log.info("Saving Protection");
        Protection protection = createProtection(orientation, dateOfProtection);
        log.info("Saving Reviewer");
        Reviewer reviewer = createReviewer(request);

        log.info("Saving Decree");
        Decree decree = createDecree(request);
        log.info("Saving FQW");
        FQW fqw = createFQW(request, reviewer, decree);

        log.info("Saving Commissioner 1");
        Commissioner commissioner1 = createCommissioner(request, "1");
        log.info("Saving Commissioner 2");
        Commissioner commissioner2 = createCommissioner(request, "2");
        log.info("Saving Commissioner 3");
        Commissioner commissioner3 = createCommissioner(request, "3");

        log.info("Saving Student");
        Student student = createStudent(request, orientation, fqw, department);
        log.info("Saving Lecturer");
        Lecturer lecturer = createLecturer(request, department);
        log.info("Saving Student-Lecturer");
        StudentLecturers sl = createStudentLecturers(request, student, lecturer);

        log.info("Saving Protocol");
        Protocol protocol = createProtocol(request, student);

        log.info("Saving Question 1");
        Question q1 = createQuestion(request, "1", commissioner1);
        log.info("Saving Question 2");
        Question q2 = createQuestion(request, "2", commissioner3);
        log.info("Saving Question 3");
        Question q3 = createQuestion(request, "3", commissioner2);

        log.info("Saving PC 1");
        createProtectionCommissioner(protection, commissioner1);
        log.info("Saving PC 2");
        createProtectionCommissioner(protection, commissioner2);
        log.info("Saving PC 3");
        createProtectionCommissioner(protection, commissioner3);

        log.info("Saving PQ 1");
        createProtocolQuestion(request, protocol, q1);
        log.info("Saving PQ 2");
        createProtocolQuestion(request, protocol, q2);
        log.info("Saving PQ 3");
        createProtocolQuestion(request, protocol, q3);

        lecturer.getStudents().add(sl);
        orientation.getProtection().add(protection);
        return ResponseEntity.status(HttpStatus.OK).body("Done");
    }



    private Student createStudent(Map<String, String> request, Orientation orientation, FQW fqw, Department department) {
        String studNumStr = request.get("studNum");
        Long studNum = Long.parseLong(request.get("studNum"));
        Student student = studentService.findStudentByStudNum(studNum);
        if (student == null) {
            student = new Student();
            log.info("In process of saving Student");
            Long id = studentService.getMaxId();
            log.info("Found max id: " + id);
            if (id != null) {log.info("Setting id {}", id);student.setId(id+1);}
            log.info("Id is " + student.getId());
            String name = request.get("studName");
            if (name != null) {
                student.setName(name);
            } else {
                log.warn("Student name is null");
                student.setName("");
            }

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
            student.setOrientation(orientation);
            student.setFqw(fqw);
            student.setDepartment(department);
            try {
                studentService.saveStudent(student);
            } catch (DataIntegrityViolationException e) {
                log.info("Error in saving Student: {}", e.getMessage());
            }
        }
        return student;
    }


    private Lecturer createLecturer(Map<String, String> request, Department department) {
        Lecturer lecturer = null;
        if (request.get("lecturerName") != null) {
            lecturer = lecturerService.findByLecturerName(request.get("lecturersName"));
        }
        if (lecturer == null) {
            lecturer = new Lecturer();
            Long id = lecturerService.getMaxId();
            if (id != null) {lecturer.setId(id+1);}
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
            lecturer.setDepartment(department);
            lecturerService.saveLecturer(lecturer);
        }
        return lecturer;
    }


    private StudentLecturers createStudentLecturers(Map<String, String> request, Student student, Lecturer lecturer) {
        StudentLecturers studentLecturers = studentLecturersService
                .findStudentLecturersByStudentStudNumAndLecturerName(student.getStud_num(),
                                                                     lecturer.getName());
        if (studentLecturers == null) {
            studentLecturers = new StudentLecturers();
            Long id = studentLecturersService.getMaxId();
            if (id != null) { studentLecturers.setId(id+1); }
            studentLecturers.setId(id);
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
                studentLecturersService.saveStudentLecturers(studentLecturers);
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
            Long id = reviewerService.getMaxId();
            if (id != null) {reviewer.setId(id+1);}
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
            reviewerService.saveReviewer(reviewer);
        }
        return reviewer;
    }

    private Decree createDecree(Map<String, String> request) {
        Decree decree = decreeService.findDecreeByThemeAndNumberOfDecreeAndStudNum(Long.parseLong(request.get("studNum")), request.get("themeName"), request.get("numberOfDecree"));
        if (decree == null) {
            decree = new Decree();
            Long id = decreeService.getMaxId();
            if (id != null) {decree.setId(id+1);}
            Long studNum = Long.parseLong(request.get("studNum"));
            String theme = request.get("themeName");
            String numberOfDecree = request.get("numberOfDecree");
            if (theme != null) {decree.setTheme(theme);}
            if (numberOfDecree != null) {decree.setNumberOfDecree(numberOfDecree);}
            if (studNum != null) {decree.setStudNum(studNum);}
            decreeService.saveDecree(decree);
        }
        return decree;
    }


    private Department createDepartment(Map<String, String> request) {
        Department department = departmentService.findDepartmentByName(request.get("departmentName"));
        if (department == null) {
            department = new Department();
            Long id = departmentService.getMaxId();
            if (id != null) {department.setCode(id+1);}
            String departmentName = request.get("departmentName");
            if (departmentName != null) {
                department.setName(departmentName);
            } else {
                log.warn("Department name is null");
                department.setName("");
            }
            departmentService.saveDepartment(department);
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
            orientationService.saveOrientation(orientation);
        }
        return orientation;
    }


    private FQW createFQW(Map<String, String> request, Reviewer reviewer, Decree decree) {
        FQW fqw = fqwService.findByName(request.get("themeName"));
        if (fqw == null) {
            fqw = new FQW();
            Long id = fqwService.getMaxId();
            if (id != null) {fqw.setId(id+1);}
            String themeName = request.get("themeName");
            if (themeName != null) {
                fqw.setDecree(decree);
            } else {
                log.warn("Theme name cannot be null");
                fqw.setDecree(null);
            }
            String uniquenessStr = request.get("uniqueness");
            if (uniquenessStr != null) {
                try {
                    fqw.setUniqueness(Float.parseFloat(uniquenessStr));
                } catch (NumberFormatException e) {
                    log.warn("Uniqueness must be a valid float");
                    fqw.setUniqueness(0.0f);
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
            fqw.setReviewer(reviewer);
        }
        return fqw;
    }

    private Protocol createProtocol(Map<String, String> request, Student student) {
        Protocol protocol = protocolService.findByStudentNum(Long.parseLong(request.get("studNum")));
        if (protocol == null) {
            protocol = new Protocol();
            Long id = protocolService.getMaxId();
            if (id != null) {protocol.setId(id+1);}
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
            protocol.setStudent(student);
            protocolService.saveProtocol(protocol);
        }
        return protocol;
    }

    private Protection createProtection(Orientation orientation, Integer dateOfProtection) {
        Protection protection = protectionService.findByDateOfProtectionAndOrientationCode(orientation.getCode(), dateOfProtection);
        if (protection == null) {
            protection = new Protection();
            Long id = protectionService.getMaxId();
            if (id != null) protection.setId(id + 1);

            if (orientation != null) {
                protection.setOrientation(orientation);
            } else {
                log.warn("Orientation is null");
                protection.setOrientation(null);
            }

            if (dateOfProtection != null) {
                try {
                    protection.setDateOfProtection(dateOfProtection);
                } catch (NumberFormatException e) {
                    log.warn("Year must be a valid integer");
                    protection.setDateOfProtection(0);
                }
            } else {
                log.warn("Date of protection is null");
                protection.setDateOfProtection(0);
            }
            protection.setOrientation(orientation);
            protectionService.saveProtection(protection);
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
            Long id = commissionerService.getMaxId();
            if (id != null) {commissioner.setId(id+1);}
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
            commissionerService.saveCommissioner(commissioner);

        }
        return commissioner;
    }


    private ProtectionCommissioner createProtectionCommissioner(Protection protection, Commissioner commissioner) {
        ProtectionCommissioner pc = new ProtectionCommissioner();

        if (pc == null) {
            pc = new ProtectionCommissioner();
            Long id = protectionCommissionerService.getMaxId();
            if (id != null) {pc.setId(id+1);}

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
            protectionCommissionerService.saveProtectionCommissioner(pc);
        }
        return pc;
    }


    private Question createQuestion(Map<String, String> request, String questionNumber, Commissioner commissioner) {
        Question questionObject = questionService.findQuestion(request.get("question" + questionNumber)); // Questioner from commissionerName1,2,3,
        if (questionObject == null) {
            questionObject = new Question();
            Long id = questionService.getMaxId();
            if (id != null) {questionObject.setId(id+1);}

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
            Long id = protocolQuestionService.getMaxId();
            if (id!=null) protocolQuestion.setId(id+1);

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
            protocolQuestionService.saveProtocolQuestion(protocolQuestion);

        }

        return protocolQuestion;
    }

}
