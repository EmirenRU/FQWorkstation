package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class YearDTO {
    private String year;
    private List<YearStudentDTO> students = new ArrayList<>();

}
