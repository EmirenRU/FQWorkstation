package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

public interface FQWRepository extends JpaRepository<FQW, String> {
    FQWDTO findByName(String name);

    FQW findByName(String name);
}
