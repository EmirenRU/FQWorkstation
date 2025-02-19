package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.emiren.infosystemdepartment.Model.SQL.ProtocolQuestion;

import java.util.Optional;

@Repository
public interface ProtocolQuestionRepository extends JpaRepository<ProtocolQuestion, Long> {

    @Query("SELECT pq FROM protocol_question pq WHERE pq.protocol.student.stud_num = :studNum AND pq.question.question = :question")
    Optional<ProtocolQuestion> findByQuestionAndStudNum(String question, Long studNum);

    @Query("SELECT MAX(pq.id) FROM protocol_question pq")
    Long getMaxId();
}
