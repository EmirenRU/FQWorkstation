package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Protocol {
    @Id
    private String fioStudent; // Фио Студента

    private String fqwName; // Final Qualifying Work Name
    private String headOfTheFQW; // head of the Final qualification work
    private String review; // Рецензия
    private Integer volume; // объем
    private Integer grade; // оценка

//    private List<String> questions = new ArrayList<>();

}
