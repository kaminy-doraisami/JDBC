/**
 * 
 */
package com.util.processor;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

/**
 * @author kaminydoraisami This class reads input csv file using CSVReader
 * 
 */
public class InputFileReader {

	/**
	 * Constructor
	 */
	public InputFileReader() {
	}

	/**
	 * This methods reads input file and populates into a List of String Array
	 * 
	 * @param pathName
	 *            path of input file
	 * @param fileName
	 *            name of input csv file
	 * @return inFileEntries List of String Array that contains the entire data
	 *         in the input file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> readInput(String pathName, String fileName) throws FileNotFoundException, IOException {

		List<String[]> inFileEntries = null;
		CSVReader reader = null;
		try {
			reader = new CSVReader(new FileReader(pathName + java.io.File.separator + fileName));

			// populate entries of input file
			// into a List of String array
			inFileEntries = reader.readAll();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return inFileEntries;
	}

	/**
	 * This method reads the given sql file and returns the query read as a
	 * string
	 * 
	 * @param location
	 *            Location of the sql file
	 * @param fileName
	 *            Name of the sql file
	 * @return the query read from the sql file
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static String readSqlFile(String location, String fileName) throws FileNotFoundException, IOException {

		BufferedReader bufferedReader = null;
		StringBuilder sb = null;

		try {
			bufferedReader = new BufferedReader(new FileReader(location + java.io.File.separator + fileName));
			sb = new StringBuilder();
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null)
				bufferedReader.close();
		}

		return sb + "";

	}

}
