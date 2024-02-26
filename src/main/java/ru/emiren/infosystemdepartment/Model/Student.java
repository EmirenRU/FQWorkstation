package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long   stud_num;                // Студ.номер
    private String fio;                     // ФИО
    private String citizenship;             // Гражданство
    private String theme;                   // Тема
    private String loe;                     // Уровень образования == Level of Education
    private String scientificSupervisor;    // Научный руководитель

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "orientation_code", referencedColumnName = "code")
    private Orientation orientation;          // Направление

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_code", referencedColumnName = "code")
    private Department department;             // Кафедра

    @ManyToOne
    @JoinColumn(name = "lecturer_id", nullable = false)
    private Lecturer lecturer;

    @OneToOne
    @JoinColumn(name = "FQW_name", referencedColumnName = "name")
    private FQW fqw;
}