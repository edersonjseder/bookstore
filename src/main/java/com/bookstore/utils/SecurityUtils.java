package com.bookstore.utils;

import java.security.SecureRandom;
import java.util.Random;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    /** The encryption SALT. */
    private static final String SALT = "ddlsalt";
    
    /**
     * Bean to encrypt the password coming from input text written by user
     * during the signing up
     * 
     * @return the encrypted password
     */
    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }
    
    /**
     * Bean that generates a random password
     * 
     * @return
     */
    @Bean
    public static String randomPassword() {
    	String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    	StringBuilder strSalt = new StringBuilder();
    	Random rand = new Random();
    	
    	while(strSalt.length() < 18){
    		int index = (int) (rand.nextFloat() * SALTCHARS.length());
    		strSalt.append(SALTCHARS.charAt(index));
    	}
    	
    	String salt = strSalt.toString();
    	
    	return salt;
    }
}
