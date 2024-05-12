package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.YearStudent;

public interface YearStudentRepository extends JpaRepository<YearStudent, Long> {
}
