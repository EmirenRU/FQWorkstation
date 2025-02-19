package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.Payload.Selector.DepartmentSelector;
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

    public static DepartmentSelector mapToSelector(DepartmentDTO departmentDTO){
        return DepartmentSelector
                .builder()
                .value(String.valueOf(departmentDTO.getCode()))
                .name(departmentDTO.getName())
                .build();
    }
}
