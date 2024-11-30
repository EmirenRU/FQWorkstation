package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.YearStudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.YearStudent;

import java.util.List;

public class YearStudentMapper {
    public static YearStudent mapToYearStudent(YearStudentDTO yearStudentDTO){
        return YearStudent.builder()
                .id(yearStudentDTO.getId())
                .year(YearMapper.mapToYear(yearStudentDTO.getYear()))
                .student(StudentMapper.mapToStudent(yearStudentDTO.getStudent()))
                .build();
    }

    public static YearStudentDTO mapToYearStudentDTO(YearStudent yearStudent){
        return YearStudentDTO.builder()
                .id(yearStudent.getId())
                .year(YearMapper.mapToYearDTO(yearStudent.getYear()))
                .student(StudentMapper.mapToStudentDTO(yearStudent.getStudent()))
                .build();
    }
}
