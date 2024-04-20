package ru.emiren.infosystemdepartment.DTO.Support;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DataDTO {
    public Long id;
    public String fullName;
    public String email;
    public String phone;
    public String description;
}
