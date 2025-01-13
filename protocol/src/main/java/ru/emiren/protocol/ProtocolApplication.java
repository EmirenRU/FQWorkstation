package ru.emiren.protocol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class ProtocolApplication {
    public static void main( String[] args ) {
        SpringApplication.run(ProtocolApplication.class, args);
    }
}
