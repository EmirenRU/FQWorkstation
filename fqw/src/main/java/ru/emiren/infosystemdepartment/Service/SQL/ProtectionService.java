package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

import java.time.Year;
import java.util.List;

public interface ProtectionService {
    List<ProtectionDTO> getAllProtections();
    Year getDateWithSpecificStudent(StudentLecturersDTO studentLecturersDTO, List<ProtectionDTO> protections);
    void deleteProtection(Protection protection);
    Protection saveProtection(Protection protection);
    Protection getProtectionById(Long id);
    ProtectionDTO getProtectionDTOById(Long id);
    Protection updateProtection(Protection protection);

}
