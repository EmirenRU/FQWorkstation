package ru.emiren.infosystemdepartment.Service.DatabaseReader;

import ru.emiren.infosystemdepartment.DTO.LecturerDTO;

import java.util.List;

public interface LecturerService {
    LecturerDTO findByLecturerId(Long id);

    LecturerDTO findByLecturerName(String name);

    List<LecturerDTO> getAllLecturer();
}
