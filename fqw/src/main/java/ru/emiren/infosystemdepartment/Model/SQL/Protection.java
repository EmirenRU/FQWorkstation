package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;
//import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
//@RedisHash
public class Protection implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orientation_code", referencedColumnName = "id")
    private Orientation orientation;          // Направление

    private Integer dateOfProtection; // Year

    @OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "protection_id")
    private List<ProtectionCommissioner> commissioners = new ArrayList<>();
}
