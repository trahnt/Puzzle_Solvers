package puzzles.clock;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.List;

/**
 * This class contains the main program for the clock puzzle
 *
 * @author Trent Wesley taw8452
 */
public class Clock {
    /**
     * Main method for Clock puzzle
     * @param args # of hours on clock, start time, stop time
     */
    public static void main(String[] args) {
        if (args.length != 3) {
            System.out.println("Usage: java Clock hours start stop");}
        else{
            System.out.println("Hours: " + args[0] + ", Start: " + args[1] + ", End: " + args[2]);

            // create the initial ClockConfig
            ClockConfig initClock = new ClockConfig(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
                    Integer.parseInt(args[2]));

            // create Solver
            Solver solver = new Solver();

            // calls Solver's solve with the initial config and get solution path
            List<Configuration> path = solver.solve(initClock);
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());

            // print solution path if it exists
            if (path == null) System.out.println("No solution");
            else{
                for (int i = 0; i < path.size(); i++){
                    System.out.println("Step " + i + ": " + path.get(i).hashCode());
                }
            }}
    }
}