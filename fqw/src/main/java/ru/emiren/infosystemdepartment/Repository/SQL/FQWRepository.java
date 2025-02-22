package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.DTO.SQL.FQWDTO;
import ru.emiren.infosystemdepartment.Model.SQL.FQW;

import java.util.Optional;

public interface FQWRepository extends JpaRepository<FQW, String> {
    @Query("SELECT f from FQW f WHERE f.decree.theme = :name and (f.decree.numberOfDecree IS NULL or f.decree.numberOfDecree = :numberOfDecree)")
    FQWDTO findDtoByName(String name);

    @Query("SELECT f from FQW f WHERE f.decree.theme = :name and (f.decree.numberOfDecree IS NULL or f.decree.numberOfDecree = :numberOfDecree)")
    Optional<FQW> findByNameAndNumberOfDecree(String name, String numberOfDecree);

    @Query("SELECT MAX(f.id) FROM FQW f")
    Long getMaxId();
}
