package com.bookstore.exceptions;

import static java.lang.String.format;

public class TheUserNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TheUserNotFoundException(String username){
		super(format("User with username % doesn't exist", username));
	}
}
