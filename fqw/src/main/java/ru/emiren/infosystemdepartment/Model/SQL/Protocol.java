package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Protocol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private Student student; // Фио Студента

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "theme", referencedColumnName = "id")
    private FQW fqw; // Final Qualifying Work Name
    private String headOfTheFQW; // head of the Final qualification work
    private String review; // Рецензия
    private Integer volume; // объем
    private Integer grade; // оценка
    private String language;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    List<ProtocolQuestion> questions = new ArrayList<>();

}
