package com.wallethub;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

public class JavaQuestion03Test extends TestCase {

	public void test_type() throws Exception {
		assertNotNull(JavaQuestion03.class);
	}

	public void test_instantiation() throws Exception {
		JavaQuestion03 target = new JavaQuestion03();
		assertNotNull(target);
	}

	public void test_main_A$StringArray() throws Exception {
		String[] args = new String[] {};
		JavaQuestion03.main(args);
	}

	public void test_findMostFrequent_A$Path() throws Exception {
		JavaQuestion03 target = new JavaQuestion03();
		Path file = Paths.get("C:\\tmp\\test numbers.txt");
		List<String> actual = target.findMostFrequent(file);
		List<String> expected = Arrays.asList("123456","12345","1234","123","12","1");
		assertEquals(expected, actual);
	}

}
