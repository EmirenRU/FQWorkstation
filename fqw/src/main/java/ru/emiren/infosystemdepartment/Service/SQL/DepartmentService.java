package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.DepartmentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Department;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
    Department saveDepartment(Department department);
    void deleteDepartment(Department department);
}
