package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class YearStudentDTO implements Serializable {
    private Long id;

    private StudentDTO student;
    private YearDTO year;
}
