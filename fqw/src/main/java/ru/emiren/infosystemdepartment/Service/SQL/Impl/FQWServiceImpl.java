package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.FQWMapper;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;
import ru.emiren.infosystemdepartment.Repository.SQL.FQWRepository;
import ru.emiren.infosystemdepartment.Service.SQL.FQWService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FQWServiceImpl implements FQWService {
    FQWRepository fqwRepository;

    @Autowired
    public FQWServiceImpl(FQWRepository fqwRepository) {
        this.fqwRepository = fqwRepository;
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
                .collect(Collectors.toList());
    }

    @Override
    public FQW saveFqw(FQW fqw) {
        return fqwRepository.save(fqw);
    }

    @Override
    public FQWDTO getFQWDTO(String name) {
        return fqwRepository.findByName(name);
    }

    @Override
    public FQW updateFQW(String name, FQW fqw) {
        FQW upd = fqwRepository
                .findById(name)
                .orElse(new FQW());

        if (!name.isEmpty()) {
            upd.setName(name);
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
}
