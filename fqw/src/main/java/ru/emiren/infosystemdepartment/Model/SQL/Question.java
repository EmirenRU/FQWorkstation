package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
//@RedisHash
public class Question implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questioner;
    private String question;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "protocol_id")
    private List<ProtocolQuestion> protocolQuestion = new ArrayList<>();
}
