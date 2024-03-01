package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> { }
