package Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class WordSpace {
    private int startRow, startCol, endRow, endCol;
    private boolean isHorizontal;

    public WordSpace(int startRow, int startCol, int endRow, int endCol, boolean isHorizontal) {
        this.startRow = startRow;
        this.startCol = startCol;
        this.endRow = endRow;
        this.endCol = endCol;
        this.isHorizontal = isHorizontal;
    }

    public int getSize() {
        return isHorizontal ? endCol - startCol + 1 : endRow - startRow + 1;
    }

    public int getStartRow() {
        return startRow;
    }

    public int getStartCol() {
        return startCol;
    }

    public int getEndRow() {
        return endRow;
    }

    public int getEndCol() {
        return endCol;
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
        solve(0);
    }

    public List<WordSpace> mapWordSpaces() {
        horizontalMap(wordSpaces);
        verticalMap(wordSpaces);

        return wordSpaces;
    }

    public void horizontalMap(List<WordSpace> wordSpaces) {
        for (int i = 0; i < grid.length; i++) {
            int j = 0;
            while (j < grid[i].length) {
                if (grid[i][j] == '?') {
                    int startCol = j;
                    while (j < grid[i].length && grid[i][j] == '?') {
                        j++;
                    }
                    if (j - startCol > 1) {
                        wordSpaces.add(new WordSpace(i, startCol, i, j - 1, true));
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
                    int startRow = i;
                    while (i < grid.length && grid[i][j] == '?') {
                        i++;
                    }
                    if (i - startRow > 1) {
                        wordSpaces.add(new WordSpace(startRow, j, i - 1, j, false));
                    }
                }
                i++;
            }
        }
    }

    public void insert(char[][] grid, String word, WordSpace wordSpace) {
        if (wordSpace.isHorizontal()) {
            for (int j = wordSpace.getStartCol(); j <= wordSpace.getEndCol(); j++) {
                grid[wordSpace.getStartRow()][j] = word.charAt(j - wordSpace.getStartCol());
            }
        } else {
            for (int i = wordSpace.getStartRow(); i <= wordSpace.getEndRow(); i++) {
                grid[i][wordSpace.getStartCol()] = word.charAt(i - wordSpace.getStartRow());
            }
        }
    }

    public void remove(char[][] grid, String word, WordSpace wordSpace) {
        if (wordSpace.isHorizontal()) {
            for (int j = wordSpace.getStartCol(); j <= wordSpace.getEndCol(); j++) {
                grid[wordSpace.getStartRow()][j] = '?';
            }
        } else {
            for (int i = wordSpace.getStartRow(); i <= wordSpace.getEndRow(); i++) {
                grid[i][wordSpace.getStartCol()] = '?';
            }
        }
    }

    public boolean checkIntersections(char[][] grid, String word, WordSpace wordSpace) {
        if (wordSpace.isHorizontal()) {
            for (int j = wordSpace.getStartRow(); j <= wordSpace.getEndRow() + 1; j++) {
                if (grid[wordSpace.getStartCol()][j] != '?' && grid[wordSpace.getStartCol()][j] != word.charAt(j - wordSpace.getStartRow())) {
                    return false;
                }
            }
        }
        else {
            for (int i = wordSpace.getStartCol(); i <= wordSpace.getEndCol() + 1; i++) {
                if (grid[i][wordSpace.getStartRow()] != '?' && grid[i][wordSpace.getStartRow()] != word.charAt(i - wordSpace.getStartCol())) {
                    return false;
                }
            }
        }
        return true;
    }

    public int countIntersections(char[][] grid, String word, WordSpace wordSpace) {
        int count = 0;
    
        if (wordSpace.isHorizontal()) {
            int row = wordSpace.getStartRow();
            for (int j = wordSpace.getStartCol(); j <= wordSpace.getEndCol(); j++) {
                char gridChar = grid[row][j];
                char wordChar = word.charAt(j - wordSpace.getStartCol());
    
                if (gridChar != '?' && gridChar != wordChar) {
                    return -1; 
                }
                if (gridChar == wordChar) {
                    count++; 
                }
            }
        } else {
            int col = wordSpace.getStartCol();
            for (int i = wordSpace.getStartRow(); i <= wordSpace.getEndRow(); i++) {
                char gridChar = grid[i][col];
                char wordChar = word.charAt(i - wordSpace.getStartRow());
    
                if (gridChar != '?' && gridChar != wordChar) {
                    return -1; 
                }
                if (gridChar == wordChar) {
                    count++; 
                }
            }
        }
    
        return count;
    }

    public List<String> BCU(char[][] grid, List<String> words, WordSpace wordSpace, int size) {

        List<String> validWords = new ArrayList<>();
        List<String> wordsOfLength = wordManagement.getWordsOfLength(size);
        Map<String, Integer> wordIntersections = new HashMap<>();
        
        for (String word : wordsOfLength) {
            int intersections = countIntersections(grid, word, wordSpace);
            if (intersections >= 0) {
                wordIntersections.put(word, intersections);
                validWords.add(word);
            }
        }
    
        validWords.sort((a, b) -> Integer.compare(wordIntersections.get(b), wordIntersections.get(a)));
        return validWords;
    }

    public boolean solve(int index) {
        if (index == wordSpaces.size()) {
            gridManagement.printGrid(grid);
            return true;
        }
        WordSpace wordSpace = wordSpaces.get(index);
        List<String> validWords = BCU(grid, words, wordSpace, wordSpace.getSize());
        for (String word : validWords) {
            if (checkIntersections(grid, word, wordSpace)) {
                insert(grid, word, wordSpace);
                if (solve(index + 1)) {
                    return true;
                }
                remove(grid, word, wordSpace);
            }
        }
        System.out.println("No solution found");
        gridManagement.printGrid(grid);
        return false;
    }
}