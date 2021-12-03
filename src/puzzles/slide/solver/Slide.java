package puzzles.slide.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.slide.model.SlideConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * This class contains the main method for the slide puzzle
 *
 * @author Trent Wesley taw8452
 */
public class Slide {
    /**
     * Main method for slide puzzle
     * @param args file name of slide puzzle input
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        if (args.length != 1) {
            System.out.println("Usage: java Slide filename");
        }
        System.out.println("File: " + args[0]);
        Scanner fileScanner = new Scanner(new File(args[0]));
        int rows = fileScanner.nextInt();
        int columns = fileScanner.nextInt();
        int[][] grid = new int[rows][columns];
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < columns; c++){
                String thing = fileScanner.next();
                if (thing.equals(".")) grid[r][c] = rows*columns;
                else grid[r][c] = Integer.parseInt(thing);}}

        // create the initial SlideConfig
        SlideConfig initSlide = new SlideConfig(rows, columns, grid);
        System.out.print(initSlide);

        // create Solver
        Solver solver = new Solver();

        // calls Solver's solve with the initial config and get solution path
        List<Configuration> path = solver.solve(initSlide);
        System.out.println("Total configs: " + solver.getTotalConfigs());
        System.out.println("Unique configs: " + solver.getUniqueConfigs());

        // print solution path if it exists
        if (path == null) System.out.println("No solution");
        else{
            for (int i = 0; i < path.size(); i++){
                System.out.println("Step " + i + ": ");
                System.out.println(path.get(i));
            }
        }}
    }
