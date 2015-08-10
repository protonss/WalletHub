package com.wallethub;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
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
      HashMap<String, Integer> phrases = new HashMap<String, Integer>();
      try (BufferedReader br = Files.newBufferedReader(file, Charset.forName(JavaQuestion03.charset))) {
         while (br.ready()) {
            String line = br.readLine();
            String[] linePhrases = line.split("\\|");
            for (String phrase : linePhrases) {
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
         System.out.println("Please make sure you have the right txt file.");
         e.printStackTrace();
      }
      return truncateList(phrases);
   }

   private List<String> truncateList(final Map<String, Integer> p) {
      TreeMap<String, Integer> sPhrases = new TreeMap<String, Integer>((a, b) -> p.get(a) > p.get(b) ? -1 : 1);
      sPhrases.putAll(p);
      List<String> phrases = new ArrayList<String>();
      int counter = 0;
      for (Map.Entry<String, Integer> entry : sPhrases.entrySet() ) {
         phrases.add(entry.getKey());
         sPhrases.remove(entry.getKey());
         counter++;
         if (counter >= JavaQuestion03.listSize)
            break;
      }
      return phrases;
   }

}
