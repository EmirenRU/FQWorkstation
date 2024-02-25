package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Protection {

    @Id
    private Long id;
    private String fio;
    private String citizenship;
    private String theme;
    private String orientation;
    private String department;
    private String scientificSupervisor;
    private String ConsultantFio;
    private LocalDate dataOfProtection;

}
