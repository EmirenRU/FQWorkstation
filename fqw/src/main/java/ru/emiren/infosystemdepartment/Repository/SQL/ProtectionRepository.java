package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Protection;

import java.util.Optional;


public interface ProtectionRepository extends JpaRepository<Protection, Long> {
    @Query("SELECT prt FROM Protection prt WHERE prt.orientation.code = :orientationCode")
    public Protection findDateByOrientationCode(String orientationCode);

    @Query("SELECT MAX(p.id) FROM Protection p")
    Long getMaxId();

    @Query("SELECT p FROM Protection p WHERE p.orientation.code = :code and p.dateOfProtection = :dateOfProtection")
    Optional<Protection> findByOrientationCodeAndDateOfProtection(String code, Integer dateOfProtection);
}
