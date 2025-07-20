package com.mycorp.finance.global.config;

import jakarta.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for command (write) data source.
 *
 * This class manages the DataSource, EntityManagerFactory, and TransactionManager
 * for the write-side (command) PostgreSQL database. It handles domain entities like
 * customer and account for command operations, and allows separate persistence context.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "com.mycorp.finance.customer.infrastructure.persistence.repository.command",
                "com.mycorp.finance.banking.account.infrastructure.persistence.repository.command"
        },
        entityManagerFactoryRef = "commandEntityManagerFactory",
        transactionManagerRef = "commandTransactionManager"
)
@EntityScan({
        "com.mycorp.finance.customer.infrastructure.persistence.entity.command",
        "com.mycorp.finance.banking.account.infrastructure.persistence.entity.command"
})
public class CommandDataSourceConfig {

    /**
     * Binds command DB properties from application.yml (prefix: spring.datasource.command).
     */
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.command")
    public DataSourceProperties commandDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Builds the command DB physical DataSource.
     */
    @Bean
    @Primary
    public DataSource commandDataSource() {
        return commandDataSourceProperties()
                .initializeDataSourceBuilder()
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Configures the EntityManagerFactory for command DB.
     * Includes only command-side domain packages.
     */
    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean commandEntityManagerFactory(
            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("commandDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(
                        "com.mycorp.finance.customer.infrastructure.persistence.entity.command",
                        "com.mycorp.finance.banking.account.infrastructure.persistence.entity.command"
                )
                .persistenceUnit("command")
                .properties(commandJpaProperties())
                .build();
    }

    /**
     * Hibernate properties for command DB persistence context.
     */
    private Map<String, Object> commandJpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update"); // use `none` or `validate` in prod
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.format_sql", true);
        return props;
    }

    /**
     * Transaction manager for command DB operations.
     */
    @Bean(name = "commandTransactionManager")
    @Primary
    public PlatformTransactionManager commandTransactionManager(
            @Qualifier("commandEntityManagerFactory") EntityManagerFactory commandEntityManagerFactory) {
        return new JpaTransactionManager(commandEntityManagerFactory);
    }
}
