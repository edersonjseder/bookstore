package com.bookstore.backend.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

@Configuration
public class AuthDataSourceConfig {
    
	@Value("${spring.datasource.driverClassName}")
    private String oauthDriverClassName;
	
    @Value("${spring.datasource.url}")
	private String oauthDataSourceUrl;
	
    @Value("${spring.datasource.username}")
	private String oauthUsernameDb;

    @Value("${spring.datasource.password}")
    private String oauthPasswordDb;
	
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(dataSource());
	}
	
	@Bean 
	public DataSource dataSource() { 
		/**
    	DataSource tokenDataSource = DataSourceBuilder.create()
                .driverClassName(oauthClassName)
                .username(oauthUsernameDb)
                .password(oauthPasswordDb)
                .url(oauthDataSourceUrl)
                .build();**/
		
		DriverManagerDataSource dataSource = new DriverManagerDataSource(); 
		
		dataSource.setDriverClassName(oauthDriverClassName); 
		dataSource.setUrl(oauthDataSourceUrl); 
		dataSource.setUsername(oauthUsernameDb); 
		dataSource.setPassword(oauthPasswordDb); 
		
		return dataSource; 
		
	}

}
