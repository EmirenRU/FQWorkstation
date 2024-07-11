package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

public class StudentLecturersMapper {
    public static StudentLecturers mapToStudentLecturers(StudentLecturersDTO studentLecturersDTO){
        return StudentLecturers.builder()
                .id(studentLecturersDTO.getId())
                .student(StudentMapper.mapToStudent(studentLecturersDTO.getStudent()))
                .lecturer(LecturerMapper.mapToLecturer(studentLecturersDTO.getLecturer()))
                .isConsultant(studentLecturersDTO.getIsConsultant())
                .isScientificSupervisor(studentLecturersDTO.getIsScientificSupervisor())
                .build();
    }

    public static StudentLecturersDTO mapToStudentLecturersDTO(StudentLecturers studentLecturers){
        return StudentLecturersDTO.builder()
                .id(studentLecturers.getId())
                .student(StudentMapper.mapToStudentDTO(studentLecturers.getStudent()))
                .lecturer(LecturerMapper.mapToLecturerDTO(studentLecturers.getLecturer()))
                .isScientificSupervisor(studentLecturers.getIsScientificSupervisor())
                .isConsultant(studentLecturers.getIsConsultant())
                .build();
    }
}
