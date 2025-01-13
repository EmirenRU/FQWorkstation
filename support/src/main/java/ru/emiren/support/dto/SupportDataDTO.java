package ru.emiren.support.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SupportDataDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phone;
    private String description;
}
