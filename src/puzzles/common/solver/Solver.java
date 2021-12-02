package puzzles.common.solver;

import puzzles.clock.ClockConfig;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * This class solves a puzzle
 *
 * @author Trent Wesley taw8452
 */
public class Solver {
    private int totalConfigs = 1;
    private int uniqueConfigs = 1;

    /**
     * Solve the puzzle with the given starting configuration
     * @param start starting configuration
     * @return List with the path of Configurations
     */
    public List<Configuration> solve(Configuration start){
        LinkedList<Configuration> queue = new LinkedList<>();
        HashMap<Configuration, Configuration> predMap = new HashMap<>();
        queue.add(start);
        predMap.put(start, null);
        boolean success = false;

        // run the BFS Loop
        Configuration current = new ClockConfig(0,0,0);
        while (!queue.isEmpty()){
            current = queue.remove(0);
            if (current.isSolution()) {
                success = true;
                break;}
            List<Configuration> neighbors = current.getNeighbors();
            for (Configuration c : neighbors){
                totalConfigs++;
                if (!predMap.containsKey(c)){
                    uniqueConfigs++;
                    queue.add(c);
                    predMap.put(c, current);}}}

        // build solution path and return it
        if (!success) return null;
        List<Configuration> path = new LinkedList<>();
        path.add(current);
        current = predMap.get(current);
        while (current != null){
            path.add(current);
            current = predMap.get(current);
        }
        path = reverse(path);
        return path;
    }

    /**
     * Reverses the order of a List
     * @param lst List to be reversed
     * @return reversed list
     */
    private List<Configuration> reverse(List<Configuration> lst){
        List<Configuration> reversed = new LinkedList<>();
        for (int i = lst.size()-1; i >= 0; i--){
            reversed.add(lst.get(i));}
        return reversed;
    }

    /**
     * Get total configurations generated
     * @return total configurations generated
     */
    public int getTotalConfigs(){
        return this.totalConfigs;
    }

    /**
     * Get number of unique configurations generated
     * @return number of unique configurations generated
     */
    public int getUniqueConfigs(){
        return this.uniqueConfigs;
    }
}
