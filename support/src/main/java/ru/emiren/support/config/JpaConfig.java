package ru.emiren.support.config;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;


/**
 * Конфигурационный класс, который создает общий бин EntityManagerFactoryBuilder
 * для настройки JPA с Hibernate в качестве JPA-поставщика.
 */
@Configuration
public class JpaConfig {
    /**
     * Создает бин EntityManagerFactoryBuilder, который используется для создания
     * фабрик EntityManager с Hibernate в качестве JPA-поставщика.
     * @return объект EntityManagerFactoryBuilder, который может быть использован
     * для создания LocalContainerEntityManagerFactoryBean.
     */
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder(){
        return new EntityManagerFactoryBuilder(new HibernateJpaVendorAdapter(), new HashMap<>(), null);
    }
}
