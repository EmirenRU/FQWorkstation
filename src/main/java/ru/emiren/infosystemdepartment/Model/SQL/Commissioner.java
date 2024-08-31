package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Commissioner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String university;
    private String department;
    @OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "commissioner_id")
    private List<ProtectionCommissioner> protectionCommissioners = new ArrayList<>();
}
