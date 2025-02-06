package ru.emiren.infosystemdepartment.Repository.SQL;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Department;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT s.department.name FROM Student s JOIN Department d ON d.code = s.department.code WHERE s.stud_num = :studNumber")
    Optional<String> findDepartmentNameByStudNumber(Long studNumber);

    Optional<Department> findByName(String name);
}
