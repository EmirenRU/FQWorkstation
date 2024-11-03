package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtectionCommissionerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtectionCommissionerService;

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
}
