package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lecturer {
    @Id
    private Long id;

    private String fio;

    private String academicDegreeAndPosition; // Ученая степень и должность
}
