package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Commissioner;

import java.util.Optional;

public interface CommissionerRepository extends JpaRepository<Commissioner, String> {

    Commissioner findById(Long id);

    Optional<Commissioner> findByName(String commissionerName);
}
