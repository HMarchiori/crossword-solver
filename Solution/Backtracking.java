package Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Backtracking {
    private Map<String, Map<WordSpace, Integer>> intersectionCache = new HashMap<>();
    private char[][] grid;
    private GridManagement gridManagement;
    private WordManagement wordManagement;
    private List<String> words;
    private List<WordSpace> wss;
    
    public Backtracking(char[][] grid, List<String> words, WordManagement wordManagement, GridManagement gridManagement) {
        this.grid = grid;
        this.words = words;
        this.wordManagement = wordManagement;
        this.gridManagement = gridManagement;
        this.wss = new ArrayList<>();
    }

    public void solveWithBacktracking() {
        // Mapeia os espaços disponíveis para as palavras
        wss = mapWordSpaces();
    
        // Ordena as palavras para priorizar aquelas com mais interseções
        sortWordsByIntersections(grid);
    
        // Começa a resolução com backtracking
        if (backtrack(grid, words)) {
            System.out.println("Solução encontrada!");
            gridManagement.printGrid(grid);
        } else {
            System.out.println("Não foi possível encontrar uma solução.");
        }
    }

    public boolean backtrack(char[][] grid, List<String> remainingWords) {
    // Caso base: se não houverem mais palavras a serem inseridas, retornamos verdadeiro
    if (remainingWords.isEmpty()) {
        return true;  // Todas as palavras foram inseridas com sucesso
    }

    // Pegue a próxima palavra a ser inserida
    String word = remainingWords.get(0);

    // Tente colocar a palavra em diferentes espaços da grade
    for (WordSpace ws : wss) {
        // Verifique se podemos colocar a palavra nesse espaço
        if (checkIntersections(grid, word, ws)) {
            // Armazene o estado atual da grade antes da inserção
            char[][] backupGrid = copyGrid(grid);

            // Inserir a palavra no espaço
            if (ws.isHorizontal()) {
                insertHorizontally(grid, word, ws);
            } else {
                insertVertically(grid, word, ws);
            }

            // Recursivamente tente inserir as outras palavras
            List<String> nextRemainingWords = new ArrayList<>(remainingWords);
            nextRemainingWords.remove(0);  // Remover a palavra que foi inserida

            if (backtrack(grid, nextRemainingWords)) {
                return true;  // Se a tentativa foi bem-sucedida, retornamos true
            }

            // Se a tentativa falhar, restaure o estado da grade e continue tentando
            grid = backupGrid;
        }
    }

    // Se não for possível inserir a palavra, retornamos falso
    return false;
}

// Função auxiliar para fazer uma cópia da grade
public char[][] copyGrid(char[][] originalGrid) {
    char[][] copy = new char[originalGrid.length][originalGrid[0].length];
    for (int i = 0; i < originalGrid.length; i++) {
        for (int j = 0; j < originalGrid[i].length; j++) {
            copy[i][j] = originalGrid[i][j];
        }
    }
    return copy;
}


    public List<WordSpace> mapWordSpaces() {
        horizontalMap(wss);
        verticalMap(wss);
        for (WordSpace ws :wss) {
            System.out.println(ws);
        }
        return wss;
    }

    public boolean checkIntersections(char[][] grid, String word, WordSpace ws) {
        if (ws.isHorizontal()) {
            return checkIntersectionsHorizontally(grid, word, ws);
        } else {
            return checkIntersectionsVertically(grid, word, ws);
        }
    }


    public int countIntersections(char[][] grid, String word, WordSpace ws) {
        if (intersectionCache.containsKey(word) && intersectionCache.get(word).containsKey(ws)) {
            return intersectionCache.get(word).get(ws); 
        }
        int count = 0;
        if (ws.isHorizontal()) {
            count = countIntersectionsHorizontally(grid, word, ws);
        } else {
            count = countIntersectionsVertically(grid, word, ws);
        }
        intersectionCache.putIfAbsent(word, new HashMap<>());
        intersectionCache.get(word).put(ws, count);
        return count;
    }

    public void greedyInsert(char[][] grid) {
        sortWordsByIntersections(grid);
        for (String w : words) {
            WordSpace bestWs = null;
            int maxIntersections = -1;

            for (WordSpace ws: wss) {
                int intersections = countIntersections(grid, w, ws);
                if (intersections > maxIntersections) {
                    maxIntersections = intersections;
                    bestWs = ws;
                }
            }

            if (bestWs != null) {
                if (bestWs.isHorizontal()) {
                    insertHorizontally(grid, w, bestWs);
                    System.out.println("Inserted " + w + " horizontally at " + bestWs);
                } else {
                    insertVertically(grid, w, bestWs);
                    System.out.println("Inserted " + w + " vertically at " + bestWs);
                }
            } else {
                System.out.println("Could not insert " + w);
            }
        }
    }

    // Métodos auxiliares
    public void sortWordsByIntersections(char[][] grid) {
        Map<String, Integer> wordIntersections = new HashMap<>();

        for (String word : words) {
            int totalIntersections = 0;
            for (WordSpace ws : wss) {
                totalIntersections += countIntersections(grid, word, ws); // Calcula interseções para cada palavra e espaço
            }
            wordIntersections.put(word, totalIntersections); // Mapeia palavra -> número de interseções
        }
        words.sort((w1, w2) -> wordIntersections.get(w2) - wordIntersections.get(w1)); 
    }

    public void insertVertically(char[][] grid, String word, WordSpace ws) {
        for (int i = ws.getStartX(); i <= ws.getEndX(); i++) {
            grid[i][ws.getStartY()] = word.charAt(i - ws.getStartX());
        }
    }

    public void insertHorizontally(char[][] grid, String word, WordSpace ws) {
        for (int j = ws.getStartY(); j <= ws.getEndY(); j++) {
            grid[ws.getStartX()][j] = word.charAt(j - ws.getStartY());
        }
    }

    public void removeVertically(char[][] grid, String word, WordSpace ws) {
        for (int i = ws.getStartX(); i <= ws.getEndX(); i++) {
            grid[i][ws.getStartY()] = '?';
        }
    }

    public void removeHorizontally(char[][] grid, String word, WordSpace ws) {
        for (int j = ws.getStartY(); j <= ws.getEndY(); j++) {
            grid[ws.getStartX()][j] = '?';
        }
    }

    public boolean checkIntersectionsHorizontally(char[][] grid, String word, WordSpace ws) {
        for (int j = ws.getStartY(); j <= ws.getEndY(); j++) {
            if (grid[ws.getStartX()][j] != '?' && grid[ws.getStartX()][j] != word.charAt(j - ws.getStartY())) {
                return false;
            }
        }
        return true;
    }

    public boolean checkIntersectionsVertically(char[][] grid, String word, WordSpace ws) {
        for (int i = ws.getStartX(); i <= ws.getEndX(); i++) {
            if (grid[i][ws.getStartY()] != '?' && grid[i][ws.getStartY()] != word.charAt(i - ws.getStartX())) {
                return false;
            }
        }
        return true;
    }
  
    public int countIntersectionsHorizontally(char[][] grid, String word, WordSpace ws) {
        int count = 0;
        for (int j = ws.getStartY(); j <= ws.getEndY(); j++) {
            if (grid[ws.getStartX()][j] != '?' && grid[ws.getStartX()][j] != word.charAt(j - ws.getStartY())) {
                count++;
            }
        }
        return count;
    }

    public int countIntersectionsVertically(char[][] grid, String word, WordSpace ws) {
        int count = 0;
        for (int i = ws.getStartX(); i <= ws.getEndX(); i++) {
            if (grid[i][ws.getStartY()] != '?' && grid[i][ws.getStartY()] != word.charAt(i - ws.getStartX())) {
                count++;
            }
        }
        return count;
    }


    public void horizontalMap(List<WordSpace> wss) {
        for (int i = 0; i < grid.length; i++) {
            int j = 0;
            while (j < grid[i].length) {
                if (grid[i][j] == '?') {
                    int startY = j;
                    while (j < grid[i].length && grid[i][j] == '?') {
                        j++;
                    }
                    if (j - startY > 1) {
                        wss.add(new WordSpace(i, startY, i, j - 1, true));
                    }
                }
                j++; 
            }
        }
    }

    public void verticalMap(List<WordSpace> wss) {
        for (int j = 0; j < grid[0].length; j++) {
            int i = 0;
            while (i < grid.length) {
                if (grid[i][j] == '?') {
                    int startX = i;
                    while (i < grid.length && grid[i][j] == '?') {
                        i++;
                    }
                    if (i - startX > 1) {
                        wss.add(new WordSpace(startX, j, i - 1, j, false));
                    }
                }
                i++;
            }
        }
    }
  
}