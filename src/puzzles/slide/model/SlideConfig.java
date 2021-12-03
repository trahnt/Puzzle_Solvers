package puzzles.slide.model;

import puzzles.common.solver.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a slide puzzle configuration
 *
 * @author Trent Wesley taw8452
 */
public class SlideConfig implements Configuration {
    private int rows;
    private int columns;
    private int[][] grid;

    public SlideConfig(int rows, int columns, int[][] grid){
        this.rows = rows;
        this.columns = columns;
        this.grid = grid;
    }


    @Override
    public boolean isSolution() {
        int previous = 0;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                if (grid[r][c] != ++previous) return false;}}
        return true;
    }

    @Override
    public List<Configuration> getNeighbors() {
        int row=0; int col=0;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                if (grid[r][c] == rows*columns){
                row = r;  col = c;}}}

        List<Configuration> configs = new LinkedList<>();
        if (row-1 >= 0){
            int[][] newGrid = copyGrid(grid);
            newGrid[row][col] = newGrid[row-1][col];
            newGrid[row-1][col] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}
        if (row+1 < rows){
            int[][] newGrid = copyGrid(grid);
            newGrid[row][col] = newGrid[row+1][col];
            newGrid[row+1][col] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}
        if (col-1 >= 0){
            int[][] newGrid = copyGrid(grid);
            newGrid[row][col] = newGrid[row][col-1];
            newGrid[row][col-1] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}
        if (col+1 < columns){
            int[][] newGrid = copyGrid(grid);
            newGrid[row][col] = newGrid[row][col+1];
            newGrid[row][col+1] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}

        return configs;
    }

    private int[][] copyGrid(int[][] grid){
        int[][] copy = new int[grid.length][grid[0].length];
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                copy[r][c] = grid[r][c];}}
        return copy;
    }

    @Override
    public String toString(){
        String string = "";
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                // TODO
            }
        }

        return string;
    }
}
