
This is the Anselmo Ribeiro's README file.

1) JAVA Questions:

1.a) In order to run all the java questions I imported the JUnit 4 library as described in Maven dependency below:
		<dependency>
			<groupId>junit</groupId>
			<artifactId>junit</artifactId>
			<version>4.12</version>
		</dependency>

1.b) Palindrome
 * I created two methods for basically two reasons 
 * 1) To make my code more easily testable 
 * 2) To ensure decoupling
 * 
 * I'm throwing Exception for null string in both methods because:
 * 1) I need to validate string size in isPalindrome method
 * 2) reverse method is supposed to be used elsewhere
 I created a  loop over the half string since the symmetrical elements switch places   
 
 
1.c) Complementary pairs
The findComplementary method itself, nothing fancy about it. 
The tricky part was testing it. 
It was easier and faster working with Integer classes to compare converted strings than comparing int pair by pair. 


1.d) Top Phrases
The point of this question is: THE FILE WON'T FIT IN MEMORY.
consequently, if I create an temp file based on the first one, it won't fit in memory either. 
I can't assume the temp file will be smaller enough to fit in memory.
So no matter how many files I handle, I have to work with a buffered version of them. 

So I decided to work with two files: origialFile and AuxFile and to divide this solution into three parts:
	a) Create the AuxFile 
	with lines in following format: 
	phrase|number of occurrences. Example: 
		Foobar Candy|23
		Microsoft Bing|45
	I'm using | as separator since the question stated the phrases don't contain |
	This file is supposed to be much smaller than the original one, depending on the number of repeated phrases.
	The bigger the number of repetitions the smaller the AuxFile.
	According to the Java RandomAccessFile library documentation, it is supposed to read from and write to the same file.
	This is the most time consuming task since I'm working with the original file (the hugest) and AuxFile (smaller)
	For every phrase in the originalFile I loop over the AuxFile. 
	
	c)Sort AuxFile
	From this point on, I don't use the original file anymore.
	I loop over AuxFile sorting it according to the number of occurrences.
	The result should look like:
		Microsoft Bing|45
		Foobar Candy|23
	Again, I'm reading from and writing to the same file. I use RandomAccessFile library again to do this.

	d)Truncate AuxFile 
	Inside a for loop up to 100000, I find the 100000th line and then truncate the file.
		
Comment: Maybe this issue deserves a deeper research. There might be another more elegant solution in BigData researches. 
Maybe using Operational System native methods/functions for this matter would work better and faster. And we can, off course, call those methods/functions from inside a Java application.
	 


2) MySQL Questions

2.a) Question 3.
If I could I would call you to make this question a little bit more clear . 
As far as I understood, the goal of this question was to transform a column into a row. 
And the row should look like: “3, white”, “3, Snow” ...
So that's what I did.

2.b) Question 4
@start_date and @end_date define the begin and end of the range of dates.
I resolved this question by dividing it into two parts:

	a) Creating the range of dates
	set @start_date := '2016-01-08';
	set @end_date := '2016-02-11';
	SELECT id, @day := DATE_ADD(@day, INTERVAL 1 DAY) AS hoje
	FROM  bugs b, (SELECT @day := DATE_SUB(STR_TO_DATE(@start_date, '%Y-%m-%d'),INTERVAL 1 DAY)   ) d
	WHERE STR_TO_DATE(@day, '%Y-%m-%d') < STR_TO_DATE(@end_date, '%Y-%m-%d');
	
	b) Creating the number of open bugs on a given day.
	SET @given_day := '2016-03-05';
	select count(*) as 'Number of bugs open' 
	from bugs
	where  STR_TO_DATE(open_date, '%Y-%m-%d')  <= STR_TO_DATE(@given_day, '%Y-%m-%d') 
		and ( STR_TO_DATE(close_date, '%Y-%m-%d') >= STR_TO_DATE(@given_day, '%Y-%m-%d') or close_date IS NULL ) ;
		
And then I put it all together.
	 

Thank you
 
  


