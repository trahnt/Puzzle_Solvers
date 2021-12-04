package puzzles.slide.model;

import puzzles.common.solver.Configuration;

import java.util.Arrays;
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

    /**
     * SlideConfig constructor
     * @param rows number of puzzle rows
     * @param columns number of puzzle columns
     * @param grid puzzle grid
     */
    public SlideConfig(int rows, int columns, int[][] grid){
        this.rows = rows;
        this.columns = columns;
        this.grid = grid;
    }

    /**
     * Determines whether a SlideConfig is the solution
     * @return true if solution, false if not
     */
    @Override
    public boolean isSolution() {
        int previous = 0;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                if (grid[r][c] != ++previous) return false;}}
        return true;
    }

    /**
     * Get neighbors of current SlideConfig
     * @return List of Configurations
     */
    @Override
    public List<Configuration> getNeighbors() {
        int row=0; int col=0;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                if (grid[r][c] == rows*columns){
                row = r;  col = c;}}}

        List<Configuration> configs = new LinkedList<>();
        if (row-1 >= 0){
            int[][] newGrid = copyGrid();
            newGrid[row][col] = newGrid[row-1][col];
            newGrid[row-1][col] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}
        if (row+1 < rows){
            int[][] newGrid = copyGrid();
            newGrid[row][col] = newGrid[row+1][col];
            newGrid[row+1][col] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}
        if (col-1 >= 0){
            int[][] newGrid = copyGrid();
            newGrid[row][col] = newGrid[row][col-1];
            newGrid[row][col-1] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}
        if (col+1 < columns){
            int[][] newGrid = copyGrid();
            newGrid[row][col] = newGrid[row][col+1];
            newGrid[row][col+1] = rows*columns;
            Configuration c = new SlideConfig(rows, columns, newGrid);
            configs.add(c);}

        return configs;
    }

    /**
     * Copy int[][]
     * @return copy of grid
     */
    public int[][] copyGrid(){
        int[][] copy = new int[grid.length][grid[0].length];
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                copy[r][c] = grid[r][c];}}
        return copy;
    }

    /**
     * Create string representation of slide puzzle config
     * @return string representation of slide puzzle config
     */
    @Override
    public String toString(){
        String string = "";
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                if (grid[r][c] < 10 || grid[r][c] == rows*columns) string = string + " ";
                if (grid[r][c] == rows*columns) string = string + ". ";
                else string = string + grid[r][c] + " ";}
            string = string + "\n";}
        return string;
    }

    /**
     * Determine whether two SlideConfigs are equal
     * @param other other SlideConfig to compare
     * @return true if grids are the same, false if not
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof SlideConfig)) return false;
        SlideConfig o = (SlideConfig) other;
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                if (grid[r][c] != o.grid[r][c]) return false;}}
        return true;
    }

    /**
     * Create hashcode of SlideConfig
     * @return hashcode of SlideConfig's grid
     */
    @Override
    public int hashCode(){
        return Arrays.deepHashCode(grid);
    }

    public int[][] getGrid(){
        return this.grid;
    }

    public void setGrid(int[][] grid){
        this.grid = grid;
    }
}
