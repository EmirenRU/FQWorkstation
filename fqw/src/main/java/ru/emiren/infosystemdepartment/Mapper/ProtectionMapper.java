package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

public class ProtectionMapper {
    public static Protection mapToProtection(ProtectionDTO protectionDTO){
        return Protection.builder()
                .id(protectionDTO.getId())
                .orientation(protectionDTO.getOrientation())
                .dateOfProtection(protectionDTO.getDateOfProtection())
                .build();
    }

    public static ProtectionDTO mapToProtectionDTO(Protection protection){
        return ProtectionDTO.builder()
                .id(protection.getId())
                .orientation(protection.getOrientation())
                .dateOfProtection(protection.getDateOfProtection())
                .build();
    }

}
