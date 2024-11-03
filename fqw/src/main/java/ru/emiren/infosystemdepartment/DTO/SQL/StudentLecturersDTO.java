package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

@Data
@Builder
public class StudentLecturersDTO {
    private Long id;

    private Student student;

    private Lecturer lecturer;

    private Boolean isScientificSupervisor;
    private Boolean isConsultant;
}
