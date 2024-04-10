package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import ru.emiren.infosystemdepartment.DTO.StudentLecturersDTO;

import java.util.List;

public interface StudentLecturersService {
    List<StudentLecturersDTO> getAllStudentLecturers();

    List<StudentLecturersDTO> findAllAndSortedByLecturerName();
}
