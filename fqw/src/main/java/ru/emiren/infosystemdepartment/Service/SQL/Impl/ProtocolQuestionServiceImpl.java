package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.stereotype.Service;
import ru.emiren.infosystemdepartment.Model.SQL.ProtocolQuestion;
import ru.emiren.infosystemdepartment.Repository.SQL.ProtocolQuestionRepository;
import ru.emiren.infosystemdepartment.Service.SQL.ProtocolQuestionService;

@Service
public class ProtocolQuestionServiceImpl implements ProtocolQuestionService {
    private final ProtocolQuestionRepository protocolQuestionRepository;

    ProtocolQuestionServiceImpl(ProtocolQuestionRepository pqRepo){
        this.protocolQuestionRepository = pqRepo;
    }


    @Override
    public void saveProtocolQuestion(ProtocolQuestion pq) {
        protocolQuestionRepository.save(pq);
    }

    @Override
    public ProtocolQuestion findByQuestionAndProtocolStudent(String question, Long studNum) {
        return protocolQuestionRepository.findByQuestionAndStudNum(question, studNum).orElse(null);
    }
}
