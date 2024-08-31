package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtocolRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtocolService;

@Service
public class ProtocolServiceImpl implements ProtocolService {
    private ProtocolRepository protocolRepository;

    @Autowired
    public ProtocolServiceImpl(ProtocolRepository protocolRepository) {
        this.protocolRepository = protocolRepository;
    }

    @Override
    public Protocol saveProtocol(Protocol protocol) {
        return protocolRepository.save(protocol);
    }
}
