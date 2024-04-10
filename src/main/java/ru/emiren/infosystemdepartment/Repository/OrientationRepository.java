package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;


public interface OrientationRepository extends JpaRepository<Orientation, String> {

}
