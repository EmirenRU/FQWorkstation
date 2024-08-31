package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

import java.time.LocalDate;
import java.util.List;

public interface ProtectionService {
    List<ProtectionDTO> getAllProtections();

    LocalDate getDateWithSpecificStudent(StudentLecturersDTO studentLecturersDTO, List<ProtectionDTO> protections);

    Protection saveProtection(Protection protection);
}
