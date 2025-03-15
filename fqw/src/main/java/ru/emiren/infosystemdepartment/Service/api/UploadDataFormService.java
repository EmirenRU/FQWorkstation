package ru.emiren.infosystemdepartment.Service.api;

import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Model.SQL.*;

import java.util.Map;

public interface UploadDataFormService {
    @Transactional
    Student createStudent(Map<String, String> request, Orientation orientation, FQW fqw, Department department);

    @Transactional
    Lecturer createLecturer(Map<String, String> request, Department department);

    @Transactional
    StudentLecturers createStudentLecturers(Map<String, String> request, Student student, Lecturer lecturer);

    @Transactional
    Reviewer createReviewer(Map<String, String> request);

    @Transactional
    Decree createDecree(Map<String, String> request);

    @Transactional
    Department createDepartment(Map<String, String> request);

    @Transactional
    Orientation createOrientation(Map<String, String> request);

    @Transactional
    FQW createFQW(Map<String, String> request, Reviewer reviewer, Decree decree);

    @Transactional
    Protocol createProtocol(Map<String, String> request, Student student);

    @Transactional
    Protection createProtection(Orientation orientation, Integer dateOfProtection);

    @Transactional
    Commissioner createCommissioner(Map<String, String> request, String commissionerNumber);

    @Transactional
    ProtectionCommissioner createProtectionCommissioner(Protection protection, Commissioner commissioner);

    @Transactional
    Question createQuestion(Map<String, String> request, String questionNumber, Commissioner commissioner);

    @Transactional
    ProtocolQuestion createProtocolQuestion(Map<String, String> request, Protocol protocol, Question question);
}
