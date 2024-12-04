package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.DepartmentDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.DepartmentMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Department;
import ru.emiren.infosystemdepartment.Repository.SQL.DepartmentRepository;
import ru.emiren.infosystemdepartment.Service.SQL.DepartmentService;

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
        return  departmentRepository.findAll()
                .stream()
                .map(DepartmentMapper::mapToDepartmentDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Department saveDepartment(Department department) {
        return departmentRepository.save(department);
    }

    @Override
    public void deleteDepartment(Department department) {
        departmentRepository.delete(department);
    }

    @Override
    public Department getDepartmentById(Long id) {
        return departmentRepository
                .findById(String.valueOf(id))
                .orElse(null);
    }

    @Override
    public DepartmentDTO getDepartmentDTOById(Long id) {
        return departmentRepository
                .findById(String.valueOf(id))
                .map(DepartmentMapper::mapToDepartmentDTO)
                .orElse(null);
    }

    @Override
    public Department updateDepartment(Long id, Department department) {
        Department upd = departmentRepository.findById(String.valueOf(id)).orElse(new Department());

        if (department.getName() != null) {
            upd.setName(department.getName());
        } if (department.getCode() != null) {
            upd.setCode(department.getCode());
        }
        return departmentRepository.save(upd);
    }
}
