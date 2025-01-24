package ru.emiren.infosystemdepartment.Model.SQL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "protocol_question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ProtocolQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "protocol_id", referencedColumnName = "id")
    @JsonBackReference
    private Protocol protocol;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    @JsonBackReference
    private Question question;
}
