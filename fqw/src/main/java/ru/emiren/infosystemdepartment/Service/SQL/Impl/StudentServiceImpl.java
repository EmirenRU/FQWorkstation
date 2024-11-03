package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.StudentMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Student;
import ru.emiren.infosystemdepartment.Repository.SQL.LecturerRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.StudentRepository;
import ru.emiren.infosystemdepartment.Service.SQL.StudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final LecturerRepository lecturerRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, LecturerRepository lecturerRepository){
        this.studentRepository = studentRepository;
        this.lecturerRepository = lecturerRepository;
    }


    @Override
    public Student saveStudent(Student studentDTO) {
        return studentRepository.save(studentDTO);
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
    public List<StudentDTO> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long lecturerId, String OrientationCode, String DepartmentCode) {
        return studentRepository.findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(lecturerId, OrientationCode, DepartmentCode)
                .stream()
                .map(StudentMapper::mapToStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentDTO> findAllStudentByLecturerId(Long lecturerId) {
        return studentRepository.findStudentsByLecturerId(lecturerId)
                .stream()
                .map(StudentMapper::mapToStudentDTO)
                .collect(Collectors.toList());
    }
}
