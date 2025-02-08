package ru.emiren.infosystemdepartment.DTO.SQL;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProtocolDTO {

    private StudentDTO student;
    private FQWDTO theme; // Final Qualifying Work Name
    private String headOfTheFQW; // head of the Final qualification work
    private String review; // Рецензия
    private Integer volume; // объем
    private Integer grade; // оценка

}
