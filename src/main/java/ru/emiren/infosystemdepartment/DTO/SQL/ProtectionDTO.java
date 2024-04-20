package ru.emiren.infosystemdepartment.DTO.SQL;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Builder;
import lombok.Data;
import ru.emiren.infosystemdepartment.Model.SQL.Orientation;

import java.time.LocalDate;

@Data
@Builder
public class ProtectionDTO {
    private Long id;

    private Orientation orientation;

    @JsonFormat(pattern = "dd-MM-yyyy") // пока под вопросом (над сделать запрос на проверку ошибки)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate dataOfProtection;
}
