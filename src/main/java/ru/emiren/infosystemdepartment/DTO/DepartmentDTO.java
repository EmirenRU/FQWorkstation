package ru.emiren.infosystemdepartment.DTO;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder

public class DepartmentDTO {
    private String code;
    private String name;
}
