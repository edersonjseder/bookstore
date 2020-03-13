package com.bookstore.backend.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.backend.domain.User;
import com.bookstore.backend.domain.UserPayment;
import com.bookstore.backend.repositories.RoleRepository;
import com.bookstore.backend.repositories.UserRepository;
import com.bookstore.backend.security.Role;
import com.bookstore.enums.RolesEnum;
import com.bookstore.exceptions.ErrorRepositoryException;
import com.bookstore.utils.EmailConstructor;
import com.bookstore.utils.FileUploadUtils;
import com.bookstore.utils.SecurityUtils;

@Service
@Transactional
public class UserService {

    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @Autowired
    private EmailConstructor mailConstructor;
    
    @Autowired
    private SmtpEmailService emailService;

    /**
     * Method that creates a new user on database
     * 
     * @param user The user coming from front end
     * @return the User object saved on database
     */
    public User registerNewUser(User user){
    	
    	User userCreated = null;
    	
    	try {

    		//Set of roles of the user
    		Set<Role> userRoles = new HashSet<>();
    		userRoles.add(new Role(RolesEnum.BASIC));
    		
    		//Password to be sent by email
    		String plainPassword = user.getPassword();
    		
    		// encrypting the password field to be saved in database
            String encryptedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encryptedPassword);

            /** Saves the other side of the user to roles
             * relationship by persisting all roles in
             * the UserRoles collection 
             */
            for (Role role : userRoles) {

                roleRepository.save(role);
                
            }
            
            //Adding the roles to the respective user
            user.getRoles().addAll(userRoles);
            
            //Adding a list of User payment
            user.setUserPaymentList(new ArrayList<UserPayment>());
            
            /**
             * This method comes from the User profile page, requested by the new register
             */
            userCreated = userRepository.save(user);
            
            LOG.info("Password {} to be send by mail.", plainPassword);
            
            /**
             * Method that sends e-mail containing user credentials
             */
            SimpleMailMessage mail = mailConstructor.constructNewUserEmail(user, plainPassword);
            emailService.sendGenericEmailMessage(mail);
    		
    	} catch (Exception e){
            throw new ErrorRepositoryException(e);
        }
    	
    	return userCreated;
    	
    }
    
    public User create(User user){

        User createdUser = userRepository.findByEmail(user.getEmail());

        // Check if the user already exist
        if (createdUser != null){

            LOG.info("User with username {} already exist.", user.getUsername());

        } else {

            try {

                // encrypting the password field to be saved in database
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(encryptedPassword);

                /** Saves the other side of the user to roles
                 * relationship by persisting all roles in
                 * the UserRoles collection */
                for (Role role : user.getRoles()) {

                    roleRepository.save(role);
                }

                /**
                 * This method comes from the User profile page, requested by the new register
                 */
                createdUser = userRepository.save(user);

                LOG.info("User {} created", createdUser.getUsername());
                
                String filePath = getClass().getClassLoader().getResource("schema_t_book.sql").getPath();
                
                LOG.info("Complete SQL script path {} ", filePath);
                
                FileUploadUtils upload = new FileUploadUtils();
                upload.executeScriptUsingScriptRunner(filePath);
                
            } catch (Exception e){
                throw new ErrorRepositoryException(e);
            }
        }


        return createdUser;
    }

    /**
     * Updates a user for the given id.
     * 
     * @param id The id to look for the user object
     * @param user The User object
     * @return a updated User given the id passed or null if none is found.
     */
	public User updateUserInformationById(Integer id, User user) {

		User userUpdated = null;
		
		try {

			User userToUpdate = userRepository.findOne(id);

			if (userToUpdate != null) {

                // encrypting the password field to be saved in database
                String encryptedPassword = passwordEncoder.encode(user.getPassword());
				
				userToUpdate.setUsername(user.getUsername());
				userToUpdate.setPassword(encryptedPassword);
				userToUpdate.setEmail(user.getEmail());
				userToUpdate.setFirstName(user.getFirstName());
				userToUpdate.setLastName(user.getLastName());
				userToUpdate.setPhone(user.getPhone());
				userToUpdate.setEnabled(user.isEnabled());
				
				userUpdated = userRepository.save(userToUpdate);
				
			}

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
		return userUpdated;
			
	}
	
    /**
     * Updates a user for the given email.
     * 
     * @param email The email to look for the user object
     * @param user The User object
     * @return a updated User given the email passed or null if none is found.
     */
	public User updateForgotPasswordUserInformationByEmail(User user) {

		User userUpdated = user;
		
		try {
    		
    		//Password to be sent by email
    		String plainPassword = SecurityUtils.randomPassword();
    		
    		// encrypting the password field to be saved in database
            String encryptedPassword = passwordEncoder.encode(plainPassword);
            user.setPassword(encryptedPassword);

            userUpdated = userRepository.save(user);
            

            /**
             * Method that sends e-mail containing user credentials
             */
            SimpleMailMessage mail = mailConstructor.constructNewUserEmail(user, plainPassword);
            emailService.sendGenericEmailMessage(mail);
			

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
		return userUpdated;
			
	}
	
	public void removeUserById(Integer id) {
		
		User userToRemove = null;
		
		try {

			userToRemove = userRepository.findOne(id);

			if (userToRemove != null) {
				
				userRepository.delete(id);
				
			}

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
	}

	public void removeUserByUsername(String username) {
		
		User userToRemove = null;
		
		try {

			userToRemove = userRepository.findByUsername(username);

			if (userToRemove != null) {
				
				userRepository.deleteUserByUsername(username);
				
			}

		} catch (Exception e) {
			LOG.error("Problem with loading data {} ", e.getMessage());
		}
		
	}
	
    public User findByUsername(String username){
    	return userRepository.findByUsername(username);
    }

    public User findUserById(Integer id){
    	return userRepository.findOne(id);
    }
    
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean checkUserExists(String username, String email){
        if (checkUsernameExists(username) || checkEmailExists(username)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkUsernameExists(String username) {
        if (null != findByUsername(username)) {
            return true;
        }

        return false;
    }

    public boolean checkEmailExists(String email) {
        if (null != findByEmail(email)) {
            return true;
        }

        return false;
    }

    public Set<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void enableUser(String username) {
        User user = findByUsername(username);
        user.setEnabled(true);
        userRepository.save(user);
    }

    public void disableUser(String username) {
        User user = findByUsername(username);
        user.setEnabled(false);

        LOG.info("The username {} is {}", username, user.isEnabled());

        userRepository.save(user);

        LOG.info("The username {} is {}", username, user.isEnabled());
    }
    
}
