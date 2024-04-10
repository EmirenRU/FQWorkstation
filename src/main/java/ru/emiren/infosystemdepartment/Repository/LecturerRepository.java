package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;

import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    LecturerDTO findLecturerById(Long id);

    Lecturer findLecturerByFio(String fio);

}
