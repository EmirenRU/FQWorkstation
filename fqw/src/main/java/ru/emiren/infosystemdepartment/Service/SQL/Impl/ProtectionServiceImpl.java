package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Mapper.ProtectionMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtectionRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtectionService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProtectionServiceImpl implements ProtectionService {
    private ProtectionRepository protectionRepository;

    @Autowired
    public ProtectionServiceImpl(ProtectionRepository protectionRepository) {
        this.protectionRepository = protectionRepository;
    }

    @Override
    public List<ProtectionDTO> getAllProtections() {
        return protectionRepository.findAll().stream()
                .map(ProtectionMapper::mapToProtectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Integer getDateWithSpecificStudent(StudentLecturersDTO studentLecturersDTO, List<ProtectionDTO> protections) {
        Integer date = null;
        for (ProtectionDTO protectionDTO : protections) {
            if (protectionDTO.getOrientation().getCode().equals(studentLecturersDTO.getStudent().getOrientation().getCode()))
                date = protectionDTO.getDateOfProtection();
        }
        return date;
    }

    @Override
    public void deleteProtection(Protection protection) {
        protectionRepository.delete(protection);
    }

    @Override
    public Protection saveProtection(Protection protection) {
        return protectionRepository.save(protection);
    }

    @Override
    public Protection getProtectionById(Long id) {
        return protectionRepository.findById(id).orElse(null);
    }

    @Override
    public ProtectionDTO getProtectionDTOById(Long id) {
        return protectionRepository.findById(id).map(ProtectionMapper::mapToProtectionDTO).orElse(null);
    }

    @Override
    public Protection updateProtection(Protection protection) {
        Protection oldProtection = getProtectionById(protection.getId());
        if (oldProtection == null) { return protectionRepository.save(protection); }

        if (protection.getDateOfProtection() != null) { oldProtection.setDateOfProtection(protection.getDateOfProtection()); }
        if (protection.getOrientation() != null) { oldProtection.setOrientation(protection.getOrientation()); }
        if (protection.getCommissioners() != null) { oldProtection.setCommissioners(protection.getCommissioners()); }

        return protectionRepository.save(oldProtection);
    }
}
