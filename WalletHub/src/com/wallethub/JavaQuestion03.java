package com.wallethub;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

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
		} catch (Exception e) {
			System.out.println("Sorry, but something went wrong");
			e.printStackTrace();
		}
	}

	public List<String> findMostFrequent(final String fileName) throws Exception {
		try (BufferedReader oReader = Files.newBufferedReader(Paths.get(originalFile), Charset.forName(JavaQuestion03.charset))) {
			while (oReader.ready()) {
				String line = oReader.readLine();
				String[] linePhrases = line.split("\\|");
				for (String oPhrase : linePhrases) {
					this.writeToAuxFile(oPhrase);
				}
			}
			oReader.close();
		}
		return null;
	}

	public void writeToAuxFile(String oPhrase) throws FileNotFoundException, IOException {
		try (RandomAccessFile raf = new RandomAccessFile(auxFile, "rw")) {
			String line;
			Integer occur = 1;
			long pointer = raf.getFilePointer();
			while ((line = raf.readLine()) != null) {
				String phrase = line.substring(0, line.indexOf(stringSeparator));
				if (oPhrase.equals(phrase)) {
					System.out.print("line: ["+line+"] \t ");
					occur += Integer.valueOf(line.substring(line.indexOf(stringSeparator) + 1, line.length()));
					System.out.print("pointer: ["+pointer+"] \t ");
					raf.seek(pointer);
					String st = oPhrase + stringSeparator + occur.toString();
					this.write(raf, st);
					return;
				} else
					pointer = raf.getFilePointer();
			}
			String st = oPhrase + stringSeparator + occur.toString() + System.getProperty("line.separator").toString();
			this.write(raf, st);
			raf.close();
		}
	}

	private void write(RandomAccessFile raf, String st) throws IOException {
		raf.writeBytes(st);
		System.out.println("st: ["+st+"]");
	}

}
