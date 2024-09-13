package ru.emiren.infosystemdepartment.DTO.SQL;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

public class ProtectionCommissionerDTO {
    private Long id;

    private ProtectionDTO protection;
    private CommisionerDTO commissioner;

    private String question;
}
