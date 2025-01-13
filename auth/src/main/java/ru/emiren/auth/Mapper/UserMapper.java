package ru.emiren.auth.Mapper;

import ru.emiren.auth.DTO.UserDTO;
import ru.emiren.auth.Model.User;

public class UserMapper {

    public static User mapToUser(UserDTO userDTO){
        return User.builder()
                .id(userDTO.getId())
                .login(userDTO.getLogin())
                .password(userDTO.getPassword())
                .refreshToken(userDTO.getRefreshToken())
                .roles(userDTO.getRoles().stream().map(RoleMapper::mapToRole).toList())
                .refreshTokenExpiry(userDTO.getRefreshTokenExpiry())
                .build();
    }

    public static UserDTO mapToUserDTO(User user){
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(user.getPassword())
                .refreshToken(user.getRefreshToken())
                .refreshTokenExpiry(user.getRefreshTokenExpiry())
                .roles(user.getRoles().stream().map(RoleMapper::mapToRoleDTO).toList())
                .build();
    }
}
