package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

import java.util.List;

public interface FQWService {
    List<FQWDTO> getAllFQW();
    void deleteFQW(FQW fqw);
    FQW saveFqw(FQW fqw);
    FQW    getFQW(String name);
    FQWDTO getFQWDTO(String name);
    FQW updateFQW(String name, FQW fqw);

    FQW getFqwByName(String theme);

    FQW findByName(String themeName);

    Long getMaxId();
}
