package ru.emiren.infosystemdepartment.Properties;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PostgreSQLDataSourceProperties {

    private Map<String, Object> properties = new HashMap<>();

    public PostgreSQLDataSourceProperties() {
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }
}
