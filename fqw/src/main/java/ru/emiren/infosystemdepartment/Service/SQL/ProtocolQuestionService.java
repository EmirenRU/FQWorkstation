package ru.emiren.infosystemdepartment.Service.SQL;

import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Model.SQL.ProtocolQuestion;

public interface ProtocolQuestionService {
    @Transactional
    void saveProtocolQuestion(ProtocolQuestion pq);

    ProtocolQuestion findByQuestionAndProtocolStudent(String question, Long studNum, String questioner);

    Long getMaxId();
}
