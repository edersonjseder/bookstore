package com.bookstore.backend.service;

import org.springframework.mail.SimpleMailMessage;

public interface EmailService {
	
    /**
     * Sends an email with the content of the Simple Mail Message object
     * @param message The object containing the email content
     */
    public void sendGenericEmailMessage(SimpleMailMessage message);

}
