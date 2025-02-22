package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.SQL.DecreeDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Decree;

public class DecreeMapper {
    public static Decree mapToDecree(DecreeDTO decreeDTO){
        return Decree.builder()
                .id(decreeDTO.getId())
                .studNum(decreeDTO.getStudNum())
                .theme(decreeDTO.getTheme())
                .numberOfDecree(decreeDTO.getNumberOfDecree())
                .build();
    }

    public static DecreeDTO mapToDecreeDTO(Decree decree){
        return DecreeDTO.builder()
                .id(decree.getId())
                .studNum(decree.getStudNum())
                .theme(decree.getTheme())
                .numberOfDecree(decree.getNumberOfDecree())
                .build();
    }
}
