package ru.emiren.infosystemdepartment.Service.SQL.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.CommissionerDTO;
import ru.emiren.infosystemdepartment.Mapper.CommissionerMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;
import ru.emiren.infosystemdepartment.Repository.SQL.CommissionerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.CommissionerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommissionerServiceImpl implements CommissionerService {

    private CommissionerRepository commissionerRepository;

    @Autowired
    public CommissionerServiceImpl(CommissionerRepository commisionerRepository) {
        this.commissionerRepository = commisionerRepository;
    }

    @Override
    public Commissioner saveCommissioner(Commissioner commissioner) {
        return commissionerRepository.save(commissioner);
    }

    @Override
    public void deleteCommissioner(Commissioner commissioner) {
        commissionerRepository.delete(commissioner);
    }

    @Override
    public List<CommissionerDTO> getAllCommissioners() {
        return commissionerRepository.findAll().stream()
                .map(CommissionerMapper::mapToCommisionerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Commissioner updateCommissioner(Long id, Commissioner commissioner) {
        Commissioner updated = commissionerRepository
                               .findById(String.valueOf(id))
                               .orElse(new Commissioner());
        updated.setId(id);

        if (commissioner.getName() != null)  { updated.setName(commissioner.getName()); }
        if (commissioner.getDepartment() != null) { updated.setDepartment(commissioner.getDepartment());}
        if (commissioner.getUniversity() != null) { updated.setUniversity(commissioner.getUniversity());}
        if (commissioner.getProtectionCommissioners() != null) { updated.setProtectionCommissioners(commissioner.getProtectionCommissioners());}

        return commissionerRepository.save(updated);
    }

    @Override
    public Commissioner getCommissioner(Long id) {
        return commissionerRepository.findById(id);
    }

    @Override
    public CommissionerDTO getCommissionerDTO(Long id) {
        return CommissionerMapper.mapToCommisionerDTO(commissionerRepository.findById(id));
    }

    @Override
    public Commissioner findByName(String commissionerName) {
        return commissionerRepository.findByName(commissionerName).orElse(null);
    }
}
