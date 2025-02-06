package ru.emiren.infosystemdepartment.Repository.SQL;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;

import java.util.Optional;

public interface ProtocolRepository extends JpaRepository<Protocol, String> {

    @Query("SELECT p FROM Protocol p WHERE p.student.stud_num = :studNum")
    Optional<Protocol> findByStudentNumber(Long studNum);
}
