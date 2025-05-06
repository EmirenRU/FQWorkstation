package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@Entity(name = "protocol_question")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@RedisHash
public class ProtocolQuestion implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "protocol_id", referencedColumnName = "id")

    private Protocol protocol;

    @ManyToOne
    @JoinColumn(name = "question_id", referencedColumnName = "id")
    private Question question;
}
