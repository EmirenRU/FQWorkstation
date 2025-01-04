package ru.emiren.infosystemdepartment.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfFilter;
import ru.emiren.infosystemdepartment.Util.MaxRequestFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final String JS_ACCESS = "JS_ACCESS";
    private static final String ADMIN_ACCESS = "ADMIN";
    private static final String USER_ACCESS = "USER";


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.DELETE).hasRole(ADMIN_ACCESS)
//                        .requestMatchers("/admin/**").hasRole(ADMIN_ACCESS)
                        .requestMatchers("/api/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole(ADMIN_ACCESS)
                        .requestMatchers("/login/**").hasAnyAuthority(JS_ACCESS)
                        .requestMatchers("/css/**", "/js/**", "/img/**", "/thirdParty/", "favicon.ico",
                                "/resources/css/**", "/resources/js/**", "/resources/img/**", "/resources/thirdParty/**").permitAll()
                        .anyRequest().permitAll())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new MaxRequestFilter(), CsrfFilter.class)
        ;
        return http.build();
    }

}