package ru.emiren.auth.Service;

import org.springframework.http.ResponseEntity;
import ru.emiren.auth.DTO.AuthResponse;
import ru.emiren.auth.DTO.UserDTO;
import ru.emiren.auth.Model.User;

import java.util.Map;

public interface UserService {

    public ResponseEntity<?> registerUser(Map<String, String> data);
    public ResponseEntity<?> checkUserToken(String token);
    public ResponseEntity<?> getUser(String token);
    public ResponseEntity<AuthResponse> login(UserDTO userDTO);


    User loadUserByUsername(String username);
}
