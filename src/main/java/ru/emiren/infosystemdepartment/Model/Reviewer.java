package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Reviewer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fio;
    private String academicDegree; // Ученая степень
    private String position; // должность


}
