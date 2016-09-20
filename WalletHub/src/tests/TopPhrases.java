/**
 * 
 * In order to test TopPhrases please run the JUnit test class below
 * The expected result are at the bottom of this file.
 *  
*/


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
		StringBuilder st = new StringBuilder();
		for (int i = 0; i < 20; i++)
			st.append(
					"Olympics 2012 | PG012 | PG012 | PG012 | PGA | FoA | FoA | FoA | Foobar Candy | CNar Candy | CNar Candy | CNar Candy | CNET | MicroET | MicroET | MicroET | MicroET | Microsoft Bing")
					.append(System.getProperty("line.separator")).toString().getBytes();
		for (int i = 0; i < 20; i++)
			st.append(" PGA | FoobA | FoobA | FoobA | Foobar Candy | Olyndy | Olyndy | Olyndy | Olympics 2012").append(System.getProperty("line.separator")).toString().getBytes();
		for (int i = 0; i < 40; i++)
			st.append("Foobar Candy | Olympics 2012 ").append(System.getProperty("line.separator")).toString().getBytes();
		for (int i = 0; i < 200; i++)
			st.append("Foobar Candy ").append(System.getProperty("line.separator")).toString().getBytes();
		fos.write(st.toString().getBytes());
		fos.close();

		// set initial time
		Calendar b = Calendar.getInstance();
		b.setTime(new Date());

		//run method
		answers.TopPhrases top = new answers.TopPhrases(originalFile, resultFile);
		top.rank();

		//set final time
		Calendar e = Calendar.getInstance();
		e.setTime(new Date());

		//print time diff in seconds
		System.out.println( (e.getTimeInMillis() - b.getTimeInMillis()) / 1000 );

		assertTrue(top.makeStringFromResultFile().contains("Foobar Candy|284"));

		top.writeResultFile();
	}

}

/*
This is the expected result from the JUnit test class above:

.........100
..........200
..........300
..........400
..........500
..........600
..........700
..........800
...done.
<number>
Foobar Candy|284                                                                                                  
Olyndy|90                                                                                                    
FoobA|88                                                                                                    
MicroET|86                                                                                                   
Olympics 2012|80                                                                                                   
CNar Candy|64                                                                                                   
FoA|62                                                                                                   
PG012|60                                                                                                   
PGA|42                                                                                                   
Microsoft Bing|29                                                                                                    
CNET|26                                                                                                   
 */
