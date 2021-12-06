package puzzles.slide.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.File;
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

    public void makeMove(int row, int col){
        if (move){ // if player is making first selection
            if (select1(row, col)){ // if selection is valid
                selected[0] = row; // selected row is set
                selected[1] = col; // selected column is set
                move = false;
                alertObservers(new SlideClientData("Selected (" + row + ", " + col + ")"));}
            else{
                alertObservers(new SlideClientData("Invalid selection (" + row + ", " + col + ")"));
            }
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
                alertObservers(new SlideClientData("Invalid selection (" + row + ", " + col + ")"));}
        }
    }

    public boolean select1(int row, int col){
        int[][] grid = currentConfig.getGrid();
        int rows = grid.length; int columns = grid[0].length;
        if (row < rows && row >= 0 && col < columns && col >=0){
            return grid[row][col] != rows * columns;}
        return false;
    }

    public boolean select2(int row2, int col2, int row1, int col1){
        int[][] grid = currentConfig.getGrid();
        int rows = grid.length; int columns = grid[0].length;
        if (row2 >= rows || row2 < 0 || col2 >= columns || col2 < 0) return false;
        if (grid[row2][col2] != rows*columns) return false;
        if (row2 == row1) return col2 == col1+1 || col2 == col1-1;
        if (col2 == col1) return row2 == row1+1 || row2 == row1-1;
        return false;
    }

    public SlideConfig getCurrentConfig(){
        return this.currentConfig;
    }

    public void resetPuzzle(){
        currentConfig.setGrid(initialConfig.copyGrid());
        alertObservers(new SlideClientData("Puzzle Reset"));
    }

    public void hint(){
        Solver solver = new Solver();
        List<Configuration> path = solver.solve(getCurrentConfig());
        if (getCurrentConfig().isSolution()) {
            alertObservers(new SlideClientData("Already Solved"));
        }
        else if (path.isEmpty()) alertObservers(new SlideClientData("No Solution"));
        else {
            try{
                SlideConfig s = (SlideConfig) path.get(1);
                getCurrentConfig().setGrid(s.copyGrid());
                alertObservers(new SlideClientData("Next Step"));}
            catch (Exception E) {}
        }
    }
}
