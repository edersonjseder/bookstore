package com.bookstore.backend.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.backend.domain.User;
import com.bookstore.backend.repositories.UserRepository;
import com.bookstore.backend.security.CustomUserDetails;
import com.bookstore.exceptions.UserNotActivatedException;


/**
 * Created by root on 01/07/17.
 */
@Service
public class UserSecurityService implements UserDetailsService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityService.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.debug("Authenticating {}", username);
        String lowerCaseUsername = username.toLowerCase();
    	
        User userFetchedFromDB = userRepository.findByUsername(lowerCaseUsername);
        
        if (userFetchedFromDB == null){
            
        	LOG.warn("Username {} not found.", lowerCaseUsername);
            throw new UsernameNotFoundException("Username " + lowerCaseUsername + " not found.");
            
        } else if (!userFetchedFromDB.isEnabled()){

        	LOG.warn("User {} not Activated.", lowerCaseUsername);
        	throw new UserNotActivatedException("User " + lowerCaseUsername + " is not Activated.");
        	
        }
        
        LOG.info("Username {} found here ==================>>.", userFetchedFromDB.getUsername());

        return new CustomUserDetails(userFetchedFromDB);
    }
}
