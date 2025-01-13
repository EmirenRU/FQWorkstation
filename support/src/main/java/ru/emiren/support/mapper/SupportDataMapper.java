package ru.emiren.support.mapper;


import ru.emiren.support.dto.SupportDataDTO;
import ru.emiren.support.model.SupportData;

public class SupportDataMapper {
    public static SupportDataDTO dataToDataDTO(SupportData data){
        return SupportDataDTO.builder()
                .id(data.getId())
                .fullName(data.getFullName())
                .email(data.getEmail())
                .phone(data.getPhone())
                .description(data.getDescription())
                .build();
    }

    public static SupportData dataDTOToData(SupportDataDTO dataDTO){
        return SupportData.builder()
                .id(dataDTO.getId())
                .fullName(dataDTO.getFullName())
                .email(dataDTO.getEmail())
                .phone(dataDTO.getPhone())
                .description(dataDTO.getDescription())
                .build();
    }
}
