package ru.emiren.auth.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.stereotype.Component;
import ru.emiren.auth.Model.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
@Getter
public class TokenProvider {
//    @Value("${application.secret.keyword}")
    private final String SECRET_KEY = "dUYsWFctblIofWJtJS9bYi5ndDosJiNaZj9HcS9TVjxrdkYneFI6PTQ9cTtcaUNBZS4qPzZRUGN9YF19dVZcP3hWPUJNdCx+IThGXj40fkx0TWk+NzpfTy43NS9uWTRXcXRkIjRkI3pJLydyVC91Yno0L2c3Uz1cLW8tLXVhMWRtME15SyRZcFspUjloUSVzWnhkL2NjZGZ1KjI6OTxUejpdWEVfX35NXFYqZ2puNS8lIT9AfTRwXk4oNyZuOlA=";

    private final SecretKey SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    private final long accessTokenValidity = 60L * 60 * 1000; // 1 hour;
    private final long refreshTokenValidity = 60L * 60 * 60 * 24 * 1000;

//    @PostConstruct
//    public void init() {
//        SIGNING_KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
//    }

    public String generateAccessToken(String username) {

        return io.jsonwebtoken.Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessTokenValidity))
                    .signWith(SIGNING_KEY)
                .compact();
    }

    public String generateRefreshToken(String username) {
        return io.jsonwebtoken.Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshTokenValidity))
                .signWith(SIGNING_KEY)
                .compact();
    }

    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(SIGNING_KEY).build().parseSignedClaims(token).getPayload();
    }

    public boolean validateToken(String token, User userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getLogin()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}
