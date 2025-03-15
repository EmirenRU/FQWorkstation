package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Department {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long code;
    private String name;
}
