package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Commisioner;

public interface CommisionerRepository extends JpaRepository<Commisioner, String> {

}
