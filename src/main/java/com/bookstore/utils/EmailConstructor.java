package com.bookstore.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import com.bookstore.backend.domain.User;

/**
 * This class is the implementation of the email sending
 * it gets the user information from the front-end form
 * and stores in the Feedback object to be sent by e-mail.
 * Created by root on 04/06/17.
 */
@Component
public class EmailConstructor {
	
    @Value("${default.from.address}")//spring sets the value from the application.properties value to the string variable
    private String defaultFromAddress;
    
    @Value("${default.to.address}")
    private String defaultToAddress;
    
    @Value("${subject.email}")//spring sets the value from the application.properties value to the string variable
    private String subjectMail;

    private String emailText = "email.text";

    /**
     * Creates a Simple Mail Message from User pojo
     * @param user The User pojo
     * @param password The password parameter
     * @return
     */
	public SimpleMailMessage constructNewUserEmail(User user, String password) {
		
        SimpleMailMessage message = new SimpleMailMessage();
        Messages propertyMessages = new Messages();

//        message.setTo(user.getEmail());
        message.setTo(defaultToAddress);
        message.setFrom(defaultFromAddress);
        message.setSubject(subjectMail);
        message.setText(propertyMessages.getString(emailText, user.getUsername(), password));

        return message;
        
	}
	
}
