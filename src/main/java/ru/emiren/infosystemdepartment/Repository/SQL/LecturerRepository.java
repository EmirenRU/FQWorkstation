package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    LecturerDTO findLecturerById(Long id);

    Lecturer findLecturerByFio(String fio);

}
