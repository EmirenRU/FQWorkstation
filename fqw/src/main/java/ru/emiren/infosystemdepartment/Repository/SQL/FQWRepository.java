package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

import java.util.Optional;

public interface FQWRepository extends JpaRepository<FQW, String> {
    FQWDTO findDtoByName(String name);

    Optional<FQW> findByName(String name);
}
