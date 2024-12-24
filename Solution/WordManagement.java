package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WordManagement {
    private List<String> words;

    public WordManagement() {
        this.words = new ArrayList<>();
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
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return words;
    }

    public List<String> getWordsOfLength(int length) {
        List<String> wordsOfLength = new ArrayList<>();
        for (String word : words) {
            if (word.length() == length) {
                wordsOfLength.add(word);
            }
        }
        return wordsOfLength;
    }

    public void printWords(List<String> words) {
        for (String word : words) {
            System.out.println(word);
        }
    }


}