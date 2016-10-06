/**
 * 
 */
package com.app.service.jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author kaminydoraisami This class contains jdbc connection related methods
 */
public class ConnectionManager {

	/**
	 * database url property key in properties file
	 */
	private static final String PROPERTY_DB_URL = "db.url";
	/**
	 * database user property key in properties file
	 */
	private static final String PROPERTY_DB_USER = "db.user";
	/**
	 * database password property key in properties file
	 */
	private static final String PROPERTY_DB_PASSWORD = "db.password";

	// url, user and password come from a properties file
	private static String DB_URL;
	private static String DB_USER;
	private static String DB_PASSWORD;

	/**
	 * Constructor
	 */
	public ConnectionManager() {
	}

	/**
	 * This method returns a connection instance for the postgresql database
	 * 
	 * @return Connection
	 */
	public static Connection getDatabaseConnection(String dbPropertiesFilePath) {

		loadDBProperties(dbPropertiesFilePath);

		Connection connection = null;

		try {

			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
			return connection;

		} catch (SQLException e) {

			System.err.println(e.getMessage());
		}
		return connection;
	}

	/**
	 * This method closes the connection for the postgresql database
	 * 
	 */
	public static void closeDatabaseConnection(Connection connection) {

		try {
			if (connection != null) {
				connection.close();
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method retrieves from properties file the properties necessary to
	 * get a connection instance
	 * 
	 * INFO: returning DB properties instead of having void for easy testability
	 * 
	 * @param propertiesLocation
	 */
	public static Properties loadDBProperties(String propertiesLocation) {

		Properties dbProperties = new Properties();
		{

			try {
				// load the properties file
				dbProperties
						.load(new FileInputStream(propertiesLocation + java.io.File.separator + "database.properties"));

				// get the url, user and password from the properties file
				DB_URL = dbProperties.getProperty(PROPERTY_DB_URL);
				DB_USER = dbProperties.getProperty(PROPERTY_DB_USER);
				DB_PASSWORD = dbProperties.getProperty(PROPERTY_DB_PASSWORD);

			} catch (IOException e) {
				System.err.println("IO Exception thrown while reading database properties file");
				e.printStackTrace();
			}
		}
		return dbProperties;
	}

}
