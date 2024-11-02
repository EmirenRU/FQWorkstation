package ru.emiren.infosystemdepartment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableScheduling
public class InfoSystemDepartmentApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoSystemDepartmentApplication.class, args);
	}

}
