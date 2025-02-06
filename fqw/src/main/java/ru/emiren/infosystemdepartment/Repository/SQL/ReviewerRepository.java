package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;

import java.util.Optional;


public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {
    Optional<Reviewer> findByName(String name);
}
