/**
 * 
 */
package com.app.service.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kaminydoraisami
 * 
 *         This class is to execute a given query from the postgresql database.
 */
public class QueryExecutor {

	/**
	 * This method executes the given query
	 * 
	 * @param query
	 * @param limit
	 * @return the result of the query as a String
	 * @throws SQLException
	 */

	public static List<Map<String, Object>> executeQuery(String query, int limit, Connection connection)
			throws SQLException {

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Map<String, Object>> result = null;

		try {
			preparedStatement = connection.prepareStatement(query);

			preparedStatement.setInt(1, limit);

			resultSet = preparedStatement.executeQuery();

			result = iterateResultSet(resultSet);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		} finally {
			closeResultSet(resultSet);
			closePreparedStatement(preparedStatement);
		}
		return result;
	}

	/**
	 * This method iterates through the result set from the postgres table and
	 * returns the result set as a List of Map. Each row in the result set is
	 * seperated by <>
	 * 
	 * @param resultSet
	 * @return the query result as a List of Map
	 * @throws SQLException
	 */
	public static List<Map<String, Object>> iterateResultSet(ResultSet resultSet) throws SQLException {

		Map<String, Object> rowResult = new HashMap<String, Object>();
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		try {

			ResultSetMetaData metaData = resultSet.getMetaData();
			int numberOfColumns = metaData.getColumnCount();

			while (resultSet.next()) {
				// INFO: initializing for each row
				rowResult = new HashMap<String, Object>();
				for (int i = 1; i <= numberOfColumns; i++) {
					rowResult.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
				result.add(rowResult);
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return result;
	}

	/**
	 * This method is to close a result set
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public static void closeResultSet(ResultSet resultSet) throws SQLException {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * This method is to close a prepared statement
	 * 
	 * @param resultSet
	 * @throws SQLException
	 */
	public static void closePreparedStatement(PreparedStatement preparedStatement) throws SQLException {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
	}

}
