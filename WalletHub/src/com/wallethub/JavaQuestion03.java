package com.wallethub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.StringTokenizer;

public class JavaQuestion03 {

	public static final int listSize = 100000;

	// change this parameter for other countries. Recommended UTF-8
	public static final String charset = "ISO-8859-1";
	public static final String originalFile = "C:\\temp\\test numbers.txt";
	public static final String auxFile = "C:\\temp\\result.txt";
	public static final String stringSeparator = "|";

	public static void main(String[] args) {
		try {
			new JavaQuestion03().findMostFrequent(originalFile);
			// new JavaQuestion03().test();
		} catch (Exception e) {
			System.out.println("Sorry, but something went wrong");
		}
	}

	public List<String> findMostFrequent(final String fileName) throws Exception {
		try (BufferedReader oReader = Files.newBufferedReader(Paths.get(originalFile),
				Charset.forName(JavaQuestion03.charset))) {
			while (oReader.ready()) {
				String line = oReader.readLine();
				String[] linePhrases = line.split("\\|");
				for (String oPhrase : linePhrases) {
					this.writeToAuxFile(oPhrase);
				}
			}
			oReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return null;
	}

	public void writeToAuxFile(String oPhrase) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(auxFile, true))) {
			try (BufferedReader br = new BufferedReader(new FileReader(auxFile))) {
				while (br.ready()) {
					String line = br.readLine();
					String phrase = line.substring(0, line.indexOf(stringSeparator));
					Integer occur = Integer.valueOf(line.substring(line.indexOf(stringSeparator) + 1, line.length()));
					if (oPhrase.equals(phrase)) {
						// increment occurrance
						occur++; 
						
						bw.write(phrase + stringSeparator + occur.toString() );
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				System.out.println("File was not found!");
			} catch (IOException e) {
				System.out.println("No file found!");
			}
			bw.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error1!");
		} catch (IOException e) {
			System.out.println("Error2!");
		}
	}

}
