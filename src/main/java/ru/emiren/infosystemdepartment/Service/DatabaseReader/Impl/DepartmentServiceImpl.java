package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.DepartmentDTO;
import ru.emiren.infosystemdepartment.Mapper.DepartmentMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Department;
import ru.emiren.infosystemdepartment.Repository.DepartmentRepository;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.DepartmentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream().map(DepartmentMapper::mapToDepartmentDTO).collect(Collectors.toList());
    }
}
