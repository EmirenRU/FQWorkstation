package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.ProtocolDTO;
import ru.emiren.infosystemdepartment.Model.Protocol;

public class ProtocolMapper {
    public static Protocol mapToProtocol(ProtocolDTO protocolDTO){
        return Protocol.builder()
                .fioStudent(protocolDTO.getFioStudent())
                .fqwName(protocolDTO.getFqwName())
                .headOfTheFQW(protocolDTO.getHeadOfTheFQW())
                .review(protocolDTO.getReview())
                .volume(protocolDTO.getVolume())
                .grade(protocolDTO.getGrade())
                .build();
    }

    public static ProtocolDTO mapToProtocolDTO(Protocol protocol){
        return ProtocolDTO.builder()
                .fioStudent(protocol.getFioStudent())
                .fqwName(protocol.getFqwName())
                .headOfTheFQW(protocol.getHeadOfTheFQW())
                .review(protocol.getReview())
                .volume(protocol.getVolume())
                .grade(protocol.getGrade())
                .build();
    }
}
