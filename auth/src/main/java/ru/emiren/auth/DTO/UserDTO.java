package ru.emiren.auth.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
public class UserDTO {
    private Long id;

    private String login;
    private String password;
    private String refreshToken;
    private Date refreshTokenExpiry;

    private List<RoleDTO> roles = new ArrayList<>();
}
