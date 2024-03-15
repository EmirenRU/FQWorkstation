package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

public interface FQWRepository extends JpaRepository<FQW, String> {
}
