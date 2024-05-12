package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Repository.SQL.StudentRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Repository.SQL.YearStudentRepository;
import ru.emiren.infosystemdepartment.Service.SQL.YearStudentService;

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
}
