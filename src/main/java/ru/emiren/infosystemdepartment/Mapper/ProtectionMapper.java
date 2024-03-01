package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.ProtectionDTO;
import ru.emiren.infosystemdepartment.Model.Protection;

public class ProtectionMapper {
    public static Protection mapToProtection(ProtectionDTO protectionDTO){
        return Protection.builder()
                .id(protectionDTO.getId())
                .fio(protectionDTO.getFio())
                .citizenship(protectionDTO.getCitizenship())
                .theme(protectionDTO.getTheme())
                .orientation(protectionDTO.getOrientation())
                .department(protectionDTO.getDepartment())
                .scientificSupervisor(protectionDTO.getScientificSupervisor())
                .consultantFio(protectionDTO.getConsultantFio())
                .dataOfProtection(protectionDTO.getDataOfProtection())
                .build();
    }

    public static ProtectionDTO protectionDTO(Protection protection){
        return ProtectionDTO.builder()
                .id(protection.getId())
                .fio(protection.getFio())
                .citizenship(protection.getCitizenship())
                .theme(protection.getTheme())
                .orientation(protection.getOrientation())
                .department(protection.getDepartment())
                .scientificSupervisor(protection.getScientificSupervisor())
                .consultantFio(protection.getConsultantFio())
                .dataOfProtection(protection.getDataOfProtection())
                .build();
    }
}
