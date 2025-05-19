package ru.emiren.infosystemdepartment.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:13131", "http://hub:8080/sql",
                                "http://localhost:3000", "ws://localhost:13131/api",
                                "https://localhost:13131/api", "https://fqw.rudn.ru",
                                "https://localhost:13131", "https://fqw.rudn.ru:13131")
                .allowedMethods("GET", "POST", "PUT", "OPTIONS")
                .allowCredentials(true);
    }
}
