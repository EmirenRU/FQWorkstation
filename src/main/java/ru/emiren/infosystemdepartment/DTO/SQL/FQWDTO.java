package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FQWDTO implements Serializable {
    private String name;

    private String classifier; // классификатор
    private Float uniqueness; // оригинальность
    private String feedback; // отзыв
    private String volume; // объем

    private ReviewerDTO reviewer;
}
