package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;

import java.time.LocalDate;
import java.util.List;

public interface StudentLecturersService {
    List<StudentLecturersDTO> getAllStudentLecturers();

    List<StudentLecturersDTO> findAllAndSortedByLecturerName();

    List<StudentLecturersDTO> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(String orientationCode, String departmentCode, LocalDate date, String theme, Long lecturerId);
}
