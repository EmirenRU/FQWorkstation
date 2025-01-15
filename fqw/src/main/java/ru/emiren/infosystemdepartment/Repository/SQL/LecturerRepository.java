package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.emiren.infosystemdepartment.DTO.SQL.LecturerDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Lecturer;

import java.util.List;
import java.util.Optional;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

    LecturerDTO findLecturerById(Long id);

    Lecturer findLecturerByName(String name);

    @Query("SELECT l FROM Lecturer l WHERE l.id IN :lId")
    List<Lecturer> findById(@Param("lId") List<Long> id);

    @Query("Select l FROM Lecturer l JOIN StudentLecturers sl ON sl.lecturer.id = l.id")
    List<Lecturer> getAllConnectedLecturers();
}
