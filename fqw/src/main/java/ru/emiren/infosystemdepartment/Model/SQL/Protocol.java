package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RedisHash
public class Protocol implements Serializable {

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

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "question_id")
    List<ProtocolQuestion> questions = new ArrayList<>();

}
