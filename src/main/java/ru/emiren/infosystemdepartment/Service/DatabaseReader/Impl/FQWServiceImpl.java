package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.FQWDTO;
import ru.emiren.infosystemdepartment.Mapper.FQWMapper;
import ru.emiren.infosystemdepartment.Repository.FQWRepository;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.FQWService;

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
    public List<FQWDTO> getAllFQW() {
        return fqwRepository.findAll()
                .stream()
                .map(FQWMapper::mapToFQWDTO)
                .collect(Collectors.toList());
    }
}
