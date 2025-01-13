package ru.emiren.auth.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.emiren.auth.DTO.AuthResponse;
import ru.emiren.auth.DTO.UserDTO;
import ru.emiren.auth.Service.ScriptService;
import ru.emiren.auth.Service.UserService;
import ru.emiren.auth.Utils.TokenProvider;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RestAuthController {
    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final ScriptService scriptService;


    @Autowired
    public RestAuthController(UserService userService, TokenProvider tokenProvider, ScriptService scriptService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.scriptService = scriptService;
    }

    @GetMapping("")
    public ResponseEntity<Resource> transferJsToClient(){
        return scriptService.getScript();
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Map<String, String> data) {
        return userService.registerUser(data);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @GetMapping("/check-token")
    public ResponseEntity<?> checkUserToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.checkUserToken(token);
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUser(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.getUser(token);
    }
}
