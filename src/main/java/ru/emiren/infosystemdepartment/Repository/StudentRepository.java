package ru.emiren.infosystemdepartment.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.emiren.infosystemdepartment.DTO.LecturerDTO;
import ru.emiren.infosystemdepartment.DTO.StudentDTO;
import ru.emiren.infosystemdepartment.Model.SQL.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query("SELECT st FROM Student st JOIN StudentLecturers sl ON st.id = sl.student.id WHERE sl.lecturer.id = :lecturerId" )
    List<Student> findStudentsByLecturerId(@Param("lecturerId") Long lecturerId);
}
