package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT st FROM Student st JOIN StudentLecturers sl ON st.id = sl.student.id WHERE sl.lecturer.id = :lecturerId" )
    List<Student> findStudentsByLecturerId(@Param("lecturerId") Long lecturerId);

    @Query("SELECT st FROM Student st JOIN StudentLecturers  sl ON st.id = sl.student.id JOIN Orientation or ON st.orientation.code " +
            "= :orientationCode JOIN Department dep ON st.department.code = :departmentCode WHERE sl.lecturer.id = :lecturerId ")
    List<Student> findAllStudentByLecturerIdAndOrientationCodeAndDepartmentCode(Long lecturerId, String orientationCode, String departmentCode);


    Student findByName(String name);

    @Query("SELECT s FROM Student s WHERE s.stud_num = :studNum")
    Optional<Student> findByStudNum(Long studNum);

    @Query("SELECT MAX(s.id) FROM Student s")
    Long getMaxId();

}

