package com.bookstore.backend.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.bookstore.enums.AuthEnum;
import com.bookstore.utils.RoutePathUtils;

@Configuration
@EnableResourceServer
public class OAuthResourceServerConfiguration extends ResourceServerConfigurerAdapter {
	
	@Autowired
	private DefaultTokenServices tokenServices;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {

		http
			.requestMatcher(new OAuthRequestedMatcher())
            .anonymous().disable()
			.authorizeRequests()
            .antMatchers(HttpMethod.OPTIONS).permitAll()
            .antMatchers(RoutePathUtils.PATH + "/*").hasRole("ADMIN")
			.and()
			.exceptionHandling()
			.authenticationEntryPoint(customAuthenticationEntryPoint);

	}
	
	// Resource id
	@Override
	public void configure(ResourceServerSecurityConfigurer resConfig) throws Exception {
		
		resConfig
			    .resourceId(AuthEnum.RESOURCE_ID.getmValue())
				.tokenServices(tokenServices)
				.tokenStore(tokenStore);
		
	}
	
    private static class OAuthRequestedMatcher implements RequestMatcher {
        public boolean matches(HttpServletRequest request) {
            String auth = request.getHeader("Authorization");
            // Determine if the client request contained an OAuth Authorization
            boolean haveOauth2Token = (auth != null) && auth.startsWith("Bearer");
            boolean haveAccessToken = request.getParameter("access_token")!=null;
            return haveOauth2Token || haveAccessToken;
        }
    }

}
