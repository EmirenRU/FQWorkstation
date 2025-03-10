package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.transaction.Transactional;
import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.util.List;

public interface StudentLecturersService {
    StudentLecturers saveStudentLecturers(StudentLecturers studentLecturersDTO);

    List<SqlPayload> getAllStudentLecturers();

    List<StudentLecturersDTO> findAllAndSortedByLecturerName();

    @Transactional
    List<StudentLecturersDTO> findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
            (List<String> orientationCode,
             List<Long> departmentCode,
             Integer dateFrom,
             Integer dateTo,
             List<Long> theme,
             List<Long> lecturerId);

    List<StudentLecturersDTO> findAllAndSortedByDate(String date);

    void deleteStudentLecturers(StudentLecturers sl);

    StudentLecturers findStudentLecturerById(Long id);
    StudentLecturersDTO findStudentLecturersDTOById(Long id);
    StudentLecturers updateStudentLecturers(StudentLecturers sl);

    StudentLecturers findStudentLecturersByStudentStudNum(Long studNum, String leName);

    StudentLecturers findStudentLecturersByStudentStudNumAndLecturerName(Long studNum, String name);

    List<StudentLecturersDTO> findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartmentIds(List<String> orientationCodes,
                                                                                                   List<Long> departmentCode,
                                                                                                   Integer dateFrom,
                                                                                                   Integer dateTo,
                                                                                                   List<Long> theme,
                                                                                                   List<Long> lecturerIds);

    Long getMaxId();
}
