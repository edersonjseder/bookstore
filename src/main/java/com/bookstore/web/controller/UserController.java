package com.bookstore.web.controller;

import java.util.HashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.backend.domain.User;
import com.bookstore.backend.service.UserService;
import com.bookstore.utils.RoutePathUtils;

@RestController
@RequestMapping(path = RoutePathUtils.PATH)
public class UserController {
	
    
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);
    
	@Autowired	
	private UserService userService;
	
	
	//===================================== New User ====================================================
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_ADD, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> signUpUser(@RequestBody User user){
		
		LOG.info("inside method: signUpUser(User user)");
		
		if(userService.findByUsername(user.getUsername()) != null) {
			return new ResponseEntity<Object>(user.getUsername() + " already exists", HttpStatus.BAD_REQUEST);
		}
		
		if(userService.findByEmail(user.getEmail()) != null) {
			return new ResponseEntity<Object>("User with the email " + user.getEmail() + " already exists", HttpStatus.BAD_REQUEST);
		}
		
		User newUserRegistered = userService.registerNewUser(user);
		
		LOG.info("RESPONSE: " + newUserRegistered.getUsername() + " - " + newUserRegistered.getEmail());
		
		return new ResponseEntity<>(newUserRegistered, HttpStatus.OK);
		
	}
	//===================================================================================================

	
	//===================================== Forgot Password User ====================================================
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_FORGOT_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> forgotPasswordUser(@RequestBody HashMap<String, String> mapper){
		
		LOG.info("inside method: forgotPasswordUser(String email)");
		
		User userRetrieved = userService.findByEmail(mapper.get("email"));
		
		if(userRetrieved == null) {
			return new ResponseEntity<Object>("User with the email " + mapper.get("email") + " not found", HttpStatus.BAD_REQUEST);
		}
		
		User newUserRegistered = userService.updateForgotPasswordUserInformationByEmail(userRetrieved);
		
		LOG.info("RESPONSE: " + newUserRegistered.getUsername() + " - " + newUserRegistered.getEmail());
		
		return new ResponseEntity<>("E-mail sent", HttpStatus.OK);
		
	}
	//===================================================================================================

	
	//=================================== Update User ===================================================
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_UPDATE + "/{id}", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Object> updateUser(@PathVariable Integer id, @RequestBody User user) {

		LOG.info("inside method: updateUser(Integer id, User user)");

		User userUpdated = userService.updateUserInformationById(id, user);

		LOG.info("RESPONSE: " + userUpdated.getUsername() + " - " + userUpdated.getEmail());

		return new ResponseEntity<>(userUpdated, HttpStatus.OK);

	}
	//===================================================================================================

	
	//-------------------Retrieve Single User by id--------------------------------------------------------
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_USER_DETAILS + "/{id}", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserById(@PathVariable("id") Integer id){
		
		User userFetched = userService.findUserById(id);
		
		return new ResponseEntity<>(userFetched, HttpStatus.OK);
		
	}
	//-------------------Retrieve Single User by id--------------------------------------------------------
	

	//-------------------Retrieve Single User by email--------------------------------------------------------
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_USER_DETAILS + "/{email}", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserByEmail(@PathVariable("email") String email){
		
		User userFetched = userService.findByEmail(email);
		
		return new ResponseEntity<>(userFetched, HttpStatus.OK);
		
	}
	//-------------------Retrieve Single User by email--------------------------------------------------------

	
	//-------------------Retrieve Single User by user name--------------------------------------------------------
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_USER_DETAILS + "/{username}", 
					method = RequestMethod.GET, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> findUserByUsername(@PathVariable("username") String username){
		
		User userFetched = userService.findByUsername(username);
		
		return new ResponseEntity<>(userFetched, HttpStatus.OK);
		
	}
	//-------------------Retrieve Single User by user name--------------------------------------------------------

	
	//========================== Remove Single User by user name ==============================================
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_DELETE + "/{username}", 
					method = RequestMethod.DELETE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUserByUsername(@PathVariable("username") String username){
		
		userService.removeUserByUsername(username);
		
		return new ResponseEntity<>("User Removed Successfuly", HttpStatus.OK);
		
	}
	//=========================== Remove Single User by user name =============================================
	
	
	//========================== Remove Single User by Id ==============================================
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_DELETE + "/{id}", 
					method = RequestMethod.DELETE, 
					produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> deleteUserById(@PathVariable("id") Integer id){
		
		userService.removeUserById(id);
		
		return new ResponseEntity<>("User Removed Successfuly", HttpStatus.OK);
		
	}
	//=========================== Remove Single User by Id =============================================

	
	//-------------------Retrieve All Users--------------------------------------------------------
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(value = RoutePathUtils.PATH_USER + 
							RoutePathUtils.PATH_USER_ALL, 
				    method = RequestMethod.GET,
				    produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Set<User>> userAll() {

		Set<User> listUsersFetched = userService.findAllUsers();

		return new ResponseEntity<>(listUsersFetched, HttpStatus.OK);

	}
	//-------------------Retrieve All Users--------------------------------------------------------
	
	
	@RequestMapping(RoutePathUtils.PATH_USER + RoutePathUtils.PATH_CHECK_SESSIONS)
	public ResponseEntity<String> checkSession(){
		
		LOG.info("RESPONSE_ENTITY: " + HttpStatus.OK);
		
		return new ResponseEntity<>("Session Active!", HttpStatus.OK);
	}
	

	@RequestMapping(value = "/user/logout", method = RequestMethod.POST)
	public ResponseEntity<String> logout() {
		
		SecurityContextHolder.clearContext();
		
		return new ResponseEntity<String>("Logout Successfully done", HttpStatus.OK);
	}
}
