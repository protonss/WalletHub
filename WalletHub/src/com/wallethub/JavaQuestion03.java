package com.wallethub;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Map;

public class JavaQuestion03 {

	public static final int listSize = 100000;

	// change this parameter for other countries. Recommended UTF-8
	public static final String charset = "ISO-8859-1";
	public static final String inputFile = "C:\\temp\\test numbers.txt";
	public static final String outputFile = "C:\\temp\\result.txt";
	public static final String stringSeparator = "|";

	public static void main(String[] args) {
		new JavaQuestion03().findMostFrequent(inputFile);
	}

	public List<String> findMostFrequent(final String fileName) {

		BufferedWriter ofWriter = null;
		BufferedWriter ifWriter = null;
		/*ofWriter = new BufferedWriter(new FileWriter(outputFile));*/

		try (BufferedReader ifReader = Files.newBufferedReader(Paths.get(inputFile), Charset.forName(JavaQuestion03.charset))) {
			while (ifReader.ready()) {
				String line = ifReader.readLine();
				String[] linePhrases = line.split("\\|");
				for (String ifPhrase : linePhrases) {
					try (BufferedReader ofReader = Files.newBufferedReader(Paths.get(outputFile), Charset.forName(JavaQuestion03.charset))) {
						while (ofReader.ready()) {
							String ofPhrase = ofReader.readLine().split("\\|")[0];
							ofWriter = new BufferedWriter(new FileWriter(outputFile));
							if (ofPhrase.equals(ifPhrase)) { // increment
								Integer oc = Integer.valueOf(ofReader.readLine().split("\\|")[1]) + 1;
								ofWriter.write(ifPhrase + stringSeparator + oc.toString());
							} else { // create
								ofWriter.write(ifPhrase + stringSeparator + "1");
							}
							ofWriter.close();
						}
						ofReader.close();
					}
					ifWriter.write(String.format("%s%n"));
				}
			}
			ifReader.close();
		} catch (IOException e) {
			System.out.println("Please make sure you have the right txt file.");
		} catch (Exception e) {
			System.out.println("Sorry. Something went wrong in my File System");
		}
		return null;
	}

	private void writeIntoAuxFile(String phrase, Integer occurs) throws Exception {
		Path logFile = Paths.get(outputFile);
		try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardCharsets.ISO_8859_1, StandardOpenOption.APPEND, StandardOpenOption.WRITE)) {
			writer.append(phrase + stringSeparator + occurs.toString());
			writer.newLine();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception();
		}
	}

	public static <K, V> void printMap(Map<K, V> map) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			System.out.println(" Occourences : " + entry.getValue() + "  \t Phrase : " + entry.getKey());
		}
	}
}
