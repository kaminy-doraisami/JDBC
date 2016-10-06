/**
 * 
 */
package com.util.processor;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.app.service.jdbc.ConnectionManager;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.io.File;

/**
 * @author kaminydoraisami
 * 
 */
public class FileProcessorTest {

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
	 * {@link com.util.processor.FileProcessor.processFile(String inputPath,
	 * String inputFileName, String outputPath, String outputFileName)}
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void testProcessFile() throws FileNotFoundException, IOException, SQLException {

		FileProcessor fp = new FileProcessor();

		fp.processFile("src/test/resources", "TestInput.csv", "src/test/resources", "TestOutput.csv",
				"src/test/resources");
		List<String[]> expectedOutput = InputFileReader.readInput("src/test/resources", "ExpectedOutput.csv");
		List<String[]> outFileEntries = InputFileReader.readInput("src/test/resources", "TestOutput.csv");

		boolean entriesEqual = false;

		// Compare each column row by row in the expected output file and actual
		// output file
		for (int i = 0; i < 2; i++) {
			for (int col = 0; col < 5; col++) {
				entriesEqual = expectedOutput.get(i)[col].equals(outFileEntries.get(i)[col]);
			}
		}

		assertTrue("Output not as expected", entriesEqual == true);
	}

	/**
	 * This method is to test
	 * {@link com.util.processor.FileProcessor.processFile(String inputPath,
	 * String inputFileName, String outputPath, String outputFileName)} for an
	 * input file that is not present
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void testProcessFileForNoInputFile() throws FileNotFoundException, IOException, SQLException {

		FileProcessor fp = new FileProcessor();

		fp.processFile("src/test/resources", "NoSuchInput.csv", "src/test/resources", "NoOutputWritten.csv",
				"src/main/resources");

		assertFalse("Output file is not null", new File("src/test/resources/NoOutputWritten.csv").isFile());
	}

	/**
	 * This method is to test
	 * {@link com.util.processor.FileProcessor.processFile(String inputPath,
	 * String inputFileName, String outputPath, String outputFileName)} for an
	 * empty input file
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@Test
	public void testProcessFileForEmptyInputFile() throws FileNotFoundException, IOException, SQLException {

		FileProcessor fp = new FileProcessor();

		fp.processFile("src/test/resources", "EmptyInput.csv", "src/test/resources", "ENoOutputWritten.csv",
				"src/main/resources");

		assertFalse("Output file is not null", new File("src/test/resources/NoOutputWritten.csv").isFile());
	}

	/**
	 * This to test {@link com.util.processor.FileProcessor.processEntries(List
	 * <String[]> inFileEntries, String inputPath, Connection connection)}
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void testProcessEntries() throws FileNotFoundException, IOException, SQLException {

		Connection connection = ConnectionManager.getDatabaseConnection("src/test/resources");

		String[] line1 = { "1", "DCOutbound", "#ValidSqlFile.sql", "DCInbound", "#ValidSQLFile.sql" };
		List<String[]> inList = new ArrayList<String[]>();
		inList.add(line1);

		FileProcessor fp = new FileProcessor();

		List<String[]> outlist = fp.processEntries(inList, "src/test/resources", connection);

		assertFalse(outlist.isEmpty());
	}

	/**
	 * This to test {@link com.util.processor.FileProcessor.processEntries(List
	 * <String[]> inFileEntries, String inputPath, Connection connection)} for
	 * empty sql files
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void testProcessEntriesForEmptySQL() throws FileNotFoundException, IOException, SQLException {

		Connection connection = ConnectionManager.getDatabaseConnection("src/test/resources");

		String[] line1 = { "1", "DCOutbound", "#EmptySqlFile.sql", "DCInbound", "#EmptySQLFile.sql" };
		List<String[]> inList = new ArrayList<String[]>();
		inList.add(line1);

		FileProcessor fp = new FileProcessor();

		List<String[]> outlist = fp.processEntries(inList, "src/test/resources", connection);

		assertFalse(outlist.isEmpty());
	}

	/**
	 * This to test {@link com.util.processor.FileProcessor.processEntries(List
	 * <String[]> inFileEntries, String inputPath, Connection connection)} for
	 * invalid entries
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void testProcessEntriesForInvalidEntries() throws FileNotFoundException, IOException, SQLException {

		Connection connection = ConnectionManager.getDatabaseConnection("src/test/resources");

		String[] line1 = { "test", "Invalid", "#InvalidSqlFile.sql" };
		String[] line2 = { "hiud", "oenu", "nsidso" };

		List<String[]> inList = new ArrayList<String[]>();
		inList.add(line1);
		inList.add(line2);

		FileProcessor fp = new FileProcessor();

		List<String[]> outlist = fp.processEntries(inList, "src/test/resources", connection);

		assertFalse(outlist.isEmpty());
	}

	/**
	 * This to test {@link com.util.processor.FileProcessor.processEntries(List
	 * <String[]> inFileEntries, String inputPath, Connection connection)} for a
	 * sql file that is not present
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws SQLException
	 */
	@Test
	public void testProcessEntriesForNoSqlFile() throws FileNotFoundException, IOException, SQLException {

		Connection connection = ConnectionManager.getDatabaseConnection("src/test/resources");

		String[] line1 = { "test", "Invalid", "#NoSuchSqlFile.sql" };

		List<String[]> inList = new ArrayList<String[]>();
		inList.add(line1);

		FileProcessor fp = new FileProcessor();

		List<String[]> outlist = fp.processEntries(inList, "src/test/resources", connection);

		assertFalse(outlist.isEmpty());
	}
}
