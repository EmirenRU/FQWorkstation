package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.Payload.Selector.OrientationSelector;
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

    public static OrientationSelector mapToSelector(OrientationDTO orientationDTO){
        return OrientationSelector.builder()
                .name(orientationDTO.getCode() + " " + orientationDTO.getName())
                .value(orientationDTO.getCode())
                .build();
    }
}
