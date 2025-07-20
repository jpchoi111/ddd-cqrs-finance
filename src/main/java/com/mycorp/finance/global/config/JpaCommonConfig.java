package com.mycorp.finance.global.config;

import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import java.util.HashMap;

@Configuration
public class JpaCommonConfig {

    /**
     * Provides a shared EntityManagerFactoryBuilder for all data source configurations.
     * This helps avoid multiple conflicting builder beans in the Spring context.
     */
    @Bean
    public EntityManagerFactoryBuilder entityManagerFactoryBuilder() {
        return new EntityManagerFactoryBuilder(
                new HibernateJpaVendorAdapter(),
                new HashMap<>(),
                null
        );
    }
}
