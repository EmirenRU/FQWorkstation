package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.util.List;

public interface StudentLecturerRepository extends JpaRepository<StudentLecturers, Long> {

    @Query("SELECT sl FROM StudentLecturers sl ORDER BY sl.lecturer.fio")
    List<StudentLecturers> findAllSorted();
}
