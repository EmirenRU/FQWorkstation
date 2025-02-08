package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.stream.Collectors;

public class StudentMapper {

    public static Student mapToStudent(StudentDTO studentDTO){
        return Student.builder()
                .id(studentDTO.getId())
                .stud_num(studentDTO.getStud_num())
                .name(studentDTO.getName())
                .citizenship(studentDTO.getCitizenship())
                .loe(studentDTO.getLoe())
                .orientation(OrientationMapper.mapToOrientation(studentDTO.getOrientation()))
                .department(DepartmentMapper.mapToDepartment(studentDTO.getDepartment()))
                .fqw(FQWMapper.mapToFQW(studentDTO.getFqw()))
//                .lecturers(studentDTO.getLecturers().stream().map(StudentLecturersMapper::mapToStudentLecturers).collect(Collectors.toList()))
                .build();
    }

    public static StudentDTO mapToStudentDTO(Student student) {
        return StudentDTO.builder()
                .id(student.getId())
                .stud_num(student.getStud_num())
                .name(student.getName())
                .loe(student.getLoe())
                .citizenship(student.getCitizenship())
                .orientation(OrientationMapper.mapToOrientationDTO(student.getOrientation()))
                .department(DepartmentMapper.mapToDepartmentDTO(student.getDepartment()))
//                .lecturers(student.getLecturers().stream().map(StudentLecturersMapper::mapToStudentLecturersDTO).collect(Collectors.toList()))
                .fqw(FQWMapper.mapToFQWDTO(student.getFqw()))
                .build();
    }

}
