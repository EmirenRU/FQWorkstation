package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.Model.SQL.ProtocolQuestion;
import ru.emiren.infosystemdepartment.DTO.SQL.ProtocolQuestionDTO;

public class ProtocolQuestionMapper {
    public static ProtocolQuestion mapProtocolQuestion(ProtocolQuestionDTO question) {
        return ProtocolQuestion.builder()
                .id(question.getId())
                .protocol(ProtocolMapper.mapToProtocol(question.getProtocol()))
                .question(QuestionMapper.mapToQuestion(question.getQuestion()))
                .build();
    }

    public static ProtocolQuestionDTO mapProtocolQuestionDTO(ProtocolQuestion question) {
        return ProtocolQuestionDTO.builder()
                .id(question.getId())
                .protocol(ProtocolMapper.mapToProtocolDTO(question.getProtocol()))
                .question(QuestionMapper.mapToQuestionDTO(question.getQuestion()))
                .build();
    }
}
