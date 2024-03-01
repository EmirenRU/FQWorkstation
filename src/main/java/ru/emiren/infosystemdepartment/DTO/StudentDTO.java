package ru.emiren.infosystemdepartment.DTO;

import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.Department;
import ru.emiren.infosystemdepartment.Model.FQW;
import ru.emiren.infosystemdepartment.Model.Lecturer;
import ru.emiren.infosystemdepartment.Model.Orientation;

@Data
@Builder
public class StudentDTO {
    private Long id;

    private Long   stud_num;                // Студ.номер
    private String fio;                     // ФИО
    private String citizenship;             // Гражданство
    private String theme;                   // Тема
    private String loe;                     // Уровень образования == Level of Education
    private String scientificSupervisor;    // Научный руководитель
    private Orientation orientation;          // Направление
    private Department department;             // Кафедра
    private Lecturer lecturer;
    private FQW fqw;
}
