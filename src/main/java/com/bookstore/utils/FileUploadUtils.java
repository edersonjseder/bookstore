package com.bookstore.utils;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.ibatis.common.jdbc.ScriptRunner;

public class FileUploadUtils {
	
    /** The application logger */
    private static final Logger LOG = LoggerFactory.getLogger(FileUploadUtils.class);
    
    private static final String BOOK_PICTURE_FILE_NAME = "bookpicture";
    
    // Values from the application.properties file
    private String tempBookImageStore = "image.store.tmp.folder";
    
    private String oauthClassName = "datasource.driverClassName";
	
	private String oauthDataSourceUrl = "datasource.url";
	
	private String oauthUsernameDb = "datasource.username";

    private String oauthPasswordDb = "datasource.password";
    
    // Class to read the file
	private static BufferedOutputStream stream;
	
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
			Class.forName(getProperty(oauthClassName));
			
			// create connection
			conn = DriverManager.getConnection(getProperty(oauthDataSourceUrl), getProperty(oauthUsernameDb), getProperty(oauthPasswordDb));
			
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

	/**
	 * I'll still write the purpose of this method soon
	 * 
	 * @param request
	 * @param bookName
	 * @return
	 */
	public String storeImageBook(HttpServletRequest request, String bookName) {

		String bookImageUrl = null;
		
		try {
			
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			Iterator<String> iterator = multipartRequest.getFileNames();
			MultipartFile multipartFile = multipartRequest.getFile(iterator.next());
			
			byte[] bytes = multipartFile.getBytes();
			
            // The root of our temporary assets. It will create if it doesn't exist
            File tmpBookImageStoredFolder =
                    new File(getProperty(tempBookImageStore) + File.separatorChar + bookName);
            
            // Verifies if the folder exist
            if (!tmpBookImageStoredFolder.exists()){
                LOG.info("Creating the temporary root for the assets");
                tmpBookImageStoredFolder.mkdirs(); // Creates the folder
            }
            
            // Generates the file
            File tmpBookImageFile =
                    new File(tmpBookImageStoredFolder.getAbsolutePath()
                            + File.separatorChar
                            + BOOK_PICTURE_FILE_NAME
                            + "."
                            + FilenameUtils.getExtension(multipartFile.getOriginalFilename()));// Gets the extension file
            
            try {
            	
                stream = new BufferedOutputStream(new FileOutputStream(
                         new File(tmpBookImageFile.getAbsolutePath())));
                
                bookImageUrl = tmpBookImageFile.getAbsolutePath();

                stream.write(bytes);
                stream.close();

            } catch (Exception ex){
                LOG.error("An error occurred while writing the file on BufferedOutputStream");
            }

            // Clean up the temporary folder
//            tmpBookImageFile.delete();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bookImageUrl;
		
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

}
