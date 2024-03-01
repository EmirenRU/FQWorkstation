package ru.emiren.infosystemdepartment.DTO;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ProtectionDTO {
    private Long id;
    private String fio;
    private String citizenship;
    private String theme;
    private String orientation;
    private String department;
    private String scientificSupervisor;
    private String consultantFio;
    private LocalDate dataOfProtection;
}
