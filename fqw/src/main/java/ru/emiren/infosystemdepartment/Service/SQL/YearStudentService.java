package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.YearStudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.YearStudent;

import java.util.List;

public interface YearStudentService {
    List<YearStudentDTO> getAllYearStudent();

    YearStudent saveYearStudent(YearStudent ys);
}
