package answers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TopPhrases {

	public static final int listSize = 3;

	// change these parameter for your OS
	public static final String charset = "ISO-8859-1";

	private String originalFile;
	private String resultFile;

	public static final String stringSeparator = "|";
	public static int id = 1;

	public static final long RECORD_LENGTH = 10;
	public static final String EMPTY_STRING = " ";

	public TopPhrases(String originalFile, String resultFile) {
		this.originalFile = originalFile;
		File f = new File(originalFile);
		this.resultFile = f.getAbsolutePath().replace(f.getName(), "") + File.separator + "resultFile.txt";
	}

	public void rank() {
		try {
			deleteTmpFiles();

			createResultFile();
			sortResultFile();
			truncateResultFile(); 

			System.out.println("done.");
		} catch (Exception e) {
			System.out.println("Sorry, but something went wrong");
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private void truncateResultFile() throws IOException {
		try (RandomAccessFile resulrFileRW = new RandomAccessFile(resultFile, "rwd")) {
			String line;
			int c = 0;
			while ((line = resulrFileRW.readLine()) != null) {
				if (c <= listSize)
					resulrFileRW.getChannel().truncate(resulrFileRW.read());
				c++;
			}
			resulrFileRW.close();
		}
	}

	private void deleteTmpFiles() {
		Helper.deleteFile(this.resultFile);
	}

	private void sortResultFile() throws IOException {
		try (RandomAccessFile secFileRW = new RandomAccessFile(resultFile, "rwd")) {
			String line1;
			boolean again = false;
			long p1 = secFileRW.getFilePointer();
			while ((line1 = secFileRW.readLine()) != null) {
				long p2 = secFileRW.getFilePointer();
				String line2 = secFileRW.readLine();
				if (line2 == null || line1.equals(""))
					break;
				int rank1 = Integer.valueOf(Helper.getSecondElement(line1).trim());
				int rank2 = Integer.valueOf(Helper.getSecondElement(line2).trim());
				if (rank2 > rank1) {
					secFileRW.seek(p1);
					secFileRW.writeBytes(line2 + System.getProperty("line.separator") + line1);
					again = true;
				} else {
					secFileRW.seek(p2);
				}
				p1 = p2;
			}
			if (again)
				this.sortResultFile();
			secFileRW.close();
		}
	}

	public void createResultFile() throws Exception {
		try (BufferedReader originalFileReader = Files.newBufferedReader(Paths.get(originalFile), Charset.forName(TopPhrases.charset))) {
			while (originalFileReader.ready()) {
				String line = originalFileReader.readLine();
				String[] linePhrases = line.split("\\|");
				for (String originalPhrase : linePhrases) {
					this.writeToResultFile(originalPhrase.trim());
				}
			}
			originalFileReader.close();
		}
	}

	public void writeToResultFile(String originalPhrase) throws FileNotFoundException, IOException {
		new File(resultFile).createNewFile();
		try (RandomAccessFile faFile = new RandomAccessFile(resultFile, "rwd")) {
			String line;
			Integer occur = 1;
			long previousPointer = faFile.getFilePointer();
			while ((line = faFile.readLine()) != null) {
				line = line.trim();
				String exportedPhrase = Helper.getFirstElement(line);
				int id = Integer.valueOf(Helper.getThirdElement(line));
				occur = Integer.valueOf(Helper.getSecondElement(line));
				if (originalPhrase.equals(exportedPhrase)) {
					occur++;
					faFile.seek(previousPointer);
					writeLine(faFile, originalPhrase, occur, String.valueOf(id));
					return;
				} else
					previousPointer = faFile.getFilePointer();
			}
			faFile.seek(previousPointer);
			this.newLine(faFile, originalPhrase, occur, previousPointer);
			faFile.close();
		}
	}

	private void writeLine(RandomAccessFile raf, String phrase, Integer occur, String id) throws IOException {
		if (phrase.length() <= 0)
			return;
		String newLine = phrase + stringSeparator + occur.toString() + stringSeparator + id;
		raf.writeBytes(newLine);
		Helper.printCounter();
	}

	private void newLine(RandomAccessFile raf, String originalPhrase, Integer occur, long previousPointer) throws IOException {
		this.writeLine(raf, originalPhrase, occur, paddingRight(String.valueOf(id++)) + System.getProperty("line.separator").toString());
	}

	private String paddingRight(String source) {
		StringBuilder result = new StringBuilder();
		if (source != null) {
			result.append(source);
			for (int i = 0; i < RECORD_LENGTH - source.length(); i++) {
				result.append(EMPTY_STRING);
			}
		}
		return result.toString();
	}

}

class Helper {

	private static int count = 1;

	public static String getSecondElement(String line) {
		return line.substring(line.indexOf(TopPhrases.stringSeparator) + 1, line.lastIndexOf(TopPhrases.stringSeparator));
	}

	public static String getThirdElement(String line) {
		return line.substring(line.lastIndexOf(TopPhrases.stringSeparator) + 1, line.length());
	}

	public static String getFirstElement(String line) {
		return line.substring(0, line.indexOf(TopPhrases.stringSeparator));
	}

	public static void deleteFile(String fileName) {
		new File(fileName).delete();
	}

	public static void printCounter() {
		if (++count % 100 == 0)
			System.out.println(count);
		if (count % 10 == 0)
			System.out.print(".");
	}

	public static void writeFile(String file) throws IOException {
		try (BufferedReader oReader = Files.newBufferedReader(Paths.get(file), Charset.forName(TopPhrases.charset))) {
			while (oReader.ready()) {
				String line = oReader.readLine();
				System.out.println(line);
			}
			oReader.close();
		}
	}

}
