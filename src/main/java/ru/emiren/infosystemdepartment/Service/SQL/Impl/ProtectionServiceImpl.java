package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtectionDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Mapper.SQL.ProtectionMapper;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtectionRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtectionService;

import java.time.LocalDate;
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
                .map(ProtectionMapper::protectionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public LocalDate getDateWithSpecificStudent(StudentLecturersDTO studentLecturersDTO, List<ProtectionDTO> protections) {
        LocalDate date = null;
        for (ProtectionDTO protectionDTO : protections) {
            if (protectionDTO.getOrientationDTO().getCode().equals(studentLecturersDTO.getStudent().getOrientation().getCode()))
                date = protectionDTO.getDateOfProtection();
        }
        return date;
    }
}
