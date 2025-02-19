package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Model.SQL.Question;
import ru.emiren.infosystemdepartment.Repository.SQL.QuestionRepository;
import ru.emiren.infosystemdepartment.Service.SQL.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    QuestionServiceImpl(QuestionRepository qr){
        this.questionRepository = qr;
    }

    @Override
    @Transactional
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }

    @Override
    public Question findQuestion(String s) {
        return questionRepository.findByQuestion(s).orElse(null);
    }

    @Override
    public Long getMaxId() {
        return questionRepository.getMaxId();
    }
}
