package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.Lecturer;

public interface LecturerRepository extends JpaRepository<Lecturer, Long> {

}
