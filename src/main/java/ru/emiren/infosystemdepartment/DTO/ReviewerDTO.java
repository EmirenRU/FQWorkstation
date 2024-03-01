package ru.emiren.infosystemdepartment.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewerDTO {
    private Long id;
    private String fio;
    private String academicDegree; // Ученая степень
    private String position; // должность
}
