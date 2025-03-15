package ru.emiren.infosystemdepartment.Service.SQL;

import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.List;

public interface StudentService {

    @Transactional
    Student saveStudent(Student studentDTO);
    List<StudentDTO> findAllStudent();
    List<Student> findAllStudentById(Long id);

    List<StudentDTO> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long       lecturerId,
                                                                                   String OrientationCode,
                                                                                   String DepartmentCode);

    List<StudentDTO> findAllStudentByLecturerId(Long lecturerId);

    void deleteStudent(Student student);

    Student findStudentById(Long id);
    StudentDTO findStudentDTOById(Long id);
    Student updateStudent(Student studentDTO);

    Student findStudentByName(String studName);

    Student findStudentByStudNum(Long studNum);

    Long getMaxId();

} // CD
