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

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import answers.TopPhrases;

public class Lab01 {

	public static final String CHARSET = "ISO-8859-1";
	public static final String SOURCE_FILE = "C:\\tmp\\source.txt";
	public static final int KB = 1024;
	public static final int MB = KB * 1024;
	public static int UNIT = KB * 64;
	public static float TAMANHO = 200 * MB;

	private static int COUNT = 1;

	public static void main(String[] args) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Lab01 l = new Lab01();

		l.comparar(l);

	}

	public void comparar(Lab01 l) {
		for (int i = 1; i <= 80; i++) {
			System.out.printf("%n%s File Size: %s MB   Unit: %s KB%n", i, TAMANHO / MB, UNIT / KB);
			l.createSource();
			l.readApacheCommonsIo();
			//l.readBufferedReaderClass();
			//l.readWithFileInputStreamClass();
			//l.readWithRandomAccessFile();

			TAMANHO += 8 * MB;
			// UNIT += 8 * KB;
		}
	}

	private void readApacheCommonsIo() {
		Date antes = new Date();
		LineIterator it;
		try {
			it = FileUtils.lineIterator(new File(SOURCE_FILE), "UTF-8");
			try {
				while (it.hasNext()) {
					String line = it.next();
				}
			} finally {
				LineIterator.closeQuietly(it);
			}
			it.close();
			displayTimeDiff(antes, new Date(), "Apache Commons Io");
		} catch (IOException e) {
			System.out.println("Apache Commons IO deu pau . " + e.getMessage());
		}
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

	private void createSource() {
		File f = new File(SOURCE_FILE);
		// if (!f.exists() || f.length() != TAMANHO) {
		this.createSourceFile();
		// }
	}

	private void createSourceFile() {
		Date antes = new Date();
		try {
			new File(SOURCE_FILE).createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try (FileOutputStream fos = new FileOutputStream(new File(SOURCE_FILE))) {
			byte[] bs = new byte[UNIT];
			for (int i = 0; i < TAMANHO / UNIT; i++) {
				fos.write(bs);
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
