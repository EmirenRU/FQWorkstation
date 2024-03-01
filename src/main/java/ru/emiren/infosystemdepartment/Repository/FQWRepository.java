package ru.emiren.infosystemdepartment.Repository;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.FQW;
import ru.emiren.infosystemdepartment.Model.Reviewer;

public interface FQWRepository extends JpaRepository<FQW, String> {
}
