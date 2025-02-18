package Solution;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GridManagement {

    public List<String> readFile(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public char[][] createGrid(String filePath) {
        List<String> lines = readFile(filePath);
        int rows = lines.size();
        int cols = lines.get(0).length();
        char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            String line = lines.get(i);
            for (int j = 0; j < cols; j++) {
                grid[i][j] = line.charAt(j);
            }
        }
    return grid;
    }

    public void printGrid(char[][] grid) {
        StringBuilder output = new StringBuilder();
        for (char[] row : grid) {
            for (char cell : row) {
                output.append(cell).append(" "); 
            }
            output.append("\n"); 
        }
        System.out.print(output);
    }

}
