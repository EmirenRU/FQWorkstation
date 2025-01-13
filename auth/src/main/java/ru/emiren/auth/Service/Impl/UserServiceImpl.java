package ru.emiren.auth.Service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.emiren.auth.DTO.AuthResponse;
import ru.emiren.auth.DTO.UserDTO;
import ru.emiren.auth.Mapper.UserMapper;
import ru.emiren.auth.Model.Role;
import ru.emiren.auth.Model.User;
import ru.emiren.auth.Repository.RoleRepository;
import ru.emiren.auth.Repository.UserRepository;
import ru.emiren.auth.Service.UserService;
import ru.emiren.auth.Utils.TokenProvider;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ResponseEntity<?> registerUser(Map<String, String> data) {
        String login = data.get("login");

        if (userRepository.findByLogin(login) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        String password = encoder.encode(data.get("password"));
        String role = "USER";


        Role role1 = roleRepository.findByRole(role);

        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setRoles(List.of(Objects.requireNonNull(role1)));

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public boolean hasRole(User user, String roleName) {
        return user.getRoles().stream()
                .anyMatch(role -> role.getRole().equals(roleName));
    }

    public UserDTO findByToken(String token) {
        return UserMapper.mapToUserDTO(userRepository.findByRefreshToken(token));
    }


    @Override
    public ResponseEntity<?> checkUserToken(String token) {
        UserDTO user = findByToken(token);
        if (user == null) { return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token"); }
        return ResponseEntity.status(HttpStatus.OK).body(user.getRefreshToken());
    }


    @Override
    public ResponseEntity<?> getUser(String token) {
        return null;
    }

    @Override
    public ResponseEntity<AuthResponse> login(UserDTO userDTO) {
        String accessToken = tokenProvider.generateAccessToken(userDTO.getLogin());
        String refreshToken = tokenProvider.generateRefreshToken(userDTO.getLogin());

        User user = userRepository.findByLogin(userDTO.getLogin());

        user.setRefreshToken(refreshToken);
        user.setRefreshTokenExpiry(new Date(System.currentTimeMillis() + tokenProvider.getRefreshTokenValidity()));

        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(accessToken, refreshToken));
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

}
