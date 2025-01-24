package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;

import java.util.List;

public interface OrientationService {
    List<OrientationDTO> getAllOrientations();
    void deleteOrientation(Orientation orientation);
    Orientation saveOrientation(Orientation orientation);
    Orientation getOrientation(String code);
    OrientationDTO getOrientationDTO(String code);
    Orientation updateOrientation(String code, Orientation orientation);

    String OrientationCodeWithNameByStudentNumber(Long studNumber);
}
