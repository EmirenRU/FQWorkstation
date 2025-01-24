package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.emiren.infosystemdepartment.Model.SQL.ProtocolQuestion;

@Repository
public interface ProtocolQuestionRepository extends JpaRepository<ProtocolQuestion, Long> {
}
