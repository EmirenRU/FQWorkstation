package ru.emiren.infosystemdepartment.DTO.SQL;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommissionerDTO {
    private String name;
    private String university;
    private String department;

//    private List<ProtectionCommissionerDTO> protectionCommissioners = new ArrayList<>();

}
