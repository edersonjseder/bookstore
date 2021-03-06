package com.bookstore.config;

import org.h2.server.web.WebServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.bookstore.backend.service.EmailService;
import com.bookstore.backend.service.MockEmailService;

/**
 * Configures this class to simulate an email service.
 *
 * Created by root on 04/06/17.
 */
@Configuration
@Profile("dev")
@PropertySource("file:///${user.home}/.bookstore/application-dev.properties") //gets the data info from the file in local directory
public class DevelopmentConfig {

    /**
     * Returns The Mock Email Service instance
     * @return An instance of the Mock Email Service class
     */
    @Bean
    public EmailService emailService() {
        return new MockEmailService();
    }

    @Bean
    public ServletRegistrationBean h2ConsoleServletRegistration() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new WebServlet());
        bean.addUrlMappings("/console/*");
        return bean;
    }
}
