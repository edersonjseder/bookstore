package com.bookstore.utils;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.MissingResourceException;
import java.util.Properties;

public class Messages {
	
	public final static String MESSAGE_DELETE = "Successfully Deleted";
	public final static String MESSAGE_DELETE_FAIL = "Not Possible to Delete ";
	
	public final static String MESSAGE_SAVE = "Successfully Saved";
	public final static String MESSAGE_SAVE_FAIL = "Not Possible to Save ";
	
	public final static String MESSAGE_UPDATE = "Successfully Updated";
	public final static String MESSAGE_UPDATE_FAIL = "Not Possible to Update ";
	
	public final static String MESSAGE_SEARCH_FAIL = "Not Possible to find ";
    
    public String getString(String key, Object... params  ) {
    	
        try {
        	
            return getProperty(key, params);
            
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
    
	private String getProperty(String key, Object... params) {
		
		Properties prop = new Properties();
		String property = "";
		
		try {
		    //load a properties file from class path, inside method
		    prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

		    //get the property value
		    String propertyWithParams = prop.getProperty(key);
		    
		    //Set the parameters to the keys in file
		    property = MessageFormat.format(propertyWithParams, params);

		} catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return property;
		
	}
	
}
