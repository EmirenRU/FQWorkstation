package ru.emiren.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.emiren.support.model.SupportData;

@Repository
public interface SupportDataRepository extends JpaRepository<SupportData, Long> {
}
