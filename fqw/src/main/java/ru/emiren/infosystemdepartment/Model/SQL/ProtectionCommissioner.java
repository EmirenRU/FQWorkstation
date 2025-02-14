package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "protection_commissioner")
public class ProtectionCommissioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "protection_id", referencedColumnName = "id")
    private Protection protection;
    @ManyToOne
    @JoinColumn(name = "commissioner_id", referencedColumnName = "id")
    private Commissioner commissioner;

}
