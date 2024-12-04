package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.CommissionerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;

import java.util.List;

public interface CommissionerService {

    Commissioner saveCommissioner(Commissioner commissioner);
    void deleteCommissioner(Commissioner commissioner);
    List<CommissionerDTO> getAllCommissioners();
    Commissioner updateCommissioner(Long id, Commissioner commissioner);
    Commissioner    getCommissioner(Long id);
    CommissionerDTO getCommissionerDTO(Long id);
}
