package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionCommissionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;

public interface ProtectionCommissionerRepository extends JpaRepository<ProtectionCommissioner,Integer> {
    ProtectionCommissionerDTO findById(Long id);

    ProtectionCommissioner findProtectionCommissionersById(Long id);

    @Query("SELECT MAX(pc.id) FROM ProtectionCommissioner pc")
    Long getMaxId();
}
