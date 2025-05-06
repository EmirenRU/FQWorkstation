package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@RedisHash
public class Orientation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(max = 8)
    private String code;
    private String name;

    @OneToMany(mappedBy = "orientation", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Protection> protection = new ArrayList<>();
}
