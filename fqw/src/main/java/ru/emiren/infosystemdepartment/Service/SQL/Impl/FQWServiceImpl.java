package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Mapper.FQWMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Decree;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;
import ru.emiren.infosystemdepartment.Repository.SQL.FQWRepository;
import ru.emiren.infosystemdepartment.Service.SQL.DecreeService;
import ru.emiren.infosystemdepartment.Service.SQL.FQWService;

import java.util.List;

@Service
public class FQWServiceImpl implements FQWService {
    private final DecreeService decreeService;
    FQWRepository fqwRepository;

    @Autowired
    public FQWServiceImpl(FQWRepository fqwRepository, DecreeService decreeService) {
        this.fqwRepository = fqwRepository;
        this.decreeService = decreeService;
    }

    @Override
    public void deleteFQW(FQW fqw) {
        fqwRepository.delete(fqw);
    }

    @Override
    public List<FQWDTO> getAllFQW() {
        return fqwRepository.findAll()
                .stream()
                .map(FQWMapper::mapToFQWDTO)
                .toList();
    }

    @Override
    public FQW saveFqw(FQW fqw) {
        return fqwRepository.save(fqw);
    }

    @Override
    public FQW getFQW(String name) {
        return fqwRepository.findByNameAndNumberOfDecree(name,null).orElse(null);
    }

    @Override
    public FQWDTO getFQWDTO(String name) {
        return fqwRepository.findDtoByName(name);
    }

    @Override
    public FQW updateFQW(String name, FQW fqw) {
        FQW upd = fqwRepository
                .findById(name)
                .orElse(new FQW());

        Decree decree = decreeService.findDecreeByTheme(name);

        if (decree!=null) {
            upd.setDecree(decree);
        } if (fqw.getClassifier() != null) {
            upd.setClassifier(fqw.getClassifier());
        } if (fqw.getUniqueness() != null) {
            upd.setUniqueness(fqw.getUniqueness());
        } if (fqw.getFeedback() != null) {
        upd.setFeedback(fqw.getFeedback());
        } if (fqw.getVolume() != null) {
            upd.setVolume(fqw.getVolume());
        } if (fqw.getReviewer() != null) {
            upd.setReviewer(fqw.getReviewer());
        }
        return fqwRepository.save(upd);
    }

    @Override
    public FQW getFqwByName(String theme) {
        return fqwRepository.findByNameAndNumberOfDecree(theme, null).orElse(null);
    }

    @Override
    public FQW findByName(String themeName) {
        return fqwRepository.findByNameAndNumberOfDecree(themeName, null).orElse(null);
    }

    @Override
    public Long getMaxId() {
        return fqwRepository.getMaxId();
    }
}
