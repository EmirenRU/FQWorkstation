package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.LecturerMapper;
import ru.emiren.infosystemdepartment.Mapper.SQL.StudentLecturersMapper;
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
    public List<StudentLecturersDTO> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(String orientationCode, String departmentCode, LocalDate date, String theme, Long lecturerId) {
        return studentLecturerRepository.findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
        (orientationCode,
        departmentCode,
        date,
        theme,
        lecturerId).stream()
                .map(StudentLecturersMapper::mapToStudentLecturersDTO)
                .collect(Collectors.toList());
    }
}
