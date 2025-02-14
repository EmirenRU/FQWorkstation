package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrientationDTO {
    private String code;
    private String name;

    private ProtectionDTO protection;

    @Override
    public String toString() {
        return code + " " + name ;
    }
}
