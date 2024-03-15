package ru.emiren.infosystemdepartment.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;

public interface ProtocolRepository extends JpaRepository<Protocol, String> {

}
