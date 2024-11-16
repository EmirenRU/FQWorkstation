package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.time.LocalDate;
import java.util.List;

public interface StudentLecturersService {
    StudentLecturers saveStudentLecturers(StudentLecturers studentLecturersDTO);

    List<StudentLecturersDTO> getAllStudentLecturers();

    List<StudentLecturersDTO> findAllAndSortedByLecturerName();

    List<StudentLecturersDTO> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
            (String orientationCode,
             Long departmentCode,
             java.time.Year dateFrom,
             java.time.Year dateTo,
             String theme,
             Long lecturerId);

    List<StudentLecturersDTO> findAllAndSortedByDate(String date);
}
