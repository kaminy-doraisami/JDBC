/**
 * 
 */
package com.app.service.jdbc;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kaminydoraisami
 * 
 */
public class ConnectionManagerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * This method is to test
	 * {@link com.app.service.jdbc.QueryExecutor.closeDatabaseConnection(Connection
	 * connection)}
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCloseDatabaseConnection() throws SQLException {

		Connection connection = ConnectionManager.getDatabaseConnection("src/test/resources");

		ConnectionManager.closeDatabaseConnection(connection);

		assertTrue("connection is not closed", connection.isClosed());

	}

	/**
	 * This method is to test
	 * {@link com.app.service.jdbc.QueryExecutor.getDBProperties(String
	 * propertiesLocation)} 
	 */
	@Test
	public void testGetDBProperties() {

		Properties properties = ConnectionManager.loadDBProperties("src/test/resources");
		System.out.println(properties);

		assertNotNull("Empty properties", properties);
	}

	/**
	 * This method is to test
	 * {@link com.app.service.jdbc.QueryExecutor.getDatabaseConnection()}
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testGetDatabaseConnection() throws SQLException {

		Connection connection = ConnectionManager.getDatabaseConnection("src/test/resources");

		assertTrue("connection is not closed", connection != null);

	}

}
