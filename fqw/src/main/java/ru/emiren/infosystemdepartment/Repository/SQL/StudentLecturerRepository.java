package ru.emiren.infosystemdepartment.Repository.SQL;

import jakarta.persistence.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.time.LocalDate;
import java.util.List;

public interface StudentLecturerRepository extends JpaRepository<StudentLecturers, Long> {

    @Query("SELECT sl FROM StudentLecturers sl ORDER BY sl.lecturer.name")
    List<StudentLecturers> findAllSorted();

    // TODO make a javadoc for every methods and README.md for structure of project
    // TODO make a annual year backup for data_${year}.sql
    // TODO make student [1 or(N)]<->1 RelevantNotRelevant
    // TODO make an api for minimal saving from HttpRequest (REST)
    // TODO to write a letter about dhcp server and about static ip-address
    @Query("SELECT sl FROM StudentLecturers sl " +
            "JOIN sl.student.orientation.protection p ON p.orientation.code = sl.student.orientation.code " +
            "WHERE " +
            " ((sl.lecturer.id = :lecturerId                     ) OR (:lecturerId = -1                 ) ) AND " +
            " ((sl.student.orientation.code IN :orientationCodes ) OR ('-1' IN :orientationCodes        ) ) AND " +
            " ((sl.student.department.code = :departmentCode     ) OR (:departmentCode = -1             ) ) AND " +
            " ((sl.student.fqw.name = :theme                     ) OR (:theme = '-1'                    ) ) " +
            "AND ((CAST(:dateFrom as integer) IS null                             ) OR (CAST(p.dateOfProtection AS integer) >= CAST(:dateFrom as integer))        ) " + // The problem part
            "AND ((CAST(:dateTo as integer) IS null                               ) OR (CAST(p.dateOfProtection AS integer) <= CAST(:dateTo as integer))          ) " + // The problem part
            "ORDER BY sl.lecturer.name")
    List<StudentLecturers> findAllAndSortedByLecturerAndThemeAndDateAndOrientationAndDepartment(
            @Param("orientationCodes") List<String> orientationCodes,
            @Param("departmentCode") Long departmentCode,
            @Param("dateFrom") java.time.Year dateFrom,
            @Param("dateTo") java.time.Year dateTo,
            @Param("theme") String theme,
            @Param("lecturerId") Long lecturerId);

    @Query("SELECT sl from StudentLecturers sl " +
            "JOIN year_student yr ON yr.student.stud_num = sl.student.stud_num " +
            "WHERE yr.year.year = :date AND :date IS NOT NULL " +
            "ORDER BY sl.lecturer.name")
    List<StudentLecturers> findAllSortedByDate(String date);
}
