package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.stream.Collectors;

public class StudentMapper {

    public static Student mapToStudent(StudentDTO studentDTO){
        return Student.builder()
                .id(studentDTO.getId())
                .stud_num(studentDTO.getStud_num())
                .fio(studentDTO.getFio())
                .citizenship(studentDTO.getCitizenship())
                .loe(studentDTO.getLoe())
                .orientation(studentDTO.getOrientation())
                .department(studentDTO.getDepartment())
                .fqw(studentDTO.getFqw())
                .lecturers(studentDTO.getLecturers().stream().map(StudentLecturersMapper::mapToStudentLecturers).collect(Collectors.toList()))
                .build();
    }

    public static StudentDTO mapToStudentDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .stud_num(student.getStud_num())
                .fio(student.getFio())
                .loe(student.getLoe())
                .citizenship(student.getCitizenship())
                .orientation(student.getOrientation())
                .department(student.getDepartment())
                .lecturers(student.getLecturers().stream().map(StudentLecturersMapper::mapToStudentLecturersDTO).collect(Collectors.toList()))
                .fqw(student.getFqw())
                .build();
    }
}
