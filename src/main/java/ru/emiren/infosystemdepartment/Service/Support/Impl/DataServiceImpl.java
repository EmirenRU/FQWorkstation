package ru.emiren.infosystemdepartment.Service.Support.Impl;

import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.Support.DataDTO;
import ru.emiren.infosystemdepartment.Mapper.Support.DataMapper;
import ru.emiren.infosystemdepartment.Model.Support.Data;
import ru.emiren.infosystemdepartment.Repository.Support.DataRepository;
import ru.emiren.infosystemdepartment.Service.Support.DataService;

import java.util.stream.Collectors;

import static ru.emiren.infosystemdepartment.Mapper.Support.DataMapper.dataDTOToData;

@Service
public class DataServiceImpl implements DataService {

    private final DataRepository dataRepository;

    public DataServiceImpl(DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @Override
    public Data saveData(DataDTO dataDTO) {
        return dataRepository.save(dataDTOToData(dataDTO));
    }

    @Override
    public DataDTO getDataById(int id) {
        return dataRepository.findById((long) id)
                .stream()
                .map(DataMapper::dataToDataDTO)
                .toList()
                .getFirst();
    }
}
