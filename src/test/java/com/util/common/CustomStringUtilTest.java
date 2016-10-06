/**
 * 
 */
package com.util.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author kaminydoraisami
 * 
 */
public class CustomStringUtilTest {

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
	 * This method is to {@link
	 * com.util.common.CustomStringUtil.convertToString(List<Map<String,
	 * Object>> resultSet)}
	 */
	@Test
	public void testConvertToString() {

		List<Map<String, Object>> argMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("surname", "Clyne");
		row1.put("firstname", "Cameron");
		argMap.add(row1);

		Map<String, Object> row2 = new HashMap<String, Object>();
		row2.put("surname", "Tiemann");
		row2.put("firstname", "Gail");
		argMap.add(row2);

		String result = "";
		result = CustomStringUtil.convertToString(argMap);

		StringBuffer expected = new StringBuffer();
		expected.append("firstname;Cameron");
		expected.append("\n");
		expected.append("surname;Clyne");
		expected.append("\n");
		expected.append("<>");
		expected.append("\n");
		expected.append("firstname;Gail");
		expected.append("\n");
		expected.append("surname;Tiemann");
		expected.append("\n");
		expected.append("<>");
		expected.append("\n");

		assertEquals(expected.toString(), result);
	}

	/**
	 * This method is to {@link
	 * com.util.common.CustomStringUtil.convertToString(List<Map<String,
	 * Object>> resultSet)} for null value
	 */
	@Test
	public void testConvertToStringForNull() {

		String result = CustomStringUtil.convertToString(null);

		assertTrue(result.isEmpty());
	}

	/**
	 * This method is to {@link
	 * com.util.common.CustomStringUtil.convertToString(List<Map<String,
	 * Object>> resultSet)} for an empty map
	 */
	@Test
	public void testConvertToStringForEmptyMap() {

		List<Map<String, Object>> argMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put("", "");
		row1.put("", "");
		argMap.add(row1);
		
		Map<String, Object> row2 = new HashMap<String, Object>();
		row2.put("", "");
		row2.put("", "");
		argMap.add(row2);
		

		String result = "";
		result = CustomStringUtil.convertToString(argMap);
		
		StringBuffer expected = new StringBuffer();
		expected.append(";");
		expected.append("\n");		
		expected.append("<>");
		expected.append("\n");
		expected.append(";");
		expected.append("\n");		
		expected.append("<>");
		expected.append("\n");
		
		assertEquals(expected.toString(), result);
	}
	
	/**
	 * This method is to {@link
	 * com.util.common.CustomStringUtil.convertToString(List<Map<String,
	 * Object>> resultSet)} for a map containing all null values
	 */
	@Test
	public void testConvertToStringForNullMap() {
		
		List<Map<String, Object>> argMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put(null, null);
		row1.put(null, null);
		argMap.add(row1);

		String result = "";
		result = CustomStringUtil.convertToString(argMap);
		
		StringBuffer expected = new StringBuffer();
		expected.append(null + ";" + null);
		expected.append("\n");		
		expected.append("<>");
		expected.append("\n");

		assertEquals(expected.toString(), result);
	}

	/**
	 * This method is to {@link
	 * com.util.common.CustomStringUtil.convertToString(List<Map<String,
	 * Object>> resultSet)} for a map containing some null values
	 */
	@Test
	public void testConvertToStringNullKeyMap() {
		
		List<Map<String, Object>> argMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> row1 = new HashMap<String, Object>();
		row1.put(null, "valueOfNullCol1");
		row1.put(null, "valueOfNullCol2");
		row1.put(null, "valueOfNullCol3");

		argMap.add(row1);

		String result = "";
		result = CustomStringUtil.convertToString(argMap);
		
		StringBuffer expected = new StringBuffer();
		
		expected.append(null + ";" + "valueOfNullCol3");
		expected.append("\n");		
		expected.append("<>");
		expected.append("\n");
	
		assertEquals(expected.toString(), result);
	}

}
