package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.YearDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.YearMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.SQL.YearService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class YearServiceImpl implements YearService {
    private YearRepository yearRepository;

    @Autowired
    public YearServiceImpl(YearRepository yearRepository) {
        this.yearRepository = yearRepository;
    }


    @Override
    public Year saveYear(Year year) {
        return yearRepository.save(year);
    }

    @Override
    public List<YearDTO> getYears() {
        return yearRepository.findAll()
                .stream().map(YearMapper::mapToYearDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteYear(Year year) {

    }

    @Override
    public Year getYear(String year) {
        return yearRepository.findById(year).orElse(null);
    }

    @Override
    public YearDTO getYearDTO(String year) {
        return yearRepository.findById(year).map(YearMapper::mapToYearDTO).orElse(null);
    }

    @Override
    public Year updateYear(Year year) {
        Year y = getYear(year.getYear());
        if (y == null) { return yearRepository.save(year); }

        if (year.getStudents() != null) y.setStudents(year.getStudents());
        return yearRepository.save(y);
    }
}
