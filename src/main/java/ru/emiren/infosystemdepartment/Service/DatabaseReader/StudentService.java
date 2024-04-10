package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.DTO.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.List;
import java.util.Set;

public interface StudentService {

    Student saveStudent(StudentDTO studentDTO);
    List<StudentDTO> findAllStudent();
    List<Student> findAllStudentById(Long id);

    List<StudentDTO> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long       lecturerId,
                                                                                   String OrientationCode,
                                                                                   String DepartmentCode);

    List<StudentDTO> findAllStudentByLecturerId(Long lecturerId);
}
