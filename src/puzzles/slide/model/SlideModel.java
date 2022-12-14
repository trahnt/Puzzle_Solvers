package puzzles.slide.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class SlideModel {
    /** the collection of observers of this model */
    private final List<Observer<SlideModel, SlideClientData>> observers = new LinkedList<>();

    /** the current configuration */
    private SlideConfig currentConfig;

    // Current move. true if 1st selection, false if 2nd.
    private boolean move = true;

    // Selected square
    private int[] selected = new int[2];

    // Initial Configuration
    private SlideConfig initialConfig;

    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<SlideModel, SlideClientData> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(SlideClientData data) {
        for (var observer : observers) {
            observer.update(this, data);
        }
    }

    /**
     * SlideModel constructor
     * @param filename name of file
     * @throws IOException
     */
    public SlideModel(String filename) throws IOException {
        Scanner fileScanner = new Scanner(new File(filename));
        int rows = fileScanner.nextInt();
        int columns = fileScanner.nextInt();
        int[][] grid = new int[rows][columns];
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                String thing = fileScanner.next();
                if (thing.equals(".")) grid[r][c] = rows*columns;
                else grid[r][c] = Integer.parseInt(thing);}}
        currentConfig = new SlideConfig(rows, columns, grid);
        initialConfig = new SlideConfig(rows, columns, currentConfig.copyGrid());
    }

    /**
     * Make a move based on the row and column selected
     * @param row row of number chosen
     * @param col column of number chosen
     */
    public void makeMove(int row, int col){
        if (move){ // if player is making first selection
            if (select1(row, col)){ // if selection is valid
                selected[0] = row; // selected row is set
                selected[1] = col; // selected column is set
                move = false;
                alertObservers(new SlideClientData("Selected (" + row + ", " + col + ")"));}
        }
        else{  // if player making second selection
            if (select2(row, col, selected[0], selected[1])){ // if second selection is valid
                int[][] grid = currentConfig.getGrid();

                // Swap two selected items
                int thing1 = grid[row][col];
                int thing2 = grid[selected[0]][selected[1]];
                grid[row][col] = thing2;
                grid[selected[0]][selected[1]] = thing1;
                move = true;
                alertObservers(new SlideClientData("Moved from (" + selected[0] +
                        ", " + selected[1] + ") to (" + row + ", " + col + ")"));}
            else{
                move = true;
                alertObservers(new SlideClientData("Can't move from (" + selected[0] +
                        ", " + selected[1] + ") to (" + row + ", " + col + ")"));}
        }
    }

    /**
     * Determine whether selection is valid
     * @param row row of number chosen
     * @param col column of number chosen
     * @return true if selection is valid, false if not
     */
    public boolean select1(int row, int col){
        int[][] grid = currentConfig.getGrid();
        int rows = grid.length; int columns = grid[0].length;
        if (row < rows && row >= 0 && col < columns && col >=0){
            if (grid[row][col] != rows * columns) return true;
            else {
                alertObservers(new SlideClientData("No number at (" + row + ", " + col + ")"));
                return false;
            }
        }
        alertObservers(new SlideClientData("Invalid selection (" + row + ", " + col + ")"));
        return false;
    }

    /**
     * Determine whether selection is valid
     * @param row2 2nd row choice
     * @param col2 2nd column choice
     * @param row1 1st row choice
     * @param col1 1st column choice
     * @return true if move is valid, false if not
     */
    public boolean select2(int row2, int col2, int row1, int col1){
        int[][] grid = currentConfig.getGrid();
        int rows = grid.length; int columns = grid[0].length;
        if (row2 >= rows || row2 < 0 || col2 >= columns || col2 < 0) return false;
        if (grid[row2][col2] != rows*columns) return false;
        if (row2 == row1) return col2 == col1+1 || col2 == col1-1;
        if (col2 == col1) return row2 == row1+1 || row2 == row1-1;
        return false;
    }

    /**
     * Get current slide configuration
     * @return current slide configuration
     */
    public SlideConfig getCurrentConfig(){
        return this.currentConfig;
    }

    /**
     * Reset puzzle
     */
    public void resetPuzzle(){
        move = true;
        currentConfig.setGrid(initialConfig.copyGrid());
        alertObservers(new SlideClientData("Puzzle Reset"));
    }

    /**
     * Give hint for puzzle by making a move
     */
    public void hint(){
        Solver solver = new Solver();
        List<Configuration> path = solver.solve(getCurrentConfig());
        if (getCurrentConfig().isSolution()) {
            alertObservers(new SlideClientData("Already Solved"));
        }
        else if (path == null) alertObservers(new SlideClientData("No Solution"));
        else {
            try{
                SlideConfig s = (SlideConfig) path.get(1);
                getCurrentConfig().setGrid(s.copyGrid());
                alertObservers(new SlideClientData("Next Step"));}
            catch (Exception E) {}
        }
    }

    /**
     * Load in a file
     * @param file file to be loaded for slide puzzle
     * @throws FileNotFoundException
     */
    public void loadFile(File file) throws FileNotFoundException {
        move = true;
        Scanner fileScanner = new Scanner(file);
        int rows = fileScanner.nextInt();
        int columns = fileScanner.nextInt();
        int[][] grid = new int[rows][columns];
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                String thing = fileScanner.next();
                if (thing.equals(".")) grid[r][c] = rows*columns;
                else grid[r][c] = Integer.parseInt(thing);}}
        currentConfig = new SlideConfig(rows, columns, grid);
        initialConfig = new SlideConfig(rows, columns, currentConfig.copyGrid());
        alertObservers(new SlideClientData("Loaded: " + file));
    }

    /**
     * Create string representation of model
     * @return string representation of model
     */
    @Override
    public String toString(){
        int rows = currentConfig.getGrid().length;
        int columns = currentConfig.getGrid()[0].length;
        String result = "  ";
        for (int c = 0; c < columns; c++){
            result = result + "  " + c;}
        result = result + "\n  ";
        for (int c = 0; c <columns; c++){
            result = result + "---";}
        result = result + "\n";
        for (int r = 0; r < rows; r++){
            result = result + r + "|";
            for (int c = 0; c < columns; c++){
                int num = currentConfig.getGrid()[r][c];
                if (num == rows*columns) result = result + "  .";
                else if (num < 10) result = result + "  " + num;
                else result = result + " " + num;
            }
            result = result + "\n";
        }
        return result;
    }
}
