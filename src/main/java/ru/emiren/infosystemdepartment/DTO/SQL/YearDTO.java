package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class YearDTO implements Serializable {
    private String year;
    private List<YearStudentDTO> students;

}
