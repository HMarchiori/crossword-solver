package Solution;

import java.util.List;

class Main {

    public static void main(String[] args) {

        Timer timer = new Timer();
        WordManagement wordManagement = new WordManagement();
        GridManagement gridManagement = new GridManagement();

        timer.start();

        String wordsFilePath = "Text Files/unsorted_words.txt";
        String gridFilePath = "Test Cases/grid.txt";

        List<String> words = wordManagement.readFile(wordsFilePath, 1000000);
        char[][] grid = gridManagement.createGrid(gridFilePath);
        Backtracking backtracking = new Backtracking(grid, words, wordManagement, gridManagement);
        backtracking.mapWordSpaces();
        backtracking.solve();


        timer.stop();
        System.out.println("Elapsed time: " + timer.getElapsedTime() + "ms");
    }

}