package Solution;

import java.util.List;

class Main {

    public static void main(String[] args) {

        Timer timer = new Timer();
        WordManagement wordManagement = new WordManagement();
        GridManagement gridManagement = new GridManagement();

        timer.start();

        String wordsFilePath = "Text Files/unsorted_words.txt";
        String gridFilePath = "Test Cases/grid3.txt";

        List<String> words = wordManagement.readFile(wordsFilePath, 100000);
        char[][] grid = gridManagement.createGrid(gridFilePath);
        Backtracking backtracking = new Backtracking(grid, words);
        backtracking.mapWordSpaces();


        timer.stop();
        System.out.println("Elapsed time: " + timer.getElapsedTime() + "ms");
    }

}