package ru.emiren.infosystemdepartment.DTO.Payload.Selector;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentSelector {
    private String value;
    private String name;
}
