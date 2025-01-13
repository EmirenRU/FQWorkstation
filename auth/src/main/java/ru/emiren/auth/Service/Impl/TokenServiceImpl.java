package ru.emiren.auth.Service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.emiren.auth.Model.User;
import ru.emiren.auth.Repository.UserRepository;
import ru.emiren.auth.Service.TokenService;
import ru.emiren.auth.Utils.TokenProvider;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class TokenServiceImpl implements TokenService {

    private final TokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Autowired
    public TokenServiceImpl(TokenProvider tokenProvider, UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.userRepository = userRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void cleanupExpiredTokens(){
        Date now = new Date();

        List<User> users = userRepository.findByRefreshTokenExpiryBefore(now);
        users = users.stream().peek(user -> {user.setRefreshToken(null); user.setRefreshTokenExpiry(null);
        }).toList();
        userRepository.saveAll(users);
        log.info("Cleaning up expired tokens");
    }
}
