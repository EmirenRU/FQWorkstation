package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.ProtectionDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

public class ProtectionMapper {
    public static Protection mapToProtection(ProtectionDTO protectionDTO){
        return Protection.builder()
                .orientation(protectionDTO.getOrientation())
                .dataOfProtection(protectionDTO.getDataOfProtection())
                .build();
    }

    public static ProtectionDTO protectionDTO(Protection protection){
        return ProtectionDTO.builder()
                .orientation(protection.getOrientation())
                .dataOfProtection(protection.getDataOfProtection())
                .build();
    }
}
