package puzzles.water;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.List;

/**
 * This class contains the main program for the water puzzle
 *
 * @author Trent Wesley taw8452
 */
public class Water {
    /**
     * Main method for Water puzzle
     * @param args goal amount, bucket1 size, bucket2 size...
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Water amount bucket1 bucket2 ..."));}
        else{
            int[] buckets = new int[args.length-1];
            for (int i = 1; i < args.length; i++){
                buckets[i-1] = Integer.parseInt(args[i]);}
            System.out.print("Amount: " + args[0] + ", Buckets: ");
            printBuckets(buckets);

            WaterConfig initWater = new WaterConfig(Integer.parseInt(args[0]), buckets, new int[buckets.length]);
            // create Solver
            Solver solver = new Solver();
            // calls Solver's solve with the initial config and get solution path
            List<Configuration> path = solver.solve(initWater);
            System.out.println("Total configs: " + solver.getTotalConfigs());
            System.out.println("Unique configs: " + solver.getUniqueConfigs());
            // print solution path if it exists
            if (path == null) System.out.println("No solution");
            else{
                for (int i = 0; i < path.size(); i++){
                    WaterConfig w = (WaterConfig) path.get(i);
                    System.out.print("Step " + i + ": ");
                    printBuckets(w.getBuckets());
                }}}
    }

    /**
     * prints buckets in [a, b] format
     * @param buckets int[] of gallons in buckets
     */
    private static void printBuckets(int[] buckets){
        System.out.print("[");
        for (int i = 0; i < buckets.length; i++){
            System.out.print(buckets[i]);
            if (i < buckets.length-1) System.out.print(", ");
            else System.out.println("]");}
    }
}
