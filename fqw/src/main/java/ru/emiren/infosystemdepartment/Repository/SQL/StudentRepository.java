package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT st FROM Student st JOIN StudentLecturers sl ON st.id = sl.student.id WHERE sl.lecturer.id = :lecturerId" )
    List<Student> findStudentsByLecturerId(@Param("lecturerId") Long lecturerId);

    @Query("SELECT st FROM Student st JOIN StudentLecturers  sl ON st.id = sl.student.id JOIN Orientation or ON st.orientation.code " +
            "= :orientationCode JOIN Department dep ON st.department.code = :departmentCode WHERE sl.lecturer.id = :lecturerId ")
    List<Student> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long lecturerId, String orientationCode, String departmentCode);


    Student findByName(String name);
}

