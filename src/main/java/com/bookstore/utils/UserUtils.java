package com.bookstore.utils;

import java.util.HashSet;
import java.util.Set;

import com.bookstore.backend.domain.User;
import com.bookstore.backend.security.Role;
import com.bookstore.enums.RolesEnum;

/**
 * Created by root on 01/07/17.
 */
public class UserUtils {

    /**
     * Non instantiable
     */
    private UserUtils(){
        throw new AssertionError("Non instantiable");
    }

//    public static ClientDetail createClientForDB(String authClientId, String authClientSecret) {
//    	
//    	ClientDetail authClient = new ClientDetail();
//    	
//        authClient.setClientId(authClientId);
//        authClient.setResourceIds(new HashSet<>(Arrays.asList("rest_api")));
//        authClient.setClientSecret(authClientSecret);
//        authClient.setRefreshTokenValiditySeconds(4500);
//        authClient.setAccessTokenValiditySeconds(4500);
//        authClient.setAuthorities(new HashSet<>(Arrays.asList("trust", "read", "write")));
//        authClient.setAuthorizedGrantTypes(new HashSet<>(Arrays.asList("client_credentials", "authorization_code", "implicit", "password", "refresh_token")));
//        authClient.setScope(new HashSet<>(Arrays.asList("trust", "read", "write")));
//        
//        return authClient;
//        
//    }

    public static User createUserByRegistration(User userFromRegistration) {
        User user = new User();
        user.setUsername(userFromRegistration.getUsername());
        user.setPassword(userFromRegistration.getPassword());
        user.setEmail(userFromRegistration.getEmail());
        user.setFirstName(userFromRegistration.getFirstName());
        user.setLastName(userFromRegistration.getLastName());
        user.setPhone(userFromRegistration.getPhone());
        user.setEnabled(true);
//        user.setProfileImageUrl("https://blablabla.images.com/basicuser");
        return user;
    }
    
    /**
     * Creates a user with basic attributes set for test.
     *
     * @param username The username parameter.
     * @param email The email parameter.
     * @return The User object.
     */
    public static User createUser(String username, String email) {
        User user = new User();
        user.setUsername(username);
        user.setPassword("secret");
        user.setEmail(email);
        user.setFirstName("Adam");
        user.setLastName("Martin");
        user.setPhone("123456789");
        user.setEnabled(true);
//        user.setProfileImageUrl("https://blablabla.images.com/basicuser");
        return user;
    }
    
    public static User createNewAdminUser(String username, String email){
    	
    	/** Creates a role */
    	Role adminRole = createBasicRole(RolesEnum.ADMIN);
    	
    	/** Creates the user and add the plan object as a foreign key */
    	User adminUser = createUser(username, email);
    	
    	/** Creates a Set collection of roles due to the
    	 * one to many relationship between entities */
    	Set<Role> roles = new HashSet<>();
    	
    	/** Creates the object that represent the one to many
    	 * relationship between user and role entities and add
    	 * both objects on entity as foreign key */
    	roles.add(adminRole);
    	
    	/** Adding the object collection of user roles to user entity
    	 * (Always call the get method of Set collection to add objects in JPA)*/
    	adminUser.setRoles(roles);
    	
    	return adminUser;
    	
    }
    
    public static User createNewUser(String username, String email){
    	
        /** Creates a role */
        Role basicRole = createBasicRole(RolesEnum.BASIC);

        /** Creates the user and add the plan object as a foreign key */
        User basicUser = createUser(username, email);
        
        /** Creates a Set collection of roles due to the
         * one to many relationship between entities */
        Set<Role> roles = new HashSet<>();

        /** Creates the object that represent the one to many
         * relationship between user and role entities and add
         * both objects on entity as foreign key */
        roles.add(basicRole);

        /** Adding the object collection of user roles to user entity
         * (Always call the get method of Set collection to add objects in JPA)*/
        basicUser.setRoles(roles);
        
        return basicUser;
        
    }
    
    protected static Role createBasicRole(RolesEnum rolesEnum) {
        return new Role(rolesEnum);
    }
}
