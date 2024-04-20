package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.List;

public interface StudentService {

    Student saveStudent(StudentDTO studentDTO);
    List<StudentDTO> findAllStudent();
    List<Student> findAllStudentById(Long id);

    List<StudentDTO> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long       lecturerId,
                                                                                   String OrientationCode,
                                                                                   String DepartmentCode);

    List<StudentDTO> findAllStudentByLecturerId(Long lecturerId);
}
