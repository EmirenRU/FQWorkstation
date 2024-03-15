package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Reviewer;


public interface ReviewerRepository extends JpaRepository<Reviewer, Long> {  }
