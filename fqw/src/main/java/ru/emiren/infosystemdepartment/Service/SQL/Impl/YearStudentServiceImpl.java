package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.YearStudentDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.YearStudentMapper;
import ru.emiren.infosystemdepartment.Model.SQL.YearStudent;
import ru.emiren.infosystemdepartment.Repository.SQL.StudentRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearStudentRepository;
import ru.emiren.infosystemdepartment.Service.SQL.YearStudentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YearStudentServiceImpl implements YearStudentService {
    private YearStudentRepository yearStudentRepository;
    private YearRepository yearRepository;
    private StudentRepository studentRepository;

    @Autowired
    public YearStudentServiceImpl(YearStudentRepository yearStudentRepository,
                                  YearRepository yearRepository,
                                  StudentRepository studentRepository) {
        this.yearStudentRepository = yearStudentRepository;
        this.yearRepository = yearRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public List<YearStudentDTO> getAllYearStudent() {
        return yearStudentRepository.findAll().stream()
                .map(YearStudentMapper::mapToYearStudentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public YearStudent saveYearStudent(YearStudent ys) {
        return yearStudentRepository.save(ys);
    }
}
