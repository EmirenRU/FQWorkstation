package ru.emiren.infosystemdepartment.Mapper;

import lombok.extern.slf4j.Slf4j;
import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

import java.util.List;

@Slf4j
public class StudentLecturersMapper {
    public static StudentLecturers mapToStudentLecturers(StudentLecturersDTO studentLecturersDTO){
        return StudentLecturers.builder()
                .id(studentLecturersDTO.getId())
                .student(StudentMapper.mapToStudent(studentLecturersDTO.getStudent()))
                .lecturer(LecturerMapper.mapToLecturer(studentLecturersDTO.getLecturer()))
                .isConsultant(studentLecturersDTO.getIsConsultant())
                .isScientificSupervisor(studentLecturersDTO.getIsScientificSupervisor())
                .build();
    }

    public static StudentLecturersDTO mapToStudentLecturersDTO(StudentLecturers studentLecturers){
        log.info("StudentLecturersDTO: {}", studentLecturers);
        if (studentLecturers == null) return null;
        return StudentLecturersDTO.builder()
                .id(studentLecturers.getId())
                .student(StudentMapper.mapToStudentDTO(studentLecturers.getStudent()))
                .lecturer(LecturerMapper.mapToLecturerDTO(studentLecturers.getLecturer()))
                .isScientificSupervisor(studentLecturers.getIsScientificSupervisor())
                .isConsultant(studentLecturers.getIsConsultant())
                .build();
    }

    public static SqlPayload mapToSqlPayload(StudentLecturers sl) {
        SqlPayload.SqlPayloadBuilder builder = SqlPayload.builder()
                .id(sl.getId())
                .academicDegree(sl.getLecturer().getAcademicDegree())

                .fullLecturerName(sl.getLecturer().getName())
                .fullStudentName(sl.getStudent().getName())
                .position(sl.getLecturer().getPosition())
                .studNum(sl.getStudent().getStud_num())
                .citizenship(sl.getStudent().getCitizenship());


        if (sl.getStudent().getFqw() != null && sl.getStudent().getFqw().getDecree() != null) {
            builder.theme(sl.getStudent().getFqw().getDecree().getTheme());
        }

        if (sl.getLecturer().getDepartment() != null) {
            builder.department(sl.getLecturer().getDepartment().getName());
        }

        if (sl.getStudent().getFqw() != null && sl.getStudent().getFqw().getDecree() != null) {
            builder.numberOfDecree(sl.getStudent().getFqw().getDecree().getNumberOfDecree());
        }

        return builder.build();
    }

}
