package ru.emiren.infosystemdepartment.Repository.SQL;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.util.List;

public interface StudentLecturerRepository extends JpaRepository<StudentLecturers, Long> {

    @Query("SELECT sl FROM StudentLecturers sl ORDER BY sl.lecturer.name")
    List<StudentLecturers> findAllSorted();

    // TODO make a javadoc for every methods and README.md for structure of project
    // TODO make a annual year backup for data_${year}.sql
    // TODO to write a letter about dhcp server and about static ip-address
    @Query( "SELECT sl FROM StudentLecturers sl " +
            "JOIN sl.student.orientation.protection p ON p.orientation.code = sl.student.orientation.code " +
            "WHERE " +
            " ( (sl.lecturer.id IN :lecturerId) OR (-1 IN :lecturerId ) ) AND " +
            " ( (sl.student.orientation.code IN :orientationCodes ) OR ('-1' IN :orientationCodes) ) AND " +
            " ( (sl.student.department.code IN :departmentCode ) OR (-1 IN :departmentCode ) ) AND " +
            " ( (sl.student.fqw.name ILIKE :theme) OR ( '-1' LIKE :theme ) ) AND " + // problem part is unnest, maybe we need to convert list to string and do something, or maybe one string only
            " ( (CAST(:dateFrom as integer) IS null) OR (CAST(p.dateOfProtection AS integer) >= CAST(:dateFrom as integer)) ) AND " +
            " ( (CAST(:dateTo as integer) IS null)   OR (CAST(p.dateOfProtection AS integer) <= CAST(:dateTo as integer)) ) " +
            " ORDER BY sl.lecturer.name")
    List<StudentLecturers> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
            @Param("orientationCodes") List<String> orientationCodes,
            @Param("departmentCode") List<Long> departmentCode,
            @Param("dateFrom") java.time.Year dateFrom,
            @Param("dateTo") java.time.Year dateTo,
            @Param("theme") String theme,
            @Param("lecturerId") List<Long> lecturerId);

    @Query("SELECT sl from StudentLecturers sl " +
            "JOIN year_student yr ON yr.student.stud_num = sl.student.stud_num " +
            "WHERE yr.year.yearDate = CAST(:date AS integer) AND :date IS NOT NULL " +
            "ORDER BY sl.lecturer.name")
    List<StudentLecturers> findAllSortedByDate(String date);
}
