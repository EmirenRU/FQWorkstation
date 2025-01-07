package ru.emiren.infosystemdepartment.Properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "spring.support-datasource")
public class PostgresSupportDataSourceProperties {

    @Value("${spring.support-datasource.jdbc-url}")
    private String url;
    @Value("${spring.support-datasource.username}")
    private String username;
    @Value("${spring.support-datasource.password}")
    private String password;

    private Map<String, Object> properties = new HashMap<>();

    public PostgresSupportDataSourceProperties() {
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    }

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }
}
