package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;

import java.util.Optional;

@Repository
public interface OrientationRepository extends JpaRepository<Orientation, Long> {

    OrientationDTO findByCode(String code);

    Optional<Orientation> findOrientationByCode(String code);

    @Query("SELECT o FROM Student st JOIN Orientation o ON st.orientation.code = o.code WHERE st.stud_num = :studNumber")
    Optional<Orientation> findOrientationByStudNumber(Long studNumber);

}
