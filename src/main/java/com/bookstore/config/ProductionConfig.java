package com.bookstore.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.bookstore.backend.service.EmailService;
import com.bookstore.backend.service.SmtpEmailService;

@Configuration
@Profile("prod")
@EnableJpaRepositories(basePackages = "com.bookstore.backend.repositories")
@EntityScan(basePackages = {"com.bookstore.backend.domain", "com.bookstore.backend.security"})
@ComponentScan
@ImportResource({"classpath*:spring-security-oauth2.xml"})
@EnableTransactionManagement // This enables annotation based transaction management (JPA transaction is managed by default)
@PropertySource("file:///${user.home}/.bookstore/application-prod.properties")
public class ProductionConfig {
	
    /**
     * Returns The Smtp Email Service instance
     *
     * @return An instance of the Smtp Email Service class
     */
    @Bean
    public EmailService emailService() {
        return new SmtpEmailService();
    }

}
