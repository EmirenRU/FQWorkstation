package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.YearDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Year;

import java.util.stream.Collectors;

public class YearMapper {
    public static Year mapToYear(YearDTO yr){
        return Year.builder()
                .yearDate(yr.getYearDate())
                .students(yr.getStudents().stream().map(YearStudentMapper::mapToYearStudent).toList())
                .build();
    }

    public static YearDTO mapToYearDTO(Year year){
        return YearDTO.builder()
                .yearDate(year.getYearDate())
                .students(year.getStudents().stream().map(YearStudentMapper::mapToYearStudentDTO).toList())
                .build();
    }
}
