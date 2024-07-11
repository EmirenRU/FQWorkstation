package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDTO implements Serializable {
    private Long id;
    private String fio;
    private String academicDegree; // Ученая степень
    private String position; // должность
    private DepartmentDTO department;
    private List<StudentLecturersDTO> students;
}
