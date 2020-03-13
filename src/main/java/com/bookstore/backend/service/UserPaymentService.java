package com.bookstore.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.backend.domain.UserPayment;
import com.bookstore.backend.repositories.UserPaymentRepository;

@Service
public class UserPaymentService {

	@Autowired
	private UserPaymentRepository userPaymentRepository;
	
	public UserPayment findUserPaymentById(Integer id) {
		return userPaymentRepository.findOne(id);
	}
	
	public void removeUserPaymentById(Integer id) {
		userPaymentRepository.delete(id);
	}
}
