package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDTO {
    private Long id;
    private String name;
    private String academicDegree; // Ученая степень
    private String position; // должность
    private DepartmentDTO department;
    private List<StudentDTO> students;
}
