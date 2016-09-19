package tests;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;

import junit.framework.TestCase;

public class TopPhrases extends TestCase {

	public TopPhrases() {
		// TODO Auto-generated constructor stub
	}

	public void test_type() throws Exception {
		assertNotNull(answers.TopPhrases.class);
	}

	public void test_rank() throws Exception {
		// Creating the expected
		String originalFile = "C:\\temp\\source.txt";// change this for your OS
		String resultFile = "C:\\temp\\result.txt";// change this for your OS

		File file = new File(originalFile);
		FileOutputStream fos = new FileOutputStream(file);
		for (int i = 0; i < 2; i++)
			fos.write(new StringBuilder()
					.append(
							"Olympics 2012 | PG012 | PG012 | PG012 | PGA | FoA | FoA | FoA | Foobar Candy | CNar Candy | CNar Candy | CNar Candy | CNET | MicroET | MicroET | MicroET | MicroET | Microsoft Bing")
					.append(System.getProperty("line.separator")).toString().getBytes());
		for (int i = 0; i < 2; i++)
			fos.write(
					new StringBuilder().append(" PGA | FoobA | FoobA | FoobA | Foobar Candy | Olyndy | Olyndy | Olyndy | Olympics 2012").append(System.getProperty("line.separator")).toString().getBytes());
		for (int i = 0; i < 4; i++)
			fos.write(new StringBuilder().append("Foobar Candy | Olympics 2012 ").append(System.getProperty("line.separator")).toString().getBytes());
		for (int i = 0; i < 20; i++)
			fos.write(new StringBuilder().append("Foobar Candy ").append(System.getProperty("line.separator")).toString().getBytes());
		fos.close();

		// run method
		Calendar b = Calendar.getInstance();
		b.setTime(new Date());

		answers.TopPhrases top = new answers.TopPhrases(originalFile, resultFile);
		top.rank();

		Calendar e = Calendar.getInstance();
		e.setTime(new Date());

		long diff = (e.getTimeInMillis() - b.getTimeInMillis()) / 1000;
		System.out.println(diff);
	}

}