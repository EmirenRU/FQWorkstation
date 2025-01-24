package ru.emiren.infosystemdepartment.DTO.SQL;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProtectionCommissionerDTO {
    private Long id;

    private ProtectionDTO protection;
    private CommissionerDTO commissioner;

}
