package lab;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import answers.TopPhrases;

public class Lab01 {

	public static final String CHARSET = "ISO-8859-1";
	public static final String SOURCE_FILE = "C:\\temp\\source.txt";
	public static final float REPETITIONS = 200 * 1024 * 1024;

	private static int COUNT = 1;

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Lab01 l = new Lab01();
		l.createSourceFile();

		// l.readWithFilesClass();
		l.readWithFileInputStreamClass();
		//l.readWithRandomAccessFile();
		l.readBufferedReaderClass();
	}

	private void readBufferedReaderClass() {
		Date antes = new Date();
		try (BufferedReader originalFileReader = Files.newBufferedReader(Paths.get(SOURCE_FILE), Charset.forName(TopPhrases.charset))) {
			while (originalFileReader.ready()) {
				String line = originalFileReader.readLine();
			}
		} catch (Exception e) {
			System.out.println("Buffered Reader deu pau. " + e.getMessage());
		}
		displayTimeDiff(antes, new Date(), "Buffered Reader");
	}

	private void readWithRandomAccessFile() {
		Date antes = new Date();
		try (RandomAccessFile faFile = new RandomAccessFile(SOURCE_FILE, "r")) {
			@SuppressWarnings("unused")
			String line;
			while ((line = faFile.readLine()) != null) {
				line = null;
			}
		} catch (Exception e) {
			System.out.println("Random Access File deu pau. " + e.getMessage());
		}
		displayTimeDiff(antes, new Date(), "Random Access File");
	}

	private void readWithFileInputStreamClass() {
		Date antes = new Date();
		FileInputStream inputStream = null;
		Scanner sc = null;
		try {
			inputStream = new FileInputStream(SOURCE_FILE);
			sc = new Scanner(inputStream, "UTF-8");
			while (sc.hasNextLine()) {
				@SuppressWarnings("unused")
				String line = sc.nextLine();
			}
		} catch (FileNotFoundException e) {
			System.out.println("FileInputStream deu pau. " + e.getMessage());
		} finally {
			if (sc != null) {
				sc.close();
			}
		}
		displayTimeDiff(antes, new Date(), "File Input Stream");
	}

	private void createSourceFile() {
		Date antes = new Date();
		try (FileOutputStream fos = new FileOutputStream(new File(SOURCE_FILE))) {
			for (int i = 0; i < REPETITIONS * 8; i++) {
				fos.write(new StringBuilder().append("Foobar Candy ").append(System.getProperty("line.separator")).toString().getBytes());
				fos.flush();
			}
			fos.close();
		} catch (FileNotFoundException e) {
			System.out.println("FileOutputStream");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("FileOutputStream");
			e.printStackTrace();
		}
		displayTimeDiff(antes, new Date(), "Create Source File");
	}

	public static void displayTimeDiff(Date before, Date after, String msg) {
		Calendar t1 = Calendar.getInstance();
		t1.setTime(before);
		Calendar t2 = Calendar.getInstance();
		t2.setTime(after);
		long res = ((t2.getTimeInMillis() - t1.getTimeInMillis()) / 1);
		System.out.printf("%s \t %s%n", new DecimalFormat("###,###.###").format(res), msg);
	}

	public static void printCounter() {
		if (++COUNT % 100 == 0)
			System.out.println(COUNT);
		if (COUNT % 10 == 0)
			System.out.print(".");
	}

}
