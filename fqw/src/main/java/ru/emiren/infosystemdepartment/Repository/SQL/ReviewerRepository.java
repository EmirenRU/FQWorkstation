package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

import java.util.Optional;


public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Optional<Reviewer> findByName(String name);

    @Query("SELECT MAX(r.id) FROM Reviewer r")
    Long getMaxId();
}
