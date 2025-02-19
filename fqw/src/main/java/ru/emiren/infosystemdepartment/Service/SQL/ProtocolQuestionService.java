package ru.emiren.infosystemdepartment.Service.SQL;

import ru.emiren.infosystemdepartment.Model.SQL.ProtocolQuestion;

public interface ProtocolQuestionService {
    void saveProtocolQuestion(ProtocolQuestion pq);

    ProtocolQuestion findByQuestionAndProtocolStudent(String question, Long studNum);

    Long getMaxId();
}
