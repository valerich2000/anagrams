import java.io.*;
import java.util.*;
import java.util.stream.*;

public class Main {

    public static void main(String[] args) throws IOException {

        String fileName = "src/pldf-win.txt";
        String searchAnagramsFor = "тыква";
        Main obj = new Main();

        String[] dictionary = obj.fileToArray(fileName);
        ArrayList<String> result = (ArrayList<String>) obj.collectAnagrams(dictionary, searchAnagramsFor);

        for (String anagram : result) {
            System.out.println(anagram);
        }
    }

    private Collection collectAnagrams(String[] dictionary, String searchAnagramsFor) {
        Map<Character, Integer> mapAnagram = createMapWord(searchAnagramsFor);
        return Arrays
                .stream(dictionary).parallel().unordered()
                .filter(el -> isAnagram(el, mapAnagram, searchAnagramsFor))
                .collect(Collectors.toList());
    }


    private boolean isAnagram(String s1, Map<Character, Integer> mapAnagram, String s2) {
        if (createMapWord(s1).equals(mapAnagram) && !s1.equals(s2)) return true;
        else return false;
    }


    // создаем карту символов для слова
    private Map<Character, Integer> createMapWord(String word) {
//  ver 1  164 - 190
        HashMap<Character, Integer> mapWord = new HashMap<>();
        char[] symbolWord = word.toCharArray();

        for (char symbolCurrent : symbolWord) {
            if (mapWord.containsKey(symbolCurrent)) {
                int tmp = mapWord.get(symbolCurrent);
                mapWord.put(symbolCurrent, tmp + 1);
            } else {
                mapWord.put(symbolCurrent, 1);
            }
        }
        return mapWord;

//  ver 2  246 - 272 (min - average)
//        Map<Character, Long> mapWord = word.chars()
//                .mapToObj(c -> (char) c)
//                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
//        return mapWord;

// ver 3  228 - 250
//        Map<Character, Integer> mapWord = word.chars()
//        .mapToObj(c -> (char) c)
//        .collect(Collectors.toMap(Function.identity(), i -> 1, (a, b) -> a + b));
//        return mapWord;
    }

    // записываем словарь из файла в список ArrayList (возвращаем String[])
    private String[] fileToArray(String fileName) throws IOException {
        ArrayList<String> list = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "windows-1251"));

        while (reader.ready()) {
            String line = reader.readLine();
            list.add(line);
        }
        reader.close();

        String[] dictionary = new String[list.size()];
        for(int i = 0; i < list.size(); i++) {
            dictionary[i] = list.get(i);
        }
        return dictionary;
    }
}
