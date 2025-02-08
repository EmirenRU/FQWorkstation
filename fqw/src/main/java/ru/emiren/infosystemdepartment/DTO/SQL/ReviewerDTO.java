package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewerDTO {
    private Long id;
    private String name;
    private String academicDegree; // Ученая степень
    private String position; // должность
}
