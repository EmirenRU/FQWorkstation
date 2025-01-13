package ru.emiren.auth.Mapper;

import ru.emiren.auth.DTO.RoleDTO;
import ru.emiren.auth.Model.Role;

public class RoleMapper {
    public static Role mapToRole(RoleDTO role) {
        return Role.builder()
                .id(role.getId())
                .role(role.getRole())
                .build();
    }
    public static RoleDTO mapToRoleDTO(Role role) {
        return RoleDTO.builder()
                .id(role.getId())
                .role(role.getRole())
                .build();
    }
}
