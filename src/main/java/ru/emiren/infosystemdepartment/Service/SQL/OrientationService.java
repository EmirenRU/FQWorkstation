package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;

import java.util.List;

public interface OrientationService {
    List<OrientationDTO> getAllOrientations();
}
