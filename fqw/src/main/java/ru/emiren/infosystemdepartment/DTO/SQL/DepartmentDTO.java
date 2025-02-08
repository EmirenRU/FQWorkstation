package ru.emiren.infosystemdepartment.DTO.SQL;


import lombok.Builder;
import lombok.Data;

@Data
@Builder

public class DepartmentDTO {
    private Long code;
    private String name;
}
