package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;

import java.util.List;

public interface LecturerService {
    List<LecturerDTO> findDtoByLecturerId(List<Long> id);
    Lecturer findByLecturerId(Long id);

    Lecturer findByLecturerName(String name);

    List<LecturerDTO> getAllLecturer();

    LecturerDTO createDummyLecturer();

    List<LecturerDTO> findAllLecturersById(Long lecturerId);

    Lecturer saveLecturer(Lecturer lecturer);

    void deleteLecturer(Lecturer lecturer);

    Lecturer updateLecturer(Long id, Lecturer lecturer);


    List<LecturerDTO> getConnectedLecturers();
}
