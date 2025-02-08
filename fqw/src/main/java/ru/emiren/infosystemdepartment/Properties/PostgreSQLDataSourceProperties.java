package ru.emiren.infosystemdepartment.Properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource")
@Configuration
public class PostgreSQLDataSourceProperties {

    @Value("${spring.datasource.jdbc-url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    private Map<String, Object> properties = new HashMap<>();

    public PostgreSQLDataSourceProperties() {
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    }

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }
}
