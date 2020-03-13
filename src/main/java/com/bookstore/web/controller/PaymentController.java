package com.bookstore.web.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.backend.domain.User;
import com.bookstore.backend.domain.UserBilling;
import com.bookstore.backend.domain.UserPayment;
import com.bookstore.backend.service.UserPaymentService;
import com.bookstore.backend.service.UserService;
import com.bookstore.utils.RoutePathUtils;

@RestController
@RequestMapping(path = RoutePathUtils.PATH)
public class PaymentController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private UserPaymentService userPaymentService;
	
	@RequestMapping(value = RoutePathUtils.PATH_USER_PAYMENT + 
							RoutePathUtils.PATH_ADD, method = RequestMethod.POST)
	public ResponseEntity<Object> addNewCreditCard(@RequestBody UserPayment userPayment, Principal principal){
		
		User userRetrieved = userService.findByUsername(principal.getName());
		
		UserBilling userBilling = userPayment.getUserBilling();
		
		return new ResponseEntity<>("Payment added successfully", HttpStatus.OK);
		
	}
	
}
