package ru.emiren.infosystemdepartment.Model.SQL;

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

    @ManyToOne
    @JoinColumn(name="department", referencedColumnName = "code")
    private Department department;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "student_id")
    private List<StudentLecturers> students = new ArrayList<>();
}
