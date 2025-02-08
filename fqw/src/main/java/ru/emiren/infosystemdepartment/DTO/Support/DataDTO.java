package ru.emiren.infosystemdepartment.DTO.Support;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DataDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String description;
}
