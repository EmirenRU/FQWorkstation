package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;

public class LecturerMapper {
    public static Lecturer mapToLecturer(LecturerDTO lecturerDTO){
        return Lecturer.builder()
                .id(lecturerDTO.getId())
                .name(lecturerDTO.getName())
                .academicDegree(lecturerDTO.getAcademicDegree())
                .position(lecturerDTO.getPosition())
                .department(DepartmentMapper.mapToDepartment(lecturerDTO.getDepartment()))
                .build();
    }

    public static LecturerDTO mapToLecturerDTO(Lecturer lecturer){
        return LecturerDTO.builder()
                .id(lecturer.getId())
                .name(lecturer.getName())
                .academicDegree(lecturer.getAcademicDegree())
                .position(lecturer.getPosition())
                .department(DepartmentMapper.mapToDepartmentDTO(lecturer.getDepartment()))
//                .students(lecturer.getStudents().stream().map(StudentMapper::mapToStudentDTO).collect(Collectors.toList()))
                .build();
    }
}
