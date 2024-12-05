package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

@Data
@Builder
@ToString
public class StudentLecturersDTO {
    private Long id;

    private Student student;

    private Lecturer lecturer;

    private Boolean isScientificSupervisor;
    private Boolean isConsultant;
}
