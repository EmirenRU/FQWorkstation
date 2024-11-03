package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.SQL.Year;
import ru.emiren.infosystemdepartment.Repository.SQL.YearRepository;
import ru.emiren.infosystemdepartment.Service.SQL.YearService;

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
}
