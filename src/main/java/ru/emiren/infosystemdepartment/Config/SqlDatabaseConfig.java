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
        entityManagerFactoryRef = "sqlEntityManagerFactory",
        transactionManagerRef = "sqlTransactionManager",
        basePackages = {"ru.emiren.infosystemdepartment.Model.SQL", "ru.emiren.infosystemdepartment.Repository.SQL"}
)
public class SqlDatabaseConfig {
    PostgreSQLDataSourceProperties postgreSQLDataSourceProperties;

    @Autowired
    public SqlDatabaseConfig(PostgreSQLDataSourceProperties postgreSQLDataSourceProperties) {
        this.postgreSQLDataSourceProperties = postgreSQLDataSourceProperties;
    }

    @Bean(name = "sqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource sqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "sqlEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean sqlEntityManagerFactory
            (EntityManagerFactoryBuilder builder,
             @Qualifier("sqlDataSource") DataSource dataSource) {

        HashMap<String, Object> properties = (HashMap<String, Object>) postgreSQLDataSourceProperties.getProperties();

        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("ru.emiren.infosystemdepartment.Model.SQL")
                .persistenceUnit("SQL")
                .build();
    }

    @Bean(name = "sqlTransactionManager")
    public PlatformTransactionManager dataTransactionManager
            (@Qualifier("sqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
