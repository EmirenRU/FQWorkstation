package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.CommisionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;

public class CommisionerMapper {
    public static Commissioner mapToCommisioner(CommisionerDTO commisionerDTO){
        return Commissioner.builder()
                .name(commisionerDTO.getName())
                .department(commisionerDTO.getDepartment())
                .university(commisionerDTO.getUniversity())
                .build();
    }

    public static CommisionerDTO mapToCommisionerDTO(Commissioner commissioner){
        return CommisionerDTO.builder()
                .name(commissioner.getName())
                .university(commissioner.getUniversity())
                .department(commissioner.getDepartment())
                .build();
    }
}
