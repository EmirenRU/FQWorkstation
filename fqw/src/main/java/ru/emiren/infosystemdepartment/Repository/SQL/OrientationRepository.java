package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.DTO.SQL.OrientationDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;


public interface OrientationRepository extends JpaRepository<Orientation, String> {


    OrientationDTO findByCode(String code);

    Orientation findOrientationByCode(String code);

    @Query("SELECT o FROM Student st JOIN Orientation o ON st.orientation.code = o.code")
    Orientation findOrientationByStudNumber(Long studNumber);
}
