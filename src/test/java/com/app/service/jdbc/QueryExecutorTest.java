/**
 * 
 */
package com.app.service.jdbc;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

/**
 * @author kaminydoraisami
 * 
 */
public class QueryExecutorTest {

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
	 * {@link com.app.service.jdbc.QueryExecutor.closeResultSet(ResultSet
	 * resultSet)}
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testCloseResultSet() throws SQLException {

		System.out.println("Test for closeResultSet()");
		System.out.println("==========================");

		Connection connection = ConnectionManager.getDatabaseConnection("src/main/resources");

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT student_id, student_name FROM workspace.student limit 3");

		while (resultSet.next()) {
			System.out.println(resultSet.getString("student_id") + " " + resultSet.getString("student_name"));
		}

		QueryExecutor.closeResultSet(resultSet);
		ConnectionManager.closeDatabaseConnection(connection);

		assertTrue("Result Set is not closed", resultSet.isClosed());

	}

	/**
	 * This method is to test
	 * {@link com.app.service.jdbc.QueryExecutor.closeResultSet(ResultSet
	 * resultSet)}
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testClosePreparedStatement() throws SQLException {

		System.out.println("Test for closePreparedStatement()");
		System.out.println("=================================");

		Connection connection = ConnectionManager.getDatabaseConnection("src/main/resources");

		String query = "SELECT student_id, student_name FROM workspace.student limit ?";

		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, 7);

		ResultSet resultSet = preparedStatement.executeQuery();

		while (resultSet.next()) {
			System.out.println(resultSet.getString("student_id") + " " + resultSet.getString("student_name"));
		}

		QueryExecutor.closePreparedStatement(preparedStatement);
		QueryExecutor.closeResultSet(resultSet);
		ConnectionManager.closeDatabaseConnection(connection);

		assertTrue("Prepared Statement is not closed", preparedStatement.isClosed());

	}

	/**
	 * This method is to test
	 * {@link com.app.service.jdbc.QueryExecutor.IterateResultSet(ResultSet
	 * resultSet)}
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testIterateResultSet() throws SQLException {

		System.out.println("Test for iterateResultSet()");
		System.out.println("===========================");

		Connection connection = ConnectionManager.getDatabaseConnection("src/main/resources");

		Statement statement = connection.createStatement();
		ResultSet resultSet = statement.executeQuery("SELECT student_id, student_name FROM workspace.student limit 2");

		List<Map<String, Object>> expectedMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> expectedRow1 = new HashMap<String, Object>();
		expectedRow1.put("student_name", "John");
		expectedRow1.put("student_id", 1);
		expectedMap.add(expectedRow1);

		Map<String, Object> expectedRow2 = new HashMap<String, Object>();
		expectedRow2.put("student_name", "Peter");
		expectedRow2.put("student_id", 2);
		expectedMap.add(expectedRow2);

		// validate the resultMap
		List<Map<String, Object>> resultMap = QueryExecutor.iterateResultSet(resultSet);

		QueryExecutor.closeResultSet(resultSet);
		ConnectionManager.closeDatabaseConnection(connection);

		assertEquals("Result Map not as expected", expectedMap, resultMap);

	}

	/**
	 * This method is to test
	 * {@link com.app.service.jdbc.QueryExecutor.executeQuery(String query, int
	 * limit, Connection connection}
	 * 
	 * @throws SQLException
	 */
	@Test
	public void testExecuteQuery() throws SQLException {

		System.out.println("Test for executeQuery()");
		System.out.println("========================");

		Connection connection = ConnectionManager.getDatabaseConnection("src/main/resources");

		String query = "SELECT student_id, student_name FROM workspace.student limit ?";

		List<Map<String, Object>> expectedMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> expectedRow = new HashMap<String, Object>();
		expectedRow.put("student_name", "John");
		expectedRow.put("student_id", 1);
		expectedMap.add(expectedRow);

		List<Map<String, Object>> resultMap = QueryExecutor.executeQuery(query, 1, connection);

		ConnectionManager.closeDatabaseConnection(connection);

		assertEquals("Result not as expected", expectedMap, resultMap);
	}

}
