package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;


public interface ProtectionRepository extends JpaRepository<Protection, Long> {
    @Query("SELECT prt FROM Protection prt WHERE prt.orientation.code = :orientationCode")
    public Protection findDateByOrientationCode(String orientationCode);
}
