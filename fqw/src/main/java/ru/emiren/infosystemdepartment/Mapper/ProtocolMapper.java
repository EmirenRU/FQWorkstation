package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.SQL.ProtocolDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Protocol;

public class ProtocolMapper {
    public static Protocol mapToProtocol(ProtocolDTO protocolDTO){
        return Protocol.builder()
                .student(StudentMapper.mapToStudent(protocolDTO.getStudent()))
                .fqw(FQWMapper.mapToFQW(protocolDTO.getTheme()))
                .headOfTheFQW(protocolDTO.getHeadOfTheFQW())
                .review(protocolDTO.getReview())
                .volume(protocolDTO.getVolume())
                .grade(protocolDTO.getGrade())
                .build();
    }

    public static ProtocolDTO mapToProtocolDTO(Protocol protocol){
        return ProtocolDTO.builder()
                .student(StudentMapper.mapToStudentDTO(protocol.getStudent()))
                .theme(FQWMapper.mapToFQWDTO(protocol.getFqw()))
                .headOfTheFQW(protocol.getHeadOfTheFQW())
                .review(protocol.getReview())
                .volume(protocol.getVolume())
                .grade(protocol.getGrade())
                .build();
    }
}
