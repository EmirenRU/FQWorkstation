package ru.emiren.infosystemdepartment.Service.api.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Model.SQL.*;
import ru.emiren.infosystemdepartment.Service.SQL.*;
import ru.emiren.infosystemdepartment.Service.api.UploadDataFormService;

import java.util.Map;

@Service
@Slf4j
public class UploadDataFormServiceImpl implements UploadDataFormService {
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

    public UploadDataFormServiceImpl(StudentService studentService, LecturerService lecturerService, OrientationService orientationService, CommissionerService commissionerService, StudentLecturersService studentLecturersService, ReviewerService reviewerService, DepartmentService departmentService, ProtectionService protectionService, FQWService fqwService, ProtocolService protocolService, ProtectionCommissionerService protectionCommissionerService, ProtocolQuestionService protocolQuestionService, QuestionService questionService, DecreeService decreeService) {
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


    @Transactional
    @Override
    public Student createStudent(Map<String, String> request, Orientation orientation, FQW fqw, Department department) {
        String studNumStr = request.get("studNum");
        Long studNum = Long.parseLong(request.get("studNum"));
        Student student = studentService.findStudentByStudNum(studNum);
        if (student == null) {
            student = new Student();
            log.info("In process of saving Student");
            Long id = studentService.getMaxId();
            log.info("Found max id: " + id);
            if (id != null) {log.info("Setting id {}", id);student.setId(id+1);}
            else {student.setId(1L);}
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
            log.info("In process of saving Student before setting");
            student.setOrientation(orientation);
            student.setFqw(fqw);
            student.setDepartment(department);
            log.info("In process of saving Student after setting");

            try {
                studentService.saveStudent(student);
            } catch (DataIntegrityViolationException e) {
                log.info("Error in saving Student: {}", e.getMessage());
            }
        }
        return student;
    }

    @Transactional
    @Override
    public Lecturer createLecturer(Map<String, String> request, Department department) {
        Lecturer lecturer = null;
        if (request.get("lecturersName") != null) {
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
            log.info("Pre-defined lecturer's id {}", lecturer.getId());
            lecturerService.saveLecturer(lecturer);
            log.info("Post-defined lecturer's id {}", lecturer.getId());

        }
        return lecturer;
    }


    @Transactional
    @Override
    public StudentLecturers createStudentLecturers(Map<String, String> request, Student student, Lecturer lecturer) {
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
            log.info("In process of saving StudentLecturers with lecturer's id {} and student's id {}", lecturer.getId(), student.getId());
            studentLecturers.setIsScientificSupervisor(Boolean.parseBoolean(request.get("isScientificSupervisor")));
            studentLecturers.setIsConsultant(Boolean.parseBoolean(request.get("isConsultant")));

            studentLecturersService.saveStudentLecturers(studentLecturers);

            if (!student.getLecturers().contains(studentLecturers)) {
                student.getLecturers().add(studentLecturers);
            }
            if (!lecturer.getStudents().contains(studentLecturers)) {
                lecturer.getStudents().add(studentLecturers);
            }
        }

        return studentLecturers;
    }

    @Transactional
    @Override
    public Reviewer createReviewer(Map<String, String> request) {
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

    @Transactional
    @Override
    public Decree createDecree(Map<String, String> request) {
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


    @Transactional
    @Override
    public Department createDepartment(Map<String, String> request) {
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


    @Transactional
    @Override
    public Orientation createOrientation(Map<String, String> request) {
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


    @Transactional
    @Override
    public FQW createFQW(Map<String, String> request, Reviewer reviewer, Decree decree) {
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
            try {
                fqwService.saveFqw(fqw);
            } catch (DataIntegrityViolationException e) {
                log.warn("FQW could not be saved");
            }
        }
        return fqw;
    }

    @Transactional
    @Override
    public Protocol createProtocol(Map<String, String> request, Student student) {
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

    @Transactional
    @Override
    public Protection createProtection(Orientation orientation, Integer dateOfProtection) {
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


    @Transactional
    @Override
    public Commissioner createCommissioner(Map<String, String> request, String commissionerNumber) {
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


    @Transactional
    @Override
    public ProtectionCommissioner createProtectionCommissioner(Protection protection, Commissioner commissioner) {
        ProtectionCommissioner pc = protectionCommissionerService.findByProtectionAndCommissioner(protection.getId(), commissioner.getId());

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


    @Transactional
    @Override
    public Question createQuestion(Map<String, String> request, String questionNumber, Commissioner commissioner) {
        Question questionObject = questionService.findQuestion(request.get("question" + questionNumber)); // Questioner from commissionerName1,2,3,
        if (questionObject == null) {
            log.info("Question is null");
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

            try {
                questionService.saveQuestion(questionObject);
            } catch (DataIntegrityViolationException e) {
                log.warn("Question already exists");
            }
        }

        return questionObject;
    }


    @Transactional
    @Override
    public ProtocolQuestion createProtocolQuestion(Map<String, String> request, Protocol protocol, Question question) {
        ProtocolQuestion protocolQuestion = protocolQuestionService.findByQuestionAndProtocolStudent(question.getQuestion(), protocol.getStudent().getStud_num(), question.getQuestioner());
        if (protocolQuestion == null) {
            log.info("Protocol question is null, question_id {}", question.getId());
            protocolQuestion = new ProtocolQuestion();
            Long id = protocolQuestionService.getMaxId();
            if (id!=null) protocolQuestion.setId(id+1);
            Protocol temp = protocolService.findByStudentNum(Long.valueOf(request.get("studNum")));
            protocolQuestion.setProtocol(temp);
            Question qt = questionService.findQuestion(question.getQuestion());
            protocolQuestion.setQuestion(qt);
            log.info("question_id {}", protocolQuestion.getQuestion().getId());

            protocol.getQuestions().add(protocolQuestion);
            question.getProtocolQuestion().add(protocolQuestion);
            try {
                protocolQuestionService.saveProtocolQuestion(protocolQuestion);
            } catch (DataIntegrityViolationException e) {
                log.warn("Protocol question already exists {}", e.getMessage());
            }
        }

        return protocolQuestion;
    }

}
