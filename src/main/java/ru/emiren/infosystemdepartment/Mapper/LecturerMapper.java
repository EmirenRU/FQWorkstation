package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.Lecturer;

import java.util.stream.Collectors;

public class LecturerMapper {
    public static Lecturer mapToLecturer(LecturerDTO lecturerDTO){
        return Lecturer.builder()
                .id(lecturerDTO.getId())
                .fio(lecturerDTO.getFio())
                .academicDegree(lecturerDTO.getAcademicDegree())
                .position(lecturerDTO.getPosition())
                .build();
    }

    public static LecturerDTO mapToLecturerDTO(Lecturer lecturer){
        return LecturerDTO.builder()
                .id(lecturer.getId())
                .fio(lecturer.getFio())
                .academicDegree(lecturer.getAcademicDegree())
                .position(lecturer.getPosition())
//                .students(lecturer.getStudents().stream().map(StudentMapper::mapToStudentDTO).collect(Collectors.toList()))
                .build();
    }
}
