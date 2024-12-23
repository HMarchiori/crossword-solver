package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordManagement {
    private final Map<Integer, List<String>> words;

    public WordManagement() {
        this.words = new HashMap<>();
    }

    public void readFile(String filepath, int limit) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath))) {
            String word;
            int count = 0;
            while ((word = bufferedReader.readLine())!= null && count < limit) {
                int length = word.length();
                words.computeIfAbsent(length, ArrayList::new).add(word);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<String> getWordsofLength(int length) {
        return words.getOrDefault(length, Collections.emptyList());
    }

}