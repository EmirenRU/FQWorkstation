package ru.emiren.infosystemdepartment.Mapper.Support;

import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Model.Support.Data;

public class DataMapper {
    public static DataDTO dataToDataDTO(Data data){
        return DataDTO.builder()
                .id(data.getId())
                .fullName(data.getFullName())
                .email(data.getEmail())
                .phone(data.getPhone())
                .description(data.getDescription())
                .build();
    }

    public static Data dataDTOToData(DataDTO dataDTO){
        return Data.builder()
                .id(dataDTO.getId())
                .fullName(dataDTO.getFullName())
                .email(dataDTO.getEmail())
                .phone(dataDTO.getPhone())
                .description(dataDTO.getDescription())
                .build();
    }
}
