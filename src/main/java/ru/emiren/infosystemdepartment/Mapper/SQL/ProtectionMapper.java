package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

public class ProtectionMapper {
    public static Protection mapToProtection(ProtectionDTO protectionDTO){
        return Protection.builder()
                .id(protectionDTO.getId())
                .orientation(OrientationMapper.mapToOrientation(protectionDTO.getOrientationDTO()))
                .dateOfProtection(protectionDTO.getDateOfProtection())
                .build();
    }

    public static ProtectionDTO protectionDTO(Protection protection){
        return ProtectionDTO.builder()
                .id(protection.getId())
                .orientationDTO(OrientationMapper.mapToOrientationDTO(protection.getOrientation()))
                .dateOfProtection(protection.getDateOfProtection())
                .build();
    }
}
