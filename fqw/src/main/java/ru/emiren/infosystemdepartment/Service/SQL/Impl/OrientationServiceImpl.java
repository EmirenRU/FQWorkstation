package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;
import ru.emiren.infosystemdepartment.Repository.SQL.OrientationRepository;
import ru.emiren.infosystemdepartment.Service.SQL.OrientationService;
import ru.emiren.infosystemdepartment.Mapper.SQL.OrientationMapper;

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

    @Override
    public Orientation saveOrientation(Orientation orientation) {
        return orientationRepository.save(orientation);
    }


}
