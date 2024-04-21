package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.time.LocalDate;
import java.util.List;

public interface StudentLecturerRepository extends JpaRepository<StudentLecturers, Long> {

    @Query("SELECT sl FROM StudentLecturers sl ORDER BY sl.lecturer.fio")
    List<StudentLecturers> findAllSorted();

    @Query("SELECT sl FROM StudentLecturers sl " +
            "JOIN sl.student.orientation.protection p ON p.orientation.code = sl.student.orientation.code " +
            "WHERE " +
            "((sl.lecturer.id = :lecturerId) OR (:lecturerId = -1)) AND " +
            "((sl.student.orientation.code = :orientationCode) OR (:orientationCode = '-1') ) AND " +
            "((sl.student.department.code = :departmentCode) OR (:departmentCode = '-1')) AND " +
            "((sl.student.fqw.name = :theme) OR (:theme = '-1')) " +
            "AND ( (p.dateOfProtection = :date) OR (cast(:date as DATE) IS null)) " +
            "ORDER BY sl.lecturer.fio")
    List<StudentLecturers> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment
            (String orientationCode,
             String departmentCode,
             LocalDate date,
             String theme,
             Long lecturerId);
}
