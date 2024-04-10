package ru.emiren.infosystemdepartment.DTO;

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
    private String fio;
    private String academicDegree; // Ученая степень
    private String position; // должность
    private DepartmentDTO department;
    private List<StudentDTO> students;
}
