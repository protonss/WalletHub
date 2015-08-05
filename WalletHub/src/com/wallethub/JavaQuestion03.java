package com.wallethub;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JavaQuestion03 {

	public static final int listSize = 100000;

	// change this parameter for other countries.Recommended UTF-8
	public static String charset = "ISO-8859-1";

	public static void main(String[] args) {
	}

	public List<String> findMostFrequent(final Path file) {
		TreeMap<String, Integer> phrases = new TreeMap<String, Integer>();
		try (BufferedReader br = Files.newBufferedReader(file, Charset.forName(JavaQuestion03.charset))) {
			while (br.ready()) {
				String p = br.readLine();
				String[] lphrases = p.split("\\|");
				// This for loop works for a 50 phrase per line file or any
				// other file. More generic.
				for (String phrase : lphrases) {
					phrase = phrase.trim();
					if (phrase.length() <= 0)
						continue;
					if (!phrases.containsKey(phrase)) {
						phrases.put(phrase, 1);
					} else {
						phrases.put(phrase, phrases.get(phrase) + 1);
					}
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return truncateList(phrases);
	}

	private List<String> truncateList(final TreeMap<String, Integer> p) {
		TreeMap<String, Integer> sPhrases = new TreeMap<String, Integer>(new MFComparator(p));
		sPhrases.putAll(p);
		List<String> phrases = new ArrayList<String>();
		int counter = 0;
		for (Map.Entry<String, Integer> entry : sPhrases.entrySet()) {
			phrases.add(entry.getKey());
			counter++;
			if (counter >= JavaQuestion03.listSize)
				break;
		}
		return phrases;
	}

}

class MFComparator implements Comparator<String> {
	TreeMap<String, Integer> phrases;

	public MFComparator(TreeMap<String, Integer> phrases) {
		this.phrases = phrases;
	}

	@Override
	public int compare(String a, String b) {
		return phrases.get(a) > phrases.get(b) ? -1 : 1;
	}
}
