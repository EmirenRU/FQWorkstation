package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

public class StudentMapper {

    public static Student mapToStudent(StudentDTO studentDTO){
        return Student.builder()
                .id(studentDTO.getId())
                .fio(studentDTO.getFio())
                .citizenship(studentDTO.getCitizenship())
                .loe(studentDTO.getLoe())
//                .scientificSupervisor(studentDTO.getScientificSupervisor())
                .orientation(studentDTO.getOrientation())
                .department(studentDTO.getDepartment())
//                .lecturer(studentDTO.getLecturer())
//                .fqw(studentDTO.getFqw())
                .build();
    }

    public static StudentDTO mapToStudentDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .fio(student.getFio())
                .loe(student.getLoe())
                .citizenship(student.getCitizenship())
//                .scientificSupervisor(student.getScientificSupervisor())
                .orientation(student.getOrientation())
                .department(student.getDepartment())
//                .lecturer(student.getLecturer())
//                .fqw(student.getFqw())
                .build();
    }
}
