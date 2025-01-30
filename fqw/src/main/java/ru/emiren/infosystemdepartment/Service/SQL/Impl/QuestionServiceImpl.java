package ru.emiren.infosystemdepartment.Service.SQL.Impl;

import org.springframework.stereotype.Service;
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
    public void saveQuestion(Question question) {
        questionRepository.save(question);
    }
}
