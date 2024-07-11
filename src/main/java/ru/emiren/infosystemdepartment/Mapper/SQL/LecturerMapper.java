package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;

import java.util.stream.Collectors;

public class LecturerMapper {
    public static Lecturer mapToLecturer(LecturerDTO lecturerDTO){
        return Lecturer.builder()
                .id(lecturerDTO.getId())
                .fio(lecturerDTO.getFio())
                .academicDegree(lecturerDTO.getAcademicDegree())
                .position(lecturerDTO.getPosition())
                .department(DepartmentMapper.mapToDepartment(lecturerDTO.getDepartment()))
//                .students(lecturerDTO.getStudents().stream().map(StudentLecturersMapper::mapToStudentLecturers).collect(Collectors.toList()))
                .build();
    }

    public static LecturerDTO mapToLecturerDTO(Lecturer lecturer){
        return LecturerDTO.builder()
                .id(lecturer.getId())
                .fio(lecturer.getFio())
                .academicDegree(lecturer.getAcademicDegree())
                .position(lecturer.getPosition())
                .department(DepartmentMapper.mapToDepartmentDTO(lecturer.getDepartment()))
//                .students(lecturer.getStudents().stream().map(StudentLecturersMapper::mapToStudentLecturersDTO).collect(Collectors.toList()))
                .build();
    }
}
