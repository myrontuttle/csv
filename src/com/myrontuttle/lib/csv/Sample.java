package com.myrontuttle.lib.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class Sample {

	private static final String SAMPLE_FILE = "/resources";

	public static final String[] HEADER = { "COLUMN ONE", "COLUMN TWO" };
	
	private ArrayList<String> samples;
	
	public ArrayList<String> fillSampleList() {
		ArrayList<String> sampleList = null;

		try {
			sampleList = new ArrayList<String>();

			// Read the members from their files and put them into the model
			CSVFileReader csv = new CSVFileReader(new BufferedReader(
					new FileReader(new File(SAMPLE_FILE))), ',', '"');

			Vector<String> fields;
			int lineNumber = 1;
			try {
				fields = csv.readFields();

				while (fields != null) {
					if (lineNumber != 1) {
						
						/*
						Object object = new Object();
						object.setIndex(lineNumber - 1);
						
						object.setModel(fields.get(0));

						sampleList.add(object);
						*/
					}
					fields = csv.readFields();
					lineNumber++;
				}
			} finally {
				csv.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return sampleList;
	}

	/**
	 * Change the contents of text file in its entirety, overwriting any
	 * existing text.
	 * 
	 * This style of implementation throws all exceptions to the caller.
	 * 
	 * @param aFile
	 *            is an existing file which can be written to.
	 * @throws IllegalArgumentException
	 *             if param does not comply.
	 * @throws FileNotFoundException
	 *             if the file does not exist.
	 * @throws IOException
	 *             if problem encountered during write.
	 */
	public void saveSamples() throws FileNotFoundException,
			IOException {
		ArrayList<String> sampleList = null;

		if (samples != null) {
			sampleList = samples;
		}

		File aFile = new File(SAMPLE_FILE);

		if (!aFile.exists()) {
			throw new FileNotFoundException("File does not exist: " + aFile);
		}
		if (!aFile.isFile()) {
			throw new IllegalArgumentException("Should not be a directory: "
					+ aFile);
		}
		if (!aFile.canWrite()) {
			throw new IllegalArgumentException("File cannot be written: "
					+ aFile);
		}

		// use buffering
		CSVFileWriter output = new CSVFileWriter(SAMPLE_FILE, ',', '"');

		Vector<String> fields = null;

		try {
			// create vector for the header and add it to the output file
			fields = new Vector<String>(Arrays.asList(HEADER));
			output.writeFields(fields);

			for (String sample : sampleList) {
				fields = new Vector<String>(2);

				fields.add(sample);
				/*
				fields.add(auma.getModel());
				fields.add(HoistCreator.getInstance().getTwoPlaces().format(2.01));
				 */
				output.writeFields(fields);
			}

		} finally {
			output.close();
		}
	}
	
}
