package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Long id;

    private Long   stud_num;                // Студ.номер
    private String fio;                     // ФИО
    private String citizenship;             // Гражданство
    private String theme;                   // Тема
    private String orientation;             // Направление
    private String loe;                     // Уровень образования == Level of Education
    private String scientificSupervisor;    // Научный руководитель


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumns({
            @JoinColumn(name = "department_kod",  referencedColumnName = "code"),
            @JoinColumn(name = "department_name", referencedColumnName = "name")
    })
    private Department department;          // Кафедра
}