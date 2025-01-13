package ru.emiren.support.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.support.model.SupportData;

public interface SupportDataRepository extends JpaRepository<SupportData, Long> {
}
