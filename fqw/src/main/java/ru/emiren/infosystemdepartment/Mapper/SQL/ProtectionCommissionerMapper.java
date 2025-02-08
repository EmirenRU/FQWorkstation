package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionCommissionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;

import java.util.List;

public class ProtectionCommissionerMapper {

    public static ProtectionCommissioner mapToProtectionCommissioner(ProtectionCommissionerDTO pc){
        return ProtectionCommissioner.builder()
                .id(pc.getId())
                .commissioner(CommissionerMapper.mapToCommisioner(pc.getCommissioner()))
                .protection(ProtectionMapper.mapToProtection(pc.getProtection()))
                .build();
    }

    public static ProtectionCommissionerDTO mapToProtectionCommissionerDTO(ProtectionCommissioner pc){
        return ProtectionCommissionerDTO.builder()
                .id(pc.getId())
                .commissioner(CommissionerMapper.mapToCommisionerDTO(pc.getCommissioner()))
                .protection(ProtectionMapper.mapToProtectionDTO(pc.getProtection()))
                .build();
    }
}
