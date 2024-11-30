package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionCommissionerDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.ProtectionCommissionerMapper;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtectionCommissionerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtectionCommissionerService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProtectionCommissionerServiceImpl implements ProtectionCommissionerService {

    private final ProtectionCommissionerRepository protectionCommissionerRepository;

    @Autowired
    public ProtectionCommissionerServiceImpl (ProtectionCommissionerRepository protectionCommissionerRepository) {
        this.protectionCommissionerRepository = protectionCommissionerRepository;
    }

    @Override
    public ProtectionCommissioner saveProtectionCommissioner(ProtectionCommissioner pc) {
        return protectionCommissionerRepository.save(pc);
    }

    @Override
    public List<ProtectionCommissionerDTO> getAllPC() {
        return protectionCommissionerRepository.findAll()
                .stream()
                .map(ProtectionCommissionerMapper::mapToProtectionCommissionerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProtectionCommissioner(ProtectionCommissioner pc) {

    }
}
