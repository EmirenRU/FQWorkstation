package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.SQL.Decree;
import ru.emiren.infosystemdepartment.Repository.SQL.DecreeRepository;
import ru.emiren.infosystemdepartment.Service.SQL.DecreeService;

@Service
public class DecreeServiceImpl implements DecreeService {
    private final DecreeRepository decreeRepository;

    @Autowired
    public DecreeServiceImpl(DecreeRepository decreeRepository) {
        this.decreeRepository = decreeRepository;
    }

    @Override
    public Decree findDecreeByThemeAndNumberOfDecreeAndStudNum(Long studNum, String theme, String numberOfDecree) {
        return decreeRepository.findByThemeAndNumberOfDecreeAndStudNum(studNum,theme, numberOfDecree).orElse(null);
    }

    @Override
    public Decree saveDecree(Decree decree) {
        return decreeRepository.save(decree);
    }

    @Override
    public Long getMaxId() {
        return decreeRepository.getMaxId();
    }

    @Override
    public Decree findDecreeByTheme(String name) {
        return decreeRepository.findByTheme(name).orElse(null);
    }
}
