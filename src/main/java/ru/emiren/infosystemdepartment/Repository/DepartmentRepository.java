package ru.emiren.infosystemdepartment.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import ru.emiren.infosystemdepartment.Model.SQL.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {
}
