package com.bookstore.integration.test.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bookstore.backend.domain.Book;
import com.ibatis.common.jdbc.ScriptRunner;

public class Utils {
	
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(Utils.class);
    
    // Values from the application.properties file
    private String className = "datasource.driverClassName";
	
	private String dataSourceUrl = "datasource.url";
	
	private String usernameDb = "datasource.username";

    private String passwordDb = "datasource.password";
    
	/**
	 * When the application is starting, this method executes a SQL script from the .sql file to the MySQL database 
	 * whose persistence unit is described in the application.properties file
	 * 
	 * @param scriptFilePath - The path to the file where the script is written
	 * @throws IOException - The exception if the reading goes wrong
	 * @throws SQLException - The exception if the SQL script is wrong or database is wrong
	 */
	public void executeScriptUsingScriptRunner(String scriptFilePath) throws IOException, SQLException {
		
		Reader reader = null;
		Connection conn = null;
		
		try {
			
			LOG.info("Starting to execute SQL script");
			
			// load driver class for mysql
			Class.forName(getProperty(className));
			
			// create connection
			conn = DriverManager.getConnection(getProperty(dataSourceUrl), getProperty(usernameDb), getProperty(passwordDb));
			
			// create ScripRunner object
			ScriptRunner scriptExecutor = new ScriptRunner(conn, false, false);
			
			// initialize file reader
			reader = new BufferedReader(new FileReader(scriptFilePath));
			
			// execute script with file reader as input
			scriptExecutor.runScript(reader);
			
			LOG.info("Script executed");
			
		} catch (Exception e) {
			
			LOG.error("<== Error to execute SQL script ==>");
			e.printStackTrace();
			
		} finally {
			// close file reader
			if(reader != null) {
				reader.close();
			}
			// close db connection
			if(conn != null) {
				conn.close();
			}
		}
	}

	
	private String getProperty(String key) {
		
		Properties prop = new Properties();
		String property = "";
		
		try {
		    //load a properties file from class path, inside static method
		    prop.load(getClass().getClassLoader().getResourceAsStream("application.properties"));

		    //get the property value and print it out
		    property = prop.getProperty(key);

		} 
		catch (IOException ex) {
		    ex.printStackTrace();
		}
		
		return property;
		
	}
	
    /**
     * Creates a book with basic attributes set for test.
     *
     * @param bookTitle The book title parameter.
     * @return The Book object.
     */
    public static Book createBook(String bookTitle) {
    	
    	Book book = new Book();
    	
        book.setTitle(bookTitle);

        return book;
    }

}
