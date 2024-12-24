package Solution;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> words;
    private List<WordSpace> wordSpaces;
    
    public Backtracking(char[][] grid, List<String> words) {
        this.grid = grid;
        this.words = words;
        this.wordSpaces = new ArrayList<>();
    }


    public List<WordSpace> mapWordSpaces() {
        horizontalMap(wordSpaces);
        verticalMap(wordSpaces);
        //printWordSpaces(wordSpaces);

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

    public void printWordSpaces(List<WordSpace> wordSpaces) {
        for (WordSpace wordSpace : wordSpaces) {
            System.out.println("Start Row: " + wordSpace.getStartRow());
            System.out.println("Start Col: " + wordSpace.getStartCol());
            System.out.println("End Row: " + wordSpace.getEndRow());
            System.out.println("End Col: " + wordSpace.getEndCol());
            if (wordSpace.isHorizontal()) {
                System.out.println("Orientation: Horizontal");
            } else {
                System.out.println("Orientation: Vertical");
            }
            System.out.println("Size: " + wordSpace.getSize());
            System.out.println();
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

}