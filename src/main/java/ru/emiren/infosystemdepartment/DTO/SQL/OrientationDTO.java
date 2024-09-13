package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

import java.util.List;

@Data
@Builder
public class OrientationDTO {
    private String code;
    private String name;
    private List<ProtectionDTO> protection;


    @Override
    public String toString() {
        return code + " " + name ;
    }
}
