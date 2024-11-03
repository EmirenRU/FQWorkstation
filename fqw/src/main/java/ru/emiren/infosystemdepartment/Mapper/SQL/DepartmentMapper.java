package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.DepartmentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Department;

public class DepartmentMapper {
    public static Department mapToDepartment(DepartmentDTO departmentDTO){
        return Department.builder()
                .code(departmentDTO.getCode())
                .name(departmentDTO.getName())
                .build();
    }

    public static DepartmentDTO mapToDepartmentDTO(Department department){
        return DepartmentDTO.builder()
                .code(department.getCode())
                .name(department.getName())
                .build();
    }
}
