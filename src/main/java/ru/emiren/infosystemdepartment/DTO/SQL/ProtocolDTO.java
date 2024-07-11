package ru.emiren.infosystemdepartment.DTO.SQL;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ProtocolDTO implements Serializable {

    private String fioStudent;
    private String fqwName; // Final Qualifying Work Name
    private String headOfTheFQW; // head of the Final qualification work
    private String review; // Рецензия
    private Integer volume; // объем
    private Integer grade; // оценка

    //    private List<String> questions;
}
