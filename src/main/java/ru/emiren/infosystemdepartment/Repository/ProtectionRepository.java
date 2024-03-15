package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;


public interface ProtectionRepository extends JpaRepository<Protection, Long> {

}
