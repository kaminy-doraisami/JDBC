/**
 * 
 */
package com.util.common;

import java.util.List;
import java.util.Map;

/**
 * @author kaminydoraisami This class is to convert a List of Map to String
 * 
 */
public class CustomStringUtil {

	/**
	 * Constructor
	 */
	public CustomStringUtil() {

	}

	/**
	 * This method converts the result set into a String object
	 * 
	 * @param resultSet
	 * @return
	 */
	public static String convertToString(List<Map<String, Object>> resultSet) {

		StringBuffer strResult = new StringBuffer();
		if (resultSet != null) {
			for (Map<String, Object> result : resultSet) {
				for (String columnName : result.keySet()) {
					strResult.append(columnName);
					strResult.append(";");
					strResult.append(result.get(columnName));
					strResult.append("\n");
				}
				strResult.append("<>");
				strResult.append("\n");
			}
		}
		return "" + strResult;

	}

}
