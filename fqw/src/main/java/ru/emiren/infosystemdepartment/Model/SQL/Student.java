package ru.emiren.infosystemdepartment.Model.SQL;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long   stud_num;                // Студ.номер
    private String name;                     // ФИО
    private String citizenship;             // Гражданство
    private String loe;                     // Уровень образования == Level of Education
    private String classifier;              // Классификатор

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "theme", referencedColumnName = "id")
    private FQW fqw;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "orientation_code", referencedColumnName = "code")
    private Orientation orientation;          // Направление

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_code", referencedColumnName = "code")
    private Department department;             // Кафедра

    @OneToMany(cascade = CascadeType.ALL )
    @JoinColumn(name = "lecturer_id")
    private List<StudentLecturers> lecturers = new ArrayList<>();


    @Override
    public String toString() {
        return "Student [id=" + id + ", stud_num=" +   stud_num + ", name="         + name +
                ", citizenship=" + citizenship + ", loe=" + loe + ", classifier=" + classifier +
                ", fqw=" + fqw + ", orientation=" + orientation + ", department=" + department +
                "]";
    }
}