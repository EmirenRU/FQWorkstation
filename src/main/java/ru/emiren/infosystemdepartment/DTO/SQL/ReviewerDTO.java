package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

import java.beans.Transient;
import java.io.Serializable;

@Data
@Builder
public class ReviewerDTO implements Serializable {
    private Long id;
    private String fio;
    private String academicDegree; // Ученая степень
    private String position; // должность
}
