package com.util.processor;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import com.app.service.jdbc.*;
import com.util.common.CustomStringUtil;

/**
 * @author kaminydoraisami This class reads the input file, processes it and
 *         writes the output file
 * 
 */
public class FileProcessor {

	/**
	 * Constructor
	 */
	public FileProcessor() {

	}

	/**
	 * This method iterates through the input file entries. For each row in the
	 * input file, it is checked if the column starts with '#'. If it does, the
	 * sql file name mentioned in that column is read and the query in that sql
	 * file is executed. The value in that column is replaced with the query
	 * result. The updated row is added to the List of String Array
	 * 'outFileEntries' which will eventually be written to the output file
	 * 
	 * @param inFileEntries
	 * @return outFileEntries
	 */

	public List<String[]> processEntries(List<String[]> inFileEntries, String inputPath, Connection connection)
			throws FileNotFoundException, IOException, SQLException {

		// List to hold output file entries
		List<String[]> outFileEntries = new ArrayList<String[]>();

		for (String[] row : inFileEntries) {
			for (int column = 0; column < row.length; column++) {
				if (row[column].startsWith("#")) {
					/*
					 * Read the query from the sql file mentioned in the column
					 * starting with '#'
					 */
					String query = InputFileReader.readSqlFile(inputPath, row[column].substring(1));

					/*
					 * Execute the query and replace the column with the query
					 * result INFO: In the query result, end of each row will be
					 * denoted by <>
					 */
					List<Map<String, Object>> cellValue = QueryExecutor.executeQuery(query, 1, connection);
					row[column] = CustomStringUtil.convertToString(cellValue);
				}
			}
			// Add the updated row to the List 'outFileEntries'
			outFileEntries.add(row);
		}
		return outFileEntries;
	}

	/**
	 * This method reads the input csv file, processes it and writes the output
	 * csv file. INFO: If the input file is empty or not present, no output file
	 * is written
	 */
	public void processFile(String inputPath, String inputFileName, String outputPath, String outputFileName,
			String dbPropertiesFilePath) throws FileNotFoundException, IOException, SQLException {

		List<String[]> outFileEntries = null;

		// Read input csv file into inFileEntries
		List<String[]> inFileEntries = InputFileReader.readInput(inputPath, inputFileName);

		if (inFileEntries != null && !(inFileEntries.isEmpty())) {
			// Get a connection for the database and pass it to process the
			// input file.
			Connection connection = ConnectionManager.getDatabaseConnection(dbPropertiesFilePath);
			outFileEntries = processEntries(inFileEntries, inputPath, connection);

			// Write output file
			OutputFileWriter.writeOutput(outFileEntries, outputPath, outputFileName);
			ConnectionManager.closeDatabaseConnection(connection);
		}
	}

}
