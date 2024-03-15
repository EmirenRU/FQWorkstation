package ru.emiren.infosystemdepartment.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FQWDTO {
    private Long id;

    private Student student;

    private String classifier; // классификатор

    private Float uniqueness; // оригинальность
    private String feedback; // отзыв
    private String volume; // объем

    private Reviewer reviewer;
}
