package com.bookstore.enums;

public enum FilterHeadersEnum {
	
	HEADER_ALLOW_ORIGIN("Access-Control-Allow-Origin", "http://localhost:4200"),
    HEADER_ALLOW_METHODS("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE"),
    HEADER_ALLOW_SOME_METHODS("Access-Control-Allow-Methods", "POST,GET,DELETE"),
    HEADER_MAX_AGE("Access-Control-Max-Age", "3600"),
    HEADER_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials", "true"),
    HEADER_ALLOW_HEADERS("Access-Control-Allow-Headers","password, authorization_code, refresh_token, client_credentials, implicit"),
    HEADER_ALLOW_MORE_HEADERS("Access-Control-Allow-Headers", "Content-Type,Authorization,X-XSRF-TOKEN,x-auth-token,X-Access-Token," +
    						"access-control-request-headers,access-control-request-method,accept,Origin,authorization,x-requested-with,X-Auth-Token");

    private final String mKey;

    private final String mValue;

    FilterHeadersEnum(String mKey, String mValue) {
        this.mKey = mKey;
        this.mValue = mValue;
    }

	public String getmKey() {
		return mKey;
	}

	public String getmValue() {
		return mValue;
	}

}
