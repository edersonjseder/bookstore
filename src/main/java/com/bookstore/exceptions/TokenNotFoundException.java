package com.bookstore.exceptions;

public class TokenNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;
	
    public String jti;
    public String message;
    
    public TokenNotFoundException(String jti) {
        super();
    
        this.jti = jti;
        
        message = String.format("Token with jti[%s] not found.",jti);
        
    }

}
