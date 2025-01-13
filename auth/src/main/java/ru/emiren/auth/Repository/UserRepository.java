package ru.emiren.auth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.emiren.auth.Model.User;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByRefreshToken(String token);

    List<User> findByRefreshTokenExpiryBefore(Date now);

    User findByLogin(String login);
}
