package ru.emiren.infosystemdepartment.DTO.SQL;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder

public class DepartmentDTO implements Serializable {
    private String code;
    private String name;
}
