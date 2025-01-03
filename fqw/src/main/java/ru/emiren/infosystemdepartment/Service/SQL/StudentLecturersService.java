package ru.emiren.infosystemdepartment.Service.SQL;

import jakarta.transaction.Transactional;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.time.LocalDate;
import java.util.List;

public interface StudentLecturersService {
    StudentLecturers saveStudentLecturers(StudentLecturers studentLecturersDTO);

    List<StudentLecturersDTO> getAllStudentLecturers();

    List<StudentLecturersDTO> findAllAndSortedByLecturerName();

    @Transactional
    List<StudentLecturersDTO> findAllSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
            (List<String> orientationCode,
             List<Long> departmentCode,
             java.time.Year dateFrom,
             java.time.Year dateTo,
             String theme,
             List<Long> lecturerId);

    List<StudentLecturersDTO> findAllAndSortedByDate(String date);

    void deleteStudentLecturers(StudentLecturers sl);

    StudentLecturers findStudentLecturerById(Long id);
    StudentLecturersDTO findStudentLecturersDTOById(Long id);
    StudentLecturers updateStudentLecturers(StudentLecturers sl);
}
