package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

public interface StudentLecturerRepository extends JpaRepository<StudentLecturers, Long> {
}
