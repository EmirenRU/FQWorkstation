package ru.emiren.auth.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RoleDTO {
    private Long id;

    private String role;

}
