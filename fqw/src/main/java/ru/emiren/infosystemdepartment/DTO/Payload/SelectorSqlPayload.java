package ru.emiren.infosystemdepartment.DTO.Payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.emiren.infosystemdepartment.DTO.Payload.Selector.DepartmentSelector;
import ru.emiren.infosystemdepartment.DTO.Payload.Selector.OrientationSelector;
import ru.emiren.infosystemdepartment.DTO.Payload.Selector.LecturerSelector;
import ru.emiren.infosystemdepartment.DTO.Payload.Selector.ThemeSelector;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SelectorSqlPayload {
    List<DepartmentSelector> department;
    List<OrientationSelector> orientation;
    List<LecturerSelector> student;
    List<ThemeSelector> theme;
}
