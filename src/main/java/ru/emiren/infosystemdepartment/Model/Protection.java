package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Protection {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fio;
    private String citizenship;
    private String theme;
    private String orientation;
    private String department;
    private String scientificSupervisor;
    private String consultantFio;
    private LocalDate dataOfProtection;

}
