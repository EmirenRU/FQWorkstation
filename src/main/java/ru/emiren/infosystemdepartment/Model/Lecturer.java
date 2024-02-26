package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Lecturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fio;

    private String academicDegree; // Ученая степень

    private String position; // должность

    @OneToMany(mappedBy = "lecturer")
    private List<Student> students = new ArrayList<>();
}
