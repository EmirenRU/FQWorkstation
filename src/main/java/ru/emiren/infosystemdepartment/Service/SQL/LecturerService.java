package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;

import java.util.List;

public interface LecturerService {
    LecturerDTO findByLecturerId(Long id);

    LecturerDTO findByLecturerName(String name);

    List<LecturerDTO> getAllLecturer();

    LecturerDTO createDummyLecturer();

    List<LecturerDTO> findAllLecturersById(Long lecturerId);
}
