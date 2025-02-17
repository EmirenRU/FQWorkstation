package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentDTO;
import ru.emiren.infosystemdepartment.Mapper.StudentMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Student;
import ru.emiren.infosystemdepartment.Repository.SQL.StudentRepository;
import ru.emiren.infosystemdepartment.Service.SQL.StudentService;

import java.util.Collections;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }


    @Override
    public Student saveStudent(Student studentDTO) {
        return studentRepository.save(studentDTO);
    }

    @Override
    public List<StudentDTO> findAllStudent() {
        List<Student> student = studentRepository.findAll();
        return student.stream().map(StudentMapper::mapToStudentDTO).toList();
    }

    @Override
    public List<Student> findAllStudentById(Long id) {
        return studentRepository.findAllById(Collections.singleton(id));
    }

    @Override
    public List<StudentDTO> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long lecturerId, String OrientationCode, String DepartmentCode) {
        return studentRepository.findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(lecturerId, OrientationCode, DepartmentCode)
                .stream()
                .map(StudentMapper::mapToStudentDTO)
                .toList();
    }

    @Override
    public List<StudentDTO> findAllStudentByLecturerId(Long lecturerId) {
        return studentRepository.findStudentsByLecturerId(lecturerId)
                .stream()
                .map(StudentMapper::mapToStudentDTO)
                .toList();
    }

    @Override
    public void deleteStudent(Student student) {
        studentRepository.delete(student);
    }

    @Override
    public Student findStudentById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    public StudentDTO findStudentDTOById(Long id) {
        return studentRepository.findById(id).map(StudentMapper::mapToStudentDTO).orElse(null);
    }

    @Override
    public Student updateStudent(Student st) {
        Student s = findStudentById(st.getId());
        if (s == null) return studentRepository.save(st);

        if (st.getName() != null) s.setName(st.getName());
        if (st.getCitizenship() != null) s.setCitizenship(st.getCitizenship());
        if (st.getStud_num() != null) s.setStud_num(st.getStud_num());
        if (st.getFqw() != null) s.setFqw(st.getFqw());
        if (st.getDepartment() != null) s.setDepartment(st.getDepartment());
        if (st.getClassifier() != null) s.setClassifier(st.getClassifier());
        if (st.getLecturers() != null) s.setLecturers(st.getLecturers());
        if (st.getLoe() != null) s.setLoe(st.getLoe());
        if (st.getOrientation() != null) s.setOrientation(st.getOrientation());

        return studentRepository.save(s);
    }

    @Override
    public Student findStudentByName(String studName) {
        return studentRepository.findByName(studName);
    }

    @Override
    public Student findStudentByStudNum(Long studNum) {
        return studentRepository.findByStudNum(studNum).orElse(null);
    }
}
