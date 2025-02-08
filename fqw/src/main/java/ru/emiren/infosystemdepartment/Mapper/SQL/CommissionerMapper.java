package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.CommissionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;

public class CommissionerMapper {
    public static Commissioner mapToCommisioner(CommissionerDTO commissionerDTO){
        return Commissioner.builder()
                .name(commissionerDTO.getName())
                .department(commissionerDTO.getDepartment())
                .university(commissionerDTO.getUniversity())
                .build();
    }

    public static CommissionerDTO mapToCommisionerDTO(Commissioner commissioner){
        return CommissionerDTO.builder()
                .name(commissioner.getName())
                .university(commissioner.getUniversity())
                .department(commissioner.getDepartment())
                .build();
    }
}
