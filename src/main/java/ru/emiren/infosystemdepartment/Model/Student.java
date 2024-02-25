package ru.emiren.infosystemdepartment.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table()
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Student {

    @Id
    private Long id; // студ.номер

    private String fio; // ФИО
    private String citizenship; // гражданство
    private String theme; // тема
    private String orientation; // направление
    private String loe; // Уровень образования == Level of Education
    private String scientificSupervisor; // Научный руководитель
}
