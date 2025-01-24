package ru.emiren.infosystemdepartment.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://hub:8080/sql",
                                "http://localhost:3000", "ws://localhost:8080/api",
                                "https://localhost:8080/api")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS");
    }
}
