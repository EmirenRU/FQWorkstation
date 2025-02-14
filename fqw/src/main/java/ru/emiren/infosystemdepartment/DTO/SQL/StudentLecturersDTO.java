package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class StudentLecturersDTO {
    private Long id;

    private StudentDTO student;

    private LecturerDTO lecturer;

    private Boolean isScientificSupervisor;
    private Boolean isConsultant;
}
