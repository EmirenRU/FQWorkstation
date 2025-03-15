package ru.emiren.infosystemdepartment.Service.SQL;

import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionCommissionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;

import java.util.List;

public interface ProtectionCommissionerService {
    @Transactional
    ProtectionCommissioner saveProtectionCommissioner(ProtectionCommissioner pc);
    List<ProtectionCommissionerDTO> getAllPC();
    void deleteProtectionCommissioner(ProtectionCommissioner pc);
    ProtectionCommissioner    getProtectionCommissionerById(Long id);
    ProtectionCommissionerDTO getProtectioCommissionerDTOById(Long id);
    ProtectionCommissioner updatePC(ProtectionCommissioner pc);

    Long getMaxId();

    ProtectionCommissioner findByProtectionAndCommissioner(Long id, Long id1);
}
