package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Mapper.StudentLecturersMapper;
import ru.emiren.infosystemdepartment.Repository.StudentLecturerRepository;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.StudentLecturersService;

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
}
