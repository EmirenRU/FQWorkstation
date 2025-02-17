package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtocolDTO;
import ru.emiren.infosystemdepartment.Mapper.ProtocolMapper;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtocolRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtocolService;

import java.util.List;

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

    @Override
    public void deleteProtocol(Protocol protocol) {
        protocolRepository.delete(protocol);
    }

    @Override
    public List<ProtocolDTO> getAllDtoProtocol() {
        return protocolRepository.findAll()
                .stream()
                .map(ProtocolMapper::mapToProtocolDTO)
                .toList();
    }

    @Override
    public ProtocolDTO getDtoProtocol(String fullName) {
        return protocolRepository.findById(fullName)
                .map(ProtocolMapper::mapToProtocolDTO)
                .orElse(null);
    }

    @Override
    public Protocol getProtocol(String fullName) {
        return protocolRepository.findById(fullName).orElse(null);
    }

    @Override
    public Protocol updateProtocol(Protocol protocol) {
        Protocol upd = getProtocol(protocol.getStudent().getName());
        if (upd == null) return protocolRepository.save(protocol);

        if (protocol.getGrade() != null)  upd.setGrade(protocol.getGrade());
        if (protocol.getVolume() != null) upd.setVolume(protocol.getVolume());
        if (protocol.getReview() != null) upd.setReview(protocol.getReview());
        if (protocol.getFqw() != null)    upd.setFqw(protocol.getFqw());
        if (protocol.getHeadOfTheFQW() != null) upd.setHeadOfTheFQW(protocol.getHeadOfTheFQW());

        return protocolRepository.save(upd);
    }

    @Override
    public Protocol findByStudentNum(Long studNum) {
        return protocolRepository.findByStudentNumber(studNum).orElse(null);
    }
}
