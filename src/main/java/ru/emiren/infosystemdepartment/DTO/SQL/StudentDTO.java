package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class StudentDTO implements Serializable {
    private Long id;

    private Long   stud_num;                // Студ.номер
    private String fio;                     // ФИО
    private String citizenship;             // Гражданство
    private String loe;                     // Уровень образования == Level of Education
    private String classifier;              // Классификатор

    private FQWDTO fqw;

    private OrientationDTO orientation;          // Направление

    private DepartmentDTO department;             // Кафедра

    private List<StudentLecturersDTO> lecturers;
}
