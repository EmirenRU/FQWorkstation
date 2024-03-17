package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.DTO.StudentDTO;
import ru.emiren.infosystemdepartment.Mapper.StudentMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Student;
import ru.emiren.infosystemdepartment.Repository.LecturerRepository;
import ru.emiren.infosystemdepartment.Repository.StudentRepository;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.StudentService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private LecturerRepository lecturerRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, LecturerRepository lecturerRepository){
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
    }


    @Override
    public Student saveStudent(StudentDTO studentDTO) {
        Student student = StudentMapper.mapToStudent(studentDTO);
        return studentRepository.save(student);
    }

    @Override
    public List<StudentDTO> findAllStudent() {
        List<Student> student = studentRepository.findAll();
        return student.stream().map(StudentMapper::mapToStudentDTO).collect(Collectors.toList());
    }

    @Override
    public List<Student> findAllStudentById(Long id) {
        return null;
    }

    @Override
    public List<Student> findAllStudentByFilterText(List<String> words) {
        return null;
    }

    @Override
    public List<Student> findByFilterText(Set<String> words) {
        return null;
    }

    @Override
    public List<StudentDTO> findAllStudentByLecturerId(Long lecturerId) {
        return studentRepository.findStudentsByLecturerId(lecturerId)
                .stream()
                .map(StudentMapper::mapToStudentDTO)
                .collect(Collectors.toList());
    }
}
