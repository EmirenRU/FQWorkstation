package ru.emiren.protocol.DTO;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TableData {
    private   Long id;
    private String fullLecturerName;
    private String academicDegree;
    private String position;
    private String department;
    private String fullStudentName;
    private Long studNum;
    private String citizenship;
    private String theme;
    private String numberOfDecree;
}
