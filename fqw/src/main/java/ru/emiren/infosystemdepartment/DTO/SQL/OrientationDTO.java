package ru.emiren.infosystemdepartment.DTO.SQL;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

import java.util.List;

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
