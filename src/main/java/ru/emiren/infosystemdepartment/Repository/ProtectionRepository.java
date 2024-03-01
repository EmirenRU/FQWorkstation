package ru.emiren.infosystemdepartment.Repository;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.Protection;

import java.time.LocalDate;


public interface ProtectionRepository extends JpaRepository<Protection, Long> {

}
