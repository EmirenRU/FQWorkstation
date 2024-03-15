package ru.emiren.infosystemdepartment.Service.DatabaseReader.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.OrientationDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;
import ru.emiren.infosystemdepartment.Repository.OrientationRepository;
import ru.emiren.infosystemdepartment.Service.DatabaseReader.OrientationService;
import ru.emiren.infosystemdepartment.Mapper.OrientationMapper;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrientationServiceImpl implements OrientationService {

    private OrientationRepository orientationRepository;

    @Autowired
    public OrientationServiceImpl(OrientationRepository orientationRepository){
        this.orientationRepository = orientationRepository;
    }

    @Override
    public List<OrientationDTO> getAllOrientations() {
        List<Orientation> orientations = orientationRepository.findAll();
        return orientations.stream().map(OrientationMapper::mapToOrientationDTO).collect(Collectors.toList());
    }
}
