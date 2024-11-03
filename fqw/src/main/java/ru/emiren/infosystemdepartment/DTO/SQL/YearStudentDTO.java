package ru.emiren.infosystemdepartment.DTO.SQL;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.emiren.infosystemdepartment.Model.SQL.Student;
import ru.emiren.infosystemdepartment.Model.SQL.Year;

@Data
@Builder
public class YearStudentDTO {
    private Long id;

    private Student student;
    private Year year;
}
