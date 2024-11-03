package ru.emiren.infosystemdepartment.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public DateFormat dateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
    }

}
