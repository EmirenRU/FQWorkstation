package ru.emiren.infosystemdepartment.Repository.SQL;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.emiren.infosystemdepartment.Model.SQL.Department;

public interface DepartmentRepository extends JpaRepository<Department, String> {

    @Query("SELECT d.name FROM Department d JOIN Student st ON st.stud_num = :studNumber")
    String findDepartmentNameByStudNumber(Long studNumber);
}
