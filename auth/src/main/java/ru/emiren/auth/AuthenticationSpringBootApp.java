package ru.emiren.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.emiren.auth.Model.Role;
import ru.emiren.auth.Repository.RoleRepository;

import java.util.List;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class AuthenticationSpringBootApp {
    public static void main( String[] args ) {
        SpringApplication.run(AuthenticationSpringBootApp.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                List<Role> roles = List.of(
                        new Role(1l, "ADMIN", List.of()),
                        new Role(2l, "USER", List.of()),
                        new Role(3l, "SPECTATOR", List.of())
                );
                roleRepository.saveAll(roles);
                log.info("Roles initialized: ADMIN, USER, SPECTATOR");
            }
        };
    }
}
