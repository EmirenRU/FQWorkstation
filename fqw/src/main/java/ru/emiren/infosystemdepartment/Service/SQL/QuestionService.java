package ru.emiren.infosystemdepartment.Service.SQL;

import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Model.SQL.Question;

public interface QuestionService {
    @Transactional
    void saveQuestion(Question question);

    Question findQuestion(String s);

    Long getMaxId();
}
