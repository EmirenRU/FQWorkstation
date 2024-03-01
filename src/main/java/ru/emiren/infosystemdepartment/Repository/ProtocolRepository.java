package ru.emiren.infosystemdepartment.Repository;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.Protocol;

public interface ProtocolRepository extends JpaRepository<Protocol, String> {

}
