package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.Lecturer;

import java.util.List;

public interface LecturerService {
    List<LecturerDTO> getAllLecturer();
}
