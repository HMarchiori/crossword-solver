package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class WordManagement {
    private List<String> words;
    private Map<Integer, List<String>> wordsByLength;

    public WordManagement() {
        this.words = new ArrayList<>();
        this.wordsByLength = new HashMap<>();
    }

    public List<String> readFile(String filePath, int limit) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (words.size() == limit) {
                    break;
                }
                if (line.matches("[a-zA-Z]+") && !line.matches("([a-zA-Z])\\1+")) {
                    words.add(line);
                    wordsByLength.computeIfAbsent(line.length(), _ -> new ArrayList<>()).add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<String> getWordsOfLength(int length) {
        return wordsByLength.getOrDefault(length, new ArrayList<>());
    }
}