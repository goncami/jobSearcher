package com.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.exception.RevisionLaboralException;

/**
 * Utility class to works with csv file.
 * 
 * @author gon
 * @since 1.1
 *
 */
public class UtilRevisionCsv {

	/**
	 * Reads the csv file and returns a list with the values.
	 * 
	 * @param csvFile
	 * @return
	 * @throws RevisionLaboralException
	 */
	public static ArrayList<String> readCsv(String csvFile) throws RevisionLaboralException {
		String line = "";
		ArrayList<String> csvWordsList = new ArrayList<>();
		System.out.println("current dir = " + System.getProperty("user.dir"));
		
		try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
			while ((line = br.readLine()) != null)
				csvWordsList.add(line); 
		} catch (IOException e) {
			e.printStackTrace();
			throw new RevisionLaboralException(e.getMessage());
		}
		
		return csvWordsList;
	}
}
