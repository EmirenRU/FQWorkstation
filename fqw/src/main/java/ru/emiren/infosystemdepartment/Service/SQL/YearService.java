package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.DTO.SQL.YearDTO;

import java.util.List;

public interface YearService {
    Year saveYear(Year year);
    List<YearDTO> getYears();
    void deleteYear(Year year);
}
