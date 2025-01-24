package ru.emiren.infosystemdepartment.DTO.Payload;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SqlPayload {
    private String fullLecturerName;
    private String academicDegree;
    private String position;
    private String department;
    private String fullStudentName;
    private Long studNum;
    private String citizenship;
    private String theme;
}
