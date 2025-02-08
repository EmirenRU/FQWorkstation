package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtocolDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;

import java.util.List;

public interface ProtocolService {
    Protocol saveProtocol(Protocol protocol);
    void deleteProtocol(Protocol protocol);
    List<ProtocolDTO> getAllDtoProtocol();

    ProtocolDTO getDtoProtocol(String fullName);
    Protocol getProtocol(String fullName);
    Protocol updateProtocol(Protocol protocol);

    Protocol findByStudentNum(Long studNum);
} // CD
