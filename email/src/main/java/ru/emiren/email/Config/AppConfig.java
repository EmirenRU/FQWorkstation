package ru.emiren.email.Config;

import com.google.gson.Gson;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Configuration
public class AppConfig {

    @Bean
    public DateFormat dateFormat() {
        return new SimpleDateFormat("dd/MM/yyyy-HH:mm:ss");
    }

    @Bean()
    public Gson gson(){
        return new Gson();
    }



}
