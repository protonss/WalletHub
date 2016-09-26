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

import org.apache.commons.io.FileSystemUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import answers.TopPhrases;

public class Lab02 {

	public static final String CHARSET = "ISO-8859-1";
	public static final String SOURCE_FILE = "C:\\temp\\source.txt";
	public static final int KB = 1024;
	public static final int MB = KB * 1024;
	public static int UNIT = 128 * KB;
	public static float TAMANHO = 264 * MB;

	private static int COUNT = 1;

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		Lab02 l = new Lab02();
		l.comparar(l);
	}

	public void comparar(Lab02 l) {
		for (int i = 1; i <= 80; i++) {
			System.out.printf("%n%s File Size: %s MB   Unit: %s KB%n", i, TAMANHO / MB, UNIT / KB);
			l.createSource();
			l.readApacheCommonsIo();
			// l.readBufferedReaderClass();
			// l.readWithFileInputStreamClass();
			// l.readWithRandomAccessFile();

			// TAMANHO *= 2;
			// UNIT /= 2;
		}
	}

	private void createSourceFile() {
		System.out.print("Create Source File");
		Date antes = new Date();

		new File(new File(SOURCE_FILE).getPath() + "/labtmp").mkdirs();

		displayTimeDiff(antes, new Date());
	}

	private void readApacheCommonsIo() {
		System.out.print("Apache Commons Io");
		Date antes = new Date();
		LineIterator it = null;
		
		try {
			Files.lines(Paths.get(SOURCE_FILE)).forEach(line->{
				line.length();
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		displayTimeDiff(antes, new Date());
	}

	private void readBufferedReaderClass() {
		System.out.print("Buffered Reader");
		Date antes = new Date();
		try (BufferedReader originalFileReader = Files.newBufferedReader(Paths.get(SOURCE_FILE),
				Charset.forName(TopPhrases.charset))) {
			while (originalFileReader.ready()) {
				String line = originalFileReader.readLine();
			}
		} catch (Exception e) {
			System.out.println("Buffered Reader deu pau. " + e.getMessage());
		}
		displayTimeDiff(antes, new Date());
	}

	private void readWithRandomAccessFile() {
		System.out.print("Random Access File");
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
		displayTimeDiff(antes, new Date());
	}

	private void readWithFileInputStreamClass() {
		System.out.print("File Input Stream");
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
		displayTimeDiff(antes, new Date());
	}

	private void createSource() {
		File f = new File(SOURCE_FILE);
		// if (!f.exists() || f.length() != TAMANHO) {
		this.createSourceFile();
		// }
	}

	public static void displayTimeDiff(Date before, Date after) {
		Calendar t1 = Calendar.getInstance();
		t1.setTime(before);
		Calendar t2 = Calendar.getInstance();
		t2.setTime(after);
		long res = ((t2.getTimeInMillis() - t1.getTimeInMillis()) / 1);
		System.out.printf(": \t\t %s mili segundos %n", new DecimalFormat("###,###.###").format(res));
	}

	public static void printCounter() {
		if (++COUNT % 100 == 0)
			System.out.println(COUNT);
		if (COUNT % 10 == 0)
			System.out.print(".");
	}

}
