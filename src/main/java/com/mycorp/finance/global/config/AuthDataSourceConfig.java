package com.mycorp.finance.global.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Configuration for auth-related data source, entity manager, and transaction manager.
 *
 * This module manages a dedicated PostgreSQL database for authentication (AuthUser) data,
 * and operates independently from the main customer/command data source.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.mycorp.finance.global.security.infrastructure.persistence.repository",
        entityManagerFactoryRef = "authEntityManagerFactory",
        transactionManagerRef = "authTransactionManager"
)
@EntityScan("com.mycorp.finance.global.security.infrastructure.persistence.entity")
public class AuthDataSourceConfig {

    /**
     * Binds auth DB connection properties from application.yml or application.properties
     * under the prefix `spring.datasource.auth`.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.auth")
    public DataSourceProperties authDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Builds the physical DataSource for the authentication database
     * using the configured DataSourceProperties.
     */
    @Bean
    public DataSource authDataSource() {
        return authDataSourceProperties()
                .initializeDataSourceBuilder()
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Creates the EntityManagerFactory for the auth domain.
     * Scans only auth-specific JPA entities and configures Hibernate settings.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean authEntityManagerFactory(
            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("authDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages("com.mycorp.finance.global.security.infrastructure.persistence.entity")
                .persistenceUnit("auth")
                .properties(authJpaProperties())
                .build();
    }

    /**
     * Defines JPA/Hibernate-specific properties for the auth persistence unit.
     * Adjust `hbm2ddl.auto` carefully in production environments.
     */
    private Map<String, Object> authJpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update");
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.format_sql", true);
        return props;
    }

    /**
     * Configures a dedicated transaction manager for the auth DB.
     * Enables @Transactional with `transactionManager = "authTransactionManager"`.
     */
    @Bean
    public PlatformTransactionManager authTransactionManager(
            @Qualifier("authEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
