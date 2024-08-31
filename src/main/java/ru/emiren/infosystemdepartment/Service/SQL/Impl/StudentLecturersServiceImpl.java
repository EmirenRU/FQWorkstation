package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.LecturerMapper;
import ru.emiren.infosystemdepartment.Mapper.SQL.StudentLecturersMapper;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;
import ru.emiren.infosystemdepartment.Repository.SQL.StudentLecturerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.StudentLecturersService;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentLecturersServiceImpl implements StudentLecturersService {
    StudentLecturerRepository studentLecturerRepository;

    StudentLecturersServiceImpl(StudentLecturerRepository studentLecturerRepository){
        this.studentLecturerRepository = studentLecturerRepository;
    }


    @Override
    public StudentLecturers saveStudentLecturers(StudentLecturers studentLecturersDTO) {
        return studentLecturerRepository.save(studentLecturersDTO);
    }

    @Override
    public List<StudentLecturersDTO> getAllStudentLecturers() {
        return studentLecturerRepository.findAll()
                .stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentLecturersDTO> findAllAndSortedByLecturerName() {
        return studentLecturerRepository.findAllSorted().stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentLecturersDTO> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(String orientationCode,
                                                                                                          Long departmentCode, LocalDate date, String theme, Long lecturerId) {
        return studentLecturerRepository.findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
        (orientationCode,
        departmentCode,
        date,
        theme,
        lecturerId).stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentLecturersDTO> findAllAndSortedByDate(String date) {
        return studentLecturerRepository.findAllSortedByDate(date).stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }
}
