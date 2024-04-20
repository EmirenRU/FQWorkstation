package ru.emiren.infosystemdepartment.Config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.emiren.infosystemdepartment.Properties.PostgreSQLDataSourceProperties;

import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dataEntityManagerFactory",
        transactionManagerRef = "dataTransactionManager",
        basePackages = { "ru.emiren.infosystemdepartment.Model.Support", "ru.emiren.infosystemdepartment.Repository.Support" }
)
public class DataDatabaseConfig {
    PostgreSQLDataSourceProperties postgreSQLDataSourceProperties;

    @Autowired
    public DataDatabaseConfig(PostgreSQLDataSourceProperties postgreSQLDataSourceProperties) {
        this.postgreSQLDataSourceProperties = postgreSQLDataSourceProperties;
    }

    @Bean(name = "dataDataSource")
    @ConfigurationProperties(prefix = "spring.second-datasource")
    public DataSource dataDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "dataEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory
            (EntityManagerFactoryBuilder builder,
            @Qualifier("dataDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = (HashMap<String, Object>) postgreSQLDataSourceProperties.getProperties();


        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("ru.emiren.infosystemdepartment.Model.Support")
                .persistenceUnit("Support")
                .build();
    }

    @Bean(name = "dataTransactionManager")
    public PlatformTransactionManager dataTransactionManager
            (@Qualifier("dataEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}