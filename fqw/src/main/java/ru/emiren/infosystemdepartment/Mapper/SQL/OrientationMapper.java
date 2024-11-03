package ru.emiren.infosystemdepartment.Mapper.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;


public class OrientationMapper {
    public static Orientation mapToOrientation(OrientationDTO orientationDTO){
        return Orientation.builder()
                .code(orientationDTO.getCode())
                .name(orientationDTO.getName())
                .build();
    }

    public static OrientationDTO mapToOrientationDTO(Orientation orientation){
        return OrientationDTO.builder()
                .code(orientation.getCode())
                .name(orientation.getName())
                .build();
    }
}
