package Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WordSpace {
    private int StartX, StartY, EndX, EndY;
    private boolean isHorizontal;

    public WordSpace(int StartX, int StartY, int EndX, int EndY, boolean isHorizontal) {
        this.StartX = StartX;
        this.StartY = StartY;
        this.EndX = EndX;
        this.EndY = EndY;
        this.isHorizontal = isHorizontal;
    }

    public int getSize() {
        return isHorizontal ? EndY - StartY + 1 : EndX - StartX + 1;
    }

    public int getStartX() {
        return StartX;
    }

    public int getStartY() {
        return StartY;
    }

    public int getEndX() {
        return EndX;
    }

    public int getEndY() {
        return EndY;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

}

public class Backtracking {
    private char[][] grid;
    private GridManagement gridManagement;
    private WordManagement wordManagement;
    private List<String> words;
    private List<WordSpace> wordSpaces;
    
    public Backtracking(char[][] grid, List<String> words, WordManagement wordManagement, GridManagement gridManagement) {
        this.grid = grid;
        this.words = words;
        this.wordManagement = wordManagement;
        this.gridManagement = gridManagement;
        this.wordSpaces = new ArrayList<>();
    }

    public void solve() {
        wordSpaces = mapWordSpaces();
        solve(0);
        gridManagement.printGrid(grid);
    }

    public List<WordSpace> mapWordSpaces() {
        horizontalMap(wordSpaces);
        verticalMap(wordSpaces);
       for (WordSpace wordSpace : wordSpaces) {
            System.out.println("WordSpace: " + wordSpace.getStartX() + " " + wordSpace.getStartY() + " " + wordSpace.getEndX() + " " + wordSpace.getEndY() + " " + wordSpace.isHorizontal());
            System.out.println("Size: " + wordSpace.getSize() + "\n");
        
        }
        return wordSpaces;
    }

    public void horizontalMap(List<WordSpace> wordSpaces) {
        for (int i = 0; i < grid.length; i++) {
            int j = 0;
            while (j < grid[i].length) {
                if (grid[i][j] == '?') {
                    int StartY = j;
                    while (j < grid[i].length && grid[i][j] == '?') {
                        j++;
                    }
                    if (j - StartY > 1) {
                        wordSpaces.add(new WordSpace(i, StartY, i, j - 1, true));
                    }
                }
                j++; 
            }
        }
    }

    public void verticalMap(List<WordSpace> wordSpaces) {
        for (int j = 0; j < grid[0].length; j++) {
            int i = 0;
            while (i < grid.length) {
                if (grid[i][j] == '?') {
                    int StartX = i;
                    while (i < grid.length && grid[i][j] == '?') {
                        i++;
                    }
                    if (i - StartX > 1) {
                        wordSpaces.add(new WordSpace(StartX, j, i - 1, j, false));
                    }
                }
                i++;
            }
        }
    }

    public void insert(char[][] grid, String word, WordSpace wordSpace) {
        if (!wordSpace.isHorizontal()) {
            for (int i = wordSpace.getStartX(); i <= wordSpace.getEndX(); i++) {
                grid[i][wordSpace.getStartY()] = word.charAt(i - wordSpace.getStartX());
            }
            System.out.println("Inserted " + word);
            System.out.println("StartX:" + wordSpace.getStartX() + " StartY:" + wordSpace.getStartY() + " EndX:" + wordSpace.getEndX() + " EndY:" + wordSpace.getEndY());
        } else {
            for (int j = wordSpace.getStartY(); j <= wordSpace.getEndY(); j++) {
                grid[wordSpace.getStartX()][j] = word.charAt(j - wordSpace.getStartY());
            }
        }
    }

    public void remove(char[][] grid, String word, WordSpace wordSpace) {
        if (wordSpace.isHorizontal()) {
            for (int j = wordSpace.getStartY(); j <= wordSpace.getEndY(); j++) {
                grid[wordSpace.getStartX()][j] = '?';
            }
        } else {
            for (int i = wordSpace.getStartX(); i <= wordSpace.getEndX(); i++) {
                grid[i][wordSpace.getStartY()] = '?';
            }
        }
    }

    public boolean checkIntersections(char[][] grid, String word, WordSpace wordSpace) {
        if (wordSpace.isHorizontal()) {
            for (int j = wordSpace.getStartY(); j <= wordSpace.getEndY(); j++) {
                // Verifica se o caractere na posição do grid é '?' (vazio) ou se é igual ao caractere da palavra
                if (grid[wordSpace.getStartX()][j] != '?' && grid[wordSpace.getStartX()][j] != word.charAt(j - wordSpace.getStartY())) {
                    return false; // Se a letra não coincide, retorna falso
                }
            }
        } else {
            for (int i = wordSpace.getStartX(); i <= wordSpace.getEndX(); i++) {
                // Verifica se o caractere na posição do grid é '?' (vazio) ou se é igual ao caractere da palavra
                if (grid[i][wordSpace.getStartY()] != '?' && grid[i][wordSpace.getStartY()] != word.charAt(i - wordSpace.getStartX())) {
                    return false; // Se a letra não coincide, retorna falso
                }
            }
        }
        return true; // Se não houver conflitos, retorna verdadeiro
    }

    public int countIntersections(char[][] grid, String word, WordSpace wordSpace) {
        int intersections = 0;
    
        if (wordSpace.isHorizontal()) {
            for (int j = wordSpace.getStartY(); j <= wordSpace.getEndY(); j++) {
                // Se o caractere no grid não for '?' e não corresponder ao caractere da palavra, retorna -1 (indica conflito)
                if (grid[wordSpace.getStartX()][j] != '?' && grid[wordSpace.getStartX()][j] != word.charAt(j - wordSpace.getStartY())) {
                    return -1;
                }
                // Conta a interseção se o caractere for igual ao da palavra
                if (grid[wordSpace.getStartX()][j] == word.charAt(j - wordSpace.getStartY())) {
                    intersections++;
                }
            }
        } else {
            for (int i = wordSpace.getStartX(); i <= wordSpace.getEndX(); i++) {
                // Se o caractere no grid não for '?' e não corresponder ao caractere da palavra, retorna -1 (indica conflito)
                if (grid[i][wordSpace.getStartY()] != '?' && grid[i][wordSpace.getStartY()] != word.charAt(i - wordSpace.getStartX())) {
                    return -1;
                }
                // Conta a interseção se o caractere for igual ao da palavra
                if (grid[i][wordSpace.getStartY()] == word.charAt(i - wordSpace.getStartX())) {
                    intersections++;
                }
            }
        }
        return intersections;
    }

    public String BCU(char[][] grid, List<String> words, WordSpace wordSpace, int size) {
        List<String> validWords = new ArrayList<>();
        List<String> wordsOfLength = wordManagement.getWordsOfLength(size); // Certifique-se de que wordManagement está correto
    
        // Mapeia as palavras válidas com base nas interseções
        Map<String, Integer> wordIntersections = new HashMap<>();
        
        for (String word : wordsOfLength) {
            // Verifica as interseções para cada palavra
            int intersections = countIntersections(grid, word, wordSpace);
            if (intersections >= 0) { // Só palavras que não causam conflitos
                wordIntersections.put(word, intersections);
                validWords.add(word);
            }
        }
        
        // Se não houver palavras válidas, retorna null
        if (validWords.isEmpty()) {
            return null; // Nenhuma palavra válida disponível
        }
        
        // Ordena as palavras por número de interseções (mais interseções primeiro)
        validWords.sort((a, b) -> Integer.compare(wordIntersections.get(b), wordIntersections.get(a)));
        
        // Retorna a palavra com mais interseções
        return validWords.get(0); // Retorna a melhor palavra possível
    }

    public boolean solve(int index) {
        if (index == wordSpaces.size()) {
            return true; // Todas as palavras foram posicionadas
        }
    
        WordSpace currentSpace = wordSpaces.get(index);
        int size = currentSpace.getSize();
        String word = BCU(grid, words, currentSpace, size);
    
        if (word == null) {
            return false; // Não há palavra válida para este espaço, faz backtracking
        }
    
        if (checkIntersections(grid, word, currentSpace)) {
            insert(grid, word, currentSpace);
    
            if (solve(index + 1)) {
                return true;
            }
    
            remove(grid, word, currentSpace); // Backtrack
        }
    
        return false; // Nenhuma solução encontrada
        }
    }