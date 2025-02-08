package ru.emiren.support.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.emiren.support.properties.PostgresSupportDataSourceProperties;

import javax.sql.DataSource;
import java.util.HashMap;

/**
 * Конфигурация для второго источника данных (Support), использующего PostgreSQL,
 * с настройками EntityManagerFactory, DataSource и TransactionManager.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "dataEntityManagerFactory",
        transactionManagerRef = "dataTransactionManager",
        basePackages = { "ru.emiren.support.model", "ru.emiren.support.repository" }
)
public class SupportDatabaseConfig{
    PostgresSupportDataSourceProperties postgresSupportDataSourceProperties;

    /**
     * Конструктор, используемый для внедрения зависимостей.
     * @param postgreSQLDataSourceProperties объект настроек для PostgreSQL.
     */
    @Autowired
    public SupportDatabaseConfig(PostgresSupportDataSourceProperties postgreSQLDataSourceProperties) {
        this.postgresSupportDataSourceProperties = postgreSQLDataSourceProperties;
    }

    /**
     * Определение бина источника данных для второго источника (support-datasource).
     * @return настроенный DataSource.
     */
    @Bean(name = "supportDataSource")
    public DataSource dataDataSource() { return DataSourceBuilder.create()
            .url(postgresSupportDataSourceProperties.getUrl())
            .username(postgresSupportDataSourceProperties.getUsername())
            .password(postgresSupportDataSourceProperties.getPassword())
            .build(); }


    /**
     * Создает LocalContainerEntityManagerFactoryBean для JPA с заданным источником данных и параметрами.
     * @param builder объект для построения EntityManagerFactory.
     * @param dataSource источник данных.
     * @return LocalContainerEntityManagerFactoryBean для JPA.
     */
    @Bean(name = "dataEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dataEntityManagerFactory
            (EntityManagerFactoryBuilder builder,
            @Qualifier("supportDataSource") DataSource dataSource) {
        HashMap<String, Object> properties = (HashMap<String, Object>) postgresSupportDataSourceProperties.getProperties();
        return builder.dataSource(dataSource)
                .properties(properties)
                .packages("ru.emiren.support.model")
                .persistenceUnit("Support")
                .build();
    }

    /**
     * Создает транзакционный менеджер для JPA с указанной фабрикой EntityManager.
     * @param entityManagerFactory фабрика EntityManager.
     * @return транзакционный менеджер для JPA.
     */
    @Bean(name = "dataTransactionManager")
    public PlatformTransactionManager dataTransactionManager
            (@Qualifier("dataEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}