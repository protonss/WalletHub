package answers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TopPhrases {

	public static final int listSize = 100000;

	// change these parameter for your OS
	public static final String charset = "ISO-8859-1";

	private String originalFile;
	private String resultFile;

	public static final String stringSeparator = "|";

	public static final long RECORD_LENGTH = 100;
	public static final String EMPTY_STRING = " ";

	public TopPhrases(String originalFile, String resultFile) {
		File of = new File(originalFile);
		File rf = new File(resultFile);
		this.resultFile = of.getAbsolutePath().replace(of.getName(), "") + File.separator + rf.getName();
		this.originalFile = of.getAbsolutePath().replace(of.getName(), "") + File.separator + of.getName();
	}

	public void rank() {
		try {
			deleteTmpFiles();

			/* The three steps described in README file */
			createResultFile();
			sortResultFile();
			truncateResultFile();

			System.out.println("done.");
		} catch (Exception e) {
			System.out.println("Sorry, but something went wrong");
			e.printStackTrace();
		}
	}

	private void truncateResultFile() throws IOException {
		FileOutputStream tmpFile = new FileOutputStream("tmpFile");
		try (RandomAccessFile resulrFileRW = new RandomAccessFile(resultFile, "rwd")) {
			String line;
			int c = 1;
			while ((line = resulrFileRW.readLine()) != null) {
				if (c <= listSize) {
					tmpFile.write((line + System.getProperty("line.separator")).getBytes());
				}
				c++;
			}
			resulrFileRW.close();
			tmpFile.close();
		}
		Helper.renameFile("tmpFile", resultFile);
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

	private void createResultFile() throws Exception {
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

	private void writeToResultFile(String originalPhrase) throws FileNotFoundException, IOException {
		new File(resultFile).createNewFile();
		try (RandomAccessFile faFile = new RandomAccessFile(resultFile, "rwd")) {
			String line;
			Integer occur = 1;
			long previousPointer = faFile.getFilePointer();
			while ((line = faFile.readLine()) != null) {
				line = line.trim();
				String exportedPhrase = Helper.getFirstElement(line);
				occur = Integer.valueOf(Helper.getSecondElement(line));
				if (originalPhrase.equals(exportedPhrase)) {
					occur++;
					faFile.seek(previousPointer);
					writeLine(faFile, originalPhrase, occur.toString());
					return;
				} else
					previousPointer = faFile.getFilePointer();
			}
			faFile.seek(previousPointer);
			this.newLine(faFile, originalPhrase, occur, previousPointer);
			faFile.close();
		}
	}

	private void writeLine(RandomAccessFile raf, String phrase, String occur) throws IOException {
		if (phrase.length() <= 0)
			return;
		String newLine = phrase + stringSeparator + occur.toString();
		raf.writeBytes(newLine);
		Helper.printCounter();
	}

	private void newLine(RandomAccessFile raf, String originalPhrase, Integer occur, long previousPointer) throws IOException {
		this.writeLine(raf, originalPhrase, paddingRight(String.valueOf(occur)) + System.getProperty("line.separator").toString());
	}

	private String paddingRight(String source) {
		if (source != null) {
			for (int i = 0; i < RECORD_LENGTH; i++) {
				source += EMPTY_STRING;
			}
		}
		return source;
	}

	public void writeResultFile() {
		try {
			Helper.writeFile(resultFile);
		} catch (IOException e) {
			System.out.println("Could not print result file");
		}
	}

	public String makeStringFromResultFile() {
		String st = "";
		try {
			st = Helper.makeStringFromFile(resultFile);
		} catch (IOException e) {
			System.out.println("Could not turn the result file into a string");
		}
		return st;
	}

}

class Helper {

	private static int count = 1;

	public static String getSecondElement(String line) {
		return line.substring(line.indexOf(TopPhrases.stringSeparator) + 1, line.length());
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

	public static String makeStringFromFile(String file) throws IOException {
		StringBuilder st = new StringBuilder();
		try (BufferedReader oReader = Files.newBufferedReader(Paths.get(file), Charset.forName(TopPhrases.charset))) {
			while (oReader.ready()) {
				st.append(oReader.readLine());
			}
			oReader.close();
		}
		return st.toString();
	}

	public static void renameFile(String oldName, String newName) {
		File file1 = new File(oldName);
		File file2 = new File(newName);
		file2.delete();
		file1.renameTo(file2);
	}

}
