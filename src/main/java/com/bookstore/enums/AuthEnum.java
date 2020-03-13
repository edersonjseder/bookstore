package com.bookstore.enums;

public enum AuthEnum {
	
	GRANT_TYPE_AUTHORIZATION_CODE("authorization_code"),
	GRANT_TYPE_CLIENT_CREDENTIALS("client_credentials"),
	GRANT_TYPE_IMPLICIT("implicit"),
	GRANT_TYPE_PASSWORD("password"),
	GRANT_TYPE_REFRESH_TOKEN("refresh_token"),
	REALM("BOOK_STORE_REALM"),
    CLIENT_ID("angularjsclientid"),
    SIGNING_KEY("NeYsIHjmkzPC57L"),
    SCOPE_READ("read"),
    SCOPE_WRITE("write"),
    SCOPE_ALL("all"),
    RESOURCE_ID("bookstore-sso");

    private final String mValue;

    AuthEnum(String mValue) {
        this.mValue = mValue;
    }

	public String getmValue() {
		return mValue;
	}

}
