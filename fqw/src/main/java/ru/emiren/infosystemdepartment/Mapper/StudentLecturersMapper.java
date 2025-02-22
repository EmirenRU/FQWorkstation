package ru.emiren.infosystemdepartment.Mapper;

import ru.emiren.infosystemdepartment.DTO.Payload.SqlPayload;
import ru.emiren.infosystemdepartment.DTO.SQL.StudentLecturersDTO;
import ru.emiren.infosystemdepartment.Model.SQL.StudentLecturers;

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
        return SqlPayload.builder()
                .id(sl.getId())
                .academicDegree(sl.getLecturer().getAcademicDegree())
                .theme(sl.getStudent().getFqw().getDecree().getTheme())
                .fullLecturerName(sl.getLecturer().getName())
                .fullStudentName(sl.getStudent().getName())
                .position(sl.getLecturer().getPosition())
                .studNum(sl.getStudent().getStud_num())
                .citizenship(sl.getStudent().getCitizenship())
                .department(sl.getLecturer().getDepartment().getName())
                .build();
    }

}
