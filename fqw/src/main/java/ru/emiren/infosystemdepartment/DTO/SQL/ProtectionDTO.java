package ru.emiren.infosystemdepartment.DTO.SQL;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;
import ru.emiren.infosystemdepartment.Model.SQL.ProtectionCommissioner;

import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class ProtectionDTO {
    private Long id;

    private Orientation orientation;

//    @JsonFormat(pattern = "dd-MM-yyyy") // пока под вопросом (над сделать запрос на проверку ошибки)
    @JsonSerialize(using = YearSerializer.class)
    private java.time.Year dateOfProtection;

    private List<ProtectionCommissionerDTO> commissioners = new ArrayList<>();

}
