package ru.emiren.infosystemdepartment.Service.SQL;

import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtocolDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;

import java.util.List;

public interface ProtocolService {
    @Transactional
    Protocol saveProtocol(Protocol protocol);
    void deleteProtocol(Protocol protocol);
    List<ProtocolDTO> getAllDtoProtocol();

    ProtocolDTO getDtoProtocol(String fullName);
    Protocol getProtocol(String fullName);
    Protocol updateProtocol(Protocol protocol);

    Protocol findByStudentNum(Long studNum);

    Long getMaxId();
} // CD
