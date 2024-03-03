/*
    There will be some modification for several database connection
    if uncomment it will crush the system
 */

/*package ru.emiren.infosystemdepartment;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Configuration
@Component
public class InfoSystemDepartmentConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties(prefix="spring.datasource")
    public DataSource primaryDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean
    @ConfigurationProperties(prefix="spring.second-datasource")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }

}*/
