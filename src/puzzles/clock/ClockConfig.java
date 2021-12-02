package puzzles.clock;

import puzzles.common.solver.Configuration;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a clock configuration
 *
 * @author Trent Wesley taw8452
 */
public class ClockConfig implements Configuration {
    private int totalHours;
    private int endTime;
    private int currentTime;

    /**
     * ClockConfig constructor
     * @param totalHours total hours on the clock
     * @param currentTime current time on the clock
     * @param endTime goal time on the clock
     */
    public ClockConfig(int totalHours, int currentTime, int endTime){
        this.currentTime = currentTime;
        this.endTime = endTime;
        this.totalHours = totalHours;
    }

    /**
     * Determines if a ClockConfig is a solution
     * @return true if current time is the goal time, false otherwise
     */
    @Override
    public boolean isSolution(){
        return this.currentTime == this.endTime;
    }

    /**
     * Get neighbors of current ClockConfig
     * @return neighbors of current ClockConfig (hour before and after current time)
     */
    @Override
    public List<Configuration> getNeighbors(){
        int less = currentTime - 1;
        int more = currentTime + 1;
        if (less == 0) less = totalHours;
        if (more > totalHours) more = 1;
        List<Configuration> lst = new LinkedList<>();
        lst.add(new ClockConfig(totalHours, less, endTime));
        lst.add(new ClockConfig(totalHours, more, endTime));
        return lst;
    }

    /**
     * Determine whether two ClockConfigs are equal
     * @param other other ClockConfig to compare
     * @return true if current times are equal, false if not
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof ClockConfig)) return false;
        ClockConfig o = (ClockConfig) other;
        return this.currentTime == o.currentTime;
    }

    /**
     * hashcode ClockConfig
     * @return current time to represent hashcode
     */
    @Override
    public int hashCode(){
        return currentTime;
    }
}
