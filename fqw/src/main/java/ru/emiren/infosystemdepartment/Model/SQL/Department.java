package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@RedisHash
public class Department implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String name;
}
