package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

public class StudentLecturersMapper {
    public static StudentLecturers mapToStudentLecturers(StudentLecturersDTO studentLecturersDTO){
        return StudentLecturers.builder()
                .id(studentLecturersDTO.getId())
                .student(studentLecturersDTO.getStudent())
                .lecturer(studentLecturersDTO.getLecturer())
                .isConsultant(studentLecturersDTO.getIsConsultant())
                .isScientificSupervisor(studentLecturersDTO.getIsScientificSupervisor())
                .build();
    }

    public static StudentLecturersDTO mapToStudentLecturersDTO(StudentLecturers studentLecturers){
        return StudentLecturersDTO.builder()
                .id(studentLecturers.getId())
                .student(studentLecturers.getStudent())
                .lecturer(studentLecturers.getLecturer())
                .isScientificSupervisor(studentLecturers.getIsScientificSupervisor())
                .isConsultant(studentLecturers.getIsConsultant())
                .build();
    }
}
