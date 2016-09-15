package com.wallethub;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class JavaQuestion03 {

	public static final int listSize = 100000;

	// change this parameter for other countries.Recommended UTF-8
	public static String charset = "ISO-8859-1";

	public static void main(String[] args) {
	}

	public List<String> findMostFrequent(final Path file) {
		Map<String, Integer> phrases = new LinkedHashMap<String, Integer>();
		try (BufferedReader br = Files.newBufferedReader(file, Charset.forName(JavaQuestion03.charset))) {
			while (br.ready()) {
				String line = br.readLine();
				String[] linePhrases = line.split("\\|");
				for (String phrase : linePhrases) {
					phrase = phrase.trim();
					if (phrase.length() <= 0)
						continue;
					Integer n = phrases.get(phrase);
					phrases.put(phrase, n == null ? 1 : n + 1);
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("Please make sure you have the right txt file.");
		}
		return this.truncateList(phrases);
	}

	private List<String> truncateList(final Map<String, Integer> ps) {

		Stream<Entry<String, Integer>> res = ps.entrySet().stream()
				.sorted((c1, c2) -> c2.getValue().compareTo(c1.getValue())).limit(4).;

		printMap(phrases);

		List<String> phrases = new ArrayList<String>();
		return phrases;
	}

	public static <K, V> void printMap(Map<K, V> map) {
		for (Map.Entry<K, V> entry : map.entrySet()) {
			System.out.println(" Occourences : " + entry.getValue() + "  \t Phrase : " + entry.getKey());
		}
	}
}
