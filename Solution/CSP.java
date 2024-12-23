package Solution;

import java.util.List;


class Word {    
    private int col, row, length;
    private String letters;
    private boolean isHorizontal;

    public Word(int col, int row, int length, String letters, boolean isHorizontal) {
        this.col = col;
        this.row = row;
        this.length = length;
        this.letters = letters;
        this.isHorizontal = isHorizontal;
    }

}
public class CSP {
    private char[][] grid;
    private List<Word> words;
    private WordManagement wordManagement;
    private GridManagement gridManagement;
    
}
