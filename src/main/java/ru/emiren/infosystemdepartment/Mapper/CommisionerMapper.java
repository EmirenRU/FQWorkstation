package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.CommisionerDTO;
import ru.emiren.infosystemdepartment.Model.Commisioner;

public class CommisionerMapper {
    public static Commisioner mapToCommisioner(CommisionerDTO commisionerDTO){
        return Commisioner.builder()
                .fio(commisionerDTO.getFio())
                .department(commisionerDTO.getDepartment())
                .university(commisionerDTO.getUniversity())
                .build();
    }

    public static CommisionerDTO mapToCommisionerDTO(Commisioner commisioner){
        return CommisionerDTO.builder()
                .fio(commisioner.getFio())
                .university(commisioner.getUniversity())
                .department(commisioner.getDepartment())
                .build();
    }
}
