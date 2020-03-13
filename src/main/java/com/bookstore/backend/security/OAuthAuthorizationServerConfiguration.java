package com.bookstore.backend.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.bookstore.enums.AuthEnum;

@Configuration
@EnableAuthorizationServer
public class OAuthAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
	
//	@Value("${jwt.expires_in}")
//	private int accessTokenValidity;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private AuthDataSourceConfig authDataSourceConfig;

	@Autowired
	private JwtAccessTokenConverter accessTokenConverter;
	
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    	
        clients
        	   .jdbc(authDataSourceConfig.dataSource());
        /**
        	   .inMemory()
               .withClient(AuthEnum.CLIENT_ID.getmValue()) // Header parameter Base 64 encoded: Client:Secret = angularjsclientid:NeYsIHjmkzPC57L
               .secret(AuthEnum.SIGNING_KEY.getmValue())
               .authorizedGrantTypes(AuthEnum.GRANT_TYPE_PASSWORD.getmValue(),
            		   				 AuthEnum.GRANT_TYPE_CLIENT_CREDENTIALS.getmValue(),
            		   				 AuthEnum.GRANT_TYPE_IMPLICIT.getmValue(),
            		   				 AuthEnum.GRANT_TYPE_REFRESH_TOKEN.getmValue())
               .authorities(RolesEnum.ADMIN.getRoleName(), RolesEnum.TRUSTED.getRoleName())
               .scopes(AuthEnum.SCOPE_READ.getmValue(), AuthEnum.SCOPE_WRITE.getmValue())
               .accessTokenValiditySeconds(accessTokenValidity) //Access token is only valid for 2 minutes.
               .refreshTokenValiditySeconds(600); //Refresh token is only valid for 10 minutes.
		**/
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    	
		TokenEnhancerChain enhancerChain = new TokenEnhancerChain();
		enhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));

    	endpoints
    			.tokenStore(authDataSourceConfig.tokenStore())
    			.accessTokenConverter(accessTokenConverter)
    			.tokenEnhancer(enhancerChain)
		    	.authenticationManager(authenticationManager);
    	
    } 
	
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        
    	oauthServer
    			  .allowFormAuthenticationForClients()
    			  .checkTokenAccess("permitAll()")
    			  .realm(AuthEnum.REALM.getmValue());
    	
    }
    
}

