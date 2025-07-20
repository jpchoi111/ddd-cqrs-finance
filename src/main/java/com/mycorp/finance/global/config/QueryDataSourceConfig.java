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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration for query-side (read) data source and JPA setup.
 *
 * This module manages access to the read model database (query DB),
 * and isolates repositories/entities for projection views.
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = {
                "com.mycorp.finance.customer.infrastructure.persistence.repository.query",
                "com.mycorp.finance.banking.account.infrastructure.persistence.repository.query"
        },
        entityManagerFactoryRef = "queryEntityManagerFactory",
        transactionManagerRef = "queryTransactionManager"
)
@EntityScan({
        "com.mycorp.finance.customer.infrastructure.persistence.entity.query",
        "com.mycorp.finance.banking.account.infrastructure.persistence.entity.query"
})
public class QueryDataSourceConfig {

    /**
     * Binds query DB connection properties from application.yml.
     */
    @Bean
    @ConfigurationProperties("spring.datasource.query")
    public DataSourceProperties queryDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * Builds the query DB DataSource.
     */
    @Bean
    public DataSource queryDataSource() {
        return queryDataSourceProperties()
                .initializeDataSourceBuilder()
                .driverClassName("org.postgresql.Driver")
                .build();
    }

    /**
     * Configures the EntityManagerFactory for query (read model) context.
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean queryEntityManagerFactory(
            @Qualifier("entityManagerFactoryBuilder") EntityManagerFactoryBuilder builder,
            @Qualifier("queryDataSource") DataSource dataSource) {

        return builder
                .dataSource(dataSource)
                .packages(
                        "com.mycorp.finance.customer.infrastructure.persistence.entity.query",
                        "com.mycorp.finance.banking.account.infrastructure.persistence.entity.query"
                )
                .persistenceUnit("query")
                .properties(queryJpaProperties())
                .build();
    }

    /**
     * Hibernate-specific JPA properties for query DB.
     * Avoid schema generation in read-only contexts.
     */
    private Map<String, Object> queryJpaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.hbm2ddl.auto", "update"); // no ddl for read model
        props.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        props.put("hibernate.format_sql", true);
        return props;
    }

    /**
     * Configures a dedicated transaction manager for query DB.
     */
    @Bean
    public PlatformTransactionManager queryTransactionManager(
            @Qualifier("queryEntityManagerFactory") EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
