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

/**
 * Конфигурация для основного источника данных (SQL), использующего PostgreSQL,
 * с настройками EntityManagerFactory, DataSource и TransactionManager.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "sqlEntityManagerFactory",
        transactionManagerRef = "sqlTransactionManager",
        basePackages = {"ru.emiren.infosystemdepartment.Model.SQL", "ru.emiren.infosystemdepartment.Repository.SQL"}
)
public class SqlDatabaseConfig {
    PostgreSQLDataSourceProperties postgreSQLDataSourceProperties;

    /**
     * Конструктор, используемый для внедрения зависимостей.
     * @param postgreSQLDataSourceProperties объект настроек для PostgreSQL.
     */
    @Autowired
    public SqlDatabaseConfig(PostgreSQLDataSourceProperties postgreSQLDataSourceProperties) {
        this.postgreSQLDataSourceProperties = postgreSQLDataSourceProperties;
    }

    /**
     * Определение бина источника данных для основного источника (sql-datasource).
     * @return настроенный DataSource.
     */
    @Bean(name = "sqlDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource sqlDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * Создает LocalContainerEntityManagerFactoryBean для JPA с заданным источником данных и параметрами.
     * @param builder объект для построения EntityManagerFactory.
     * @param dataSource источник данных.
     * @return LocalContainerEntityManagerFactoryBean для JPA.
     */
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

    /**
     * Создает транзакционный менеджер для JPA с указанной фабрикой EntityManager.
     * @param entityManagerFactory фабрика EntityManager.
     * @return транзакционный менеджер для JPA.
     */
    @Bean(name = "sqlTransactionManager")
    public PlatformTransactionManager dataTransactionManager
            (@Qualifier("sqlEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
