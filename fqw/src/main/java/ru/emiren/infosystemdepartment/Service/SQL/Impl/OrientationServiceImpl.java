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
import java.util.Objects;
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
    public void deleteOrientation(Orientation orientation) {
        orientationRepository.delete(orientation);
    }

    @Override
    public Orientation saveOrientation(Orientation orientation) {
        return orientationRepository.save(orientation);
    }

    @Override
    public Orientation getOrientation(String code) {
        return orientationRepository.findOrientationByCode(code);
    }

    @Override
    public OrientationDTO getOrientationDTO(String code) {
        return orientationRepository.findById(code)
                .map(OrientationMapper::mapToOrientationDTO)
                .orElse(null);
    }

    @Override
    public Orientation updateOrientation(String code, Orientation orientation) {
        Orientation upd = orientationRepository.findById(code).orElse(new Orientation());

        if (orientation.getCode() != null) { upd.setCode(orientation.getCode()); }
        if (orientation.getName() != null) { upd.setName(orientation.getName()); }
        if (orientation.getProtection() != null) { upd.setProtection(orientation.getProtection()); }

        return orientationRepository.save(upd);
    }

    @Override
    public String OrientationCodeWithNameByStudentNumber(Long studNumber) {
        Orientation tmp = orientationRepository.findOrientationByStudNumber(studNumber).orElse(null);
        OrientationDTO temp = (tmp != null) ? OrientationMapper.mapToOrientationDTO(tmp) : null;
        return (temp != null) ? temp.getCode() + " " + temp.getName() : null;
    }

    @Override
    public Orientation findByCode(String orientationCode) {
        return orientationRepository.findById(orientationCode).orElse(null);
    }

    @Override
    public OrientationDTO findOrientationByStudentNumber(Long studNumber) {
        Orientation tmp = orientationRepository.findOrientationByStudNumber(studNumber).orElse(null);
        return (tmp != null) ? OrientationMapper.mapToOrientationDTO(tmp) : null;
    }


}
