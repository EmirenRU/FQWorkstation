package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import ru.emiren.infosystemdepartment.DTO.DepartmentDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentDTO> getAllDepartments();
}
