package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionCommissionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;

import java.util.List;

public interface ProtectionCommissionerService {
    ProtectionCommissioner saveProtectionCommissioner(ProtectionCommissioner pc);
    List<ProtectionCommissionerDTO> getAllPC();
    void deleteProtectionCommissioner(ProtectionCommissioner pc);
    ProtectionCommissionerDTO getPCById(Long id);
    ProtectionCommissioner updatePC(ProtectionCommissioner pc);
}
