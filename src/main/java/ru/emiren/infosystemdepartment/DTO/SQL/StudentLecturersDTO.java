package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.io.Serializable;

@Data
@Builder
public class StudentLecturersDTO implements Serializable {
    private Long id;

    private StudentDTO student;

    private LecturerDTO lecturer;

    private Boolean isScientificSupervisor;
    private Boolean isConsultant;
}
