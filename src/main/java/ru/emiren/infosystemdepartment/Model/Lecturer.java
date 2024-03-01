package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
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
