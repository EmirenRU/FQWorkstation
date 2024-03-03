package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.DTO.StudentDTO;
import ru.emiren.infosystemdepartment.Model.Student;

import java.util.List;
import java.util.Set;

public interface StudentService {

    Student saveStudent(StudentDTO studentDTO);
    List<StudentDTO> findAllStudent();
    List<Student> findAllStudentById(Long id);

    List<Student> findAllStudentByFilterText(List<String> words);

    public List<Student> findByFilterText(Set<String> words);

}
