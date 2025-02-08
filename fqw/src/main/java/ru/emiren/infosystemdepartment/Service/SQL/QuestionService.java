package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.Model.SQL.Question;

public interface QuestionService {
    void saveQuestion(Question question);

    Question findQuestion(String s);
}
