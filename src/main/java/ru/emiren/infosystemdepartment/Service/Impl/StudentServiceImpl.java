package ru.emiren.infosystemdepartment.Service.Impl;

import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.Student;
import ru.emiren.infosystemdepartment.Service.StudentService;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {


    @Override
    public List<Student> findAllStudent() {
        return null;
    }
}
