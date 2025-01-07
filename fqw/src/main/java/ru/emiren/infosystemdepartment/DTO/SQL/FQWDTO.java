package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FQWDTO {
    private String name;

    private String classifier; // классификатор
    private Float uniqueness; // оригинальность
    private String feedback; // отзыв
    private String volume; // объем

    private ReviewerDTO reviewer;
}
