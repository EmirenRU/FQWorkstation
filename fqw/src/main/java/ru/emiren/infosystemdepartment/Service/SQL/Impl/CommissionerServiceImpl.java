package ru.emiren.infosystemdepartment.Service.SQL.Impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;
import ru.emiren.infosystemdepartment.Repository.SQL.CommisionerRepository;
import ru.emiren.infosystemdepartment.Service.SQL.CommissionerService;

@Service
public class CommissionerServiceImpl implements CommissionerService {

    private CommisionerRepository commisionerRepository;

    @Autowired
    public CommissionerServiceImpl(CommisionerRepository commisionerRepository) {
        this.commisionerRepository = commisionerRepository;
    }

    @Override
    public Commissioner saveCommissioner(Commissioner commissioner) {
        return commisionerRepository.save(commissioner);
    }
}
