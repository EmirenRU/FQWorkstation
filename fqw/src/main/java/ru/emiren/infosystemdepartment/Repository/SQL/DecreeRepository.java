package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Decree;

import java.util.Optional;

public interface DecreeRepository extends JpaRepository<Decree, Long> {

    @Query("SELECT d FROM Decree d WHERE d.theme=:theme AND d.numberOfDecree=:numberOfDecree AND d.studNum=:studNum")
    Optional<Decree> findByThemeAndNumberOfDecreeAndStudNum(Long studNum, String theme, String numberOfDecree);

    @Query("SELECT MAX(d.id) FROM Decree d")
    Long getMaxId();

    @Query("SELECT d FROM Decree d WHERE d.theme = :name")
    Optional<Decree> findByTheme(String name);
}
