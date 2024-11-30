package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.YearDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Year;

import java.util.stream.Collectors;

public class YearMapper {
    public static Year mapToYear(YearDTO yr){
        return Year.builder()
                .year(yr.getYear())
                .students(yr.getStudents().stream().map(YearStudentMapper::mapToYearStudent).collect(Collectors.toList()))
                .build();
    }

    public static YearDTO mapToYearDTO(Year year){
        return YearDTO.builder()
                .year(year.getYear())
                .students(year.getStudents().stream().map(YearStudentMapper::mapToYearStudentDTO).collect(Collectors.toList()))
                .build();
    }
}
