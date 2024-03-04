package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import ru.emiren.infosystemdepartment.DTO.OrientationDTO;

import java.util.List;

public interface OrientationService {
    List<OrientationDTO> getAllOrientations();
}
