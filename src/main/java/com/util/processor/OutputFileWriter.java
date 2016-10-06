/**
 * 
 */
package com.util.processor;

import au.com.bytecode.opencsv.CSVWriter;

import java.io.FileWriter;
import java.util.List;
import java.io.IOException;

/**
 * @author kaminydoraisami This class writes an csv file from a List of String
 *         Array
 * 
 */
public class OutputFileWriter {

	/**
	 * Constructor
	 */
	public OutputFileWriter() {
	}

	/**
	 * This method writes the output file
	 * INFO: If the param outFileEntries is null, an empty file is still written
	 * 
	 * @param outFileEntries
	 * @param pathName
	 * @param fileName
	 * @throws IOException
	 */
	public static void writeOutput(List<String[]> outFileEntries,
			String pathName, String fileName) throws IOException {

		CSVWriter writer = null;
		try {
			writer = new CSVWriter(new FileWriter(pathName
					+ java.io.File.separator + fileName));
			if (outFileEntries != null)
				writer.writeAll(outFileEntries);
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (writer != null)
				writer.close();
		}
	}

}
