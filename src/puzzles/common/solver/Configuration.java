package puzzles.common.solver;

import java.util.List;

/**
 * This class represents a configuration for a non specific puzzle
 *
 * @author Trent Wesley taw8452
 */
public interface Configuration {
    /**
     * Determines if a Configuration is a solution
     * @return true if Configuration is a solution
     */
    public boolean isSolution();

    /**
     * Get neighbors of current Configuration
     * @return neighbors of current Configuration
     */
    public List<Configuration> getNeighbors();
}
