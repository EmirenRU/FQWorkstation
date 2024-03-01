package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.Commisioner;

public interface CommisionerRepository extends JpaRepository<Commisioner, String> {

}
