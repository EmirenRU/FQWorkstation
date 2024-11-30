package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class YearStudentDTO {
    private Long id;

    private StudentDTO student;
    private YearDTO year;
}
