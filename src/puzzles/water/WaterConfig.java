package puzzles.water;

import puzzles.common.solver.Configuration;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a water puzzle configuration
 *
 * @author Trent Wesley taw8452
 */
public class WaterConfig implements Configuration {
    private int amount;
    private int[] bucketCapacities;
    private int[] buckets;

    /**
     * WaterConfig Constructor
     * @param amount goal amount of water in bucket
     * @param bucketCapacities int[] of bucket capacities
     * @param buckets int[] of current amount of water in buckets
     */
    public WaterConfig(int amount, int[] bucketCapacities, int[] buckets){
        this.amount = amount;
        this.bucketCapacities = bucketCapacities;
        this.buckets = buckets;
    }

    /**
     * Determines if a WaterConfig is a solution
     * @return true if a bucket has the goal amount of water, false otherwise
     */
    @Override
    public boolean isSolution(){
        for (int b : buckets){
            if (b == amount) return true;}
        return false;
    }

    /**
     * Get neighbors of current WaterConfig
     * @return neighbors of current WaterConfig
     */
    @Override
    public List<Configuration> getNeighbors(){
        List<Configuration> neighbors = new LinkedList<>();
        for (int i = 0; i < buckets.length; i++){
            int[] temp = buckets.clone();
            temp[i] = 0;
            neighbors.add(new WaterConfig(amount, bucketCapacities, temp.clone()));
            temp[i] = bucketCapacities[i];
            neighbors.add(new WaterConfig(amount, bucketCapacities, temp.clone()));
            for (int j = 0; j < buckets.length; j++){
                temp = buckets.clone();
                if (i != j){
                    int space = bucketCapacities[j] - buckets[j];
                    if (space >= buckets[i]){
                        temp[j] = buckets[j] + buckets[i];
                        temp[i] = 0;}
                    else{
                        temp[i] = buckets[i] - space;
                        temp[j] = bucketCapacities[j];}
                    neighbors.add(new WaterConfig(amount, bucketCapacities, temp.clone()));}}}
        return neighbors;
    }

    /**
     * Determine whether two WaterConfigs are equal
     * @param other other WaterConfig to compare
     * @return true if WaterConfigs have same buckets water levels
     */
    @Override
    public boolean equals(Object other){
        if (!(other instanceof WaterConfig)) return false;
        WaterConfig o = (WaterConfig) other;
        return Arrays.equals(this.buckets, o.buckets);
    }

    /**
     * hashcode WaterConfig
     * @return hashcode of current buckets
     */
    @Override
    public int hashCode(){
        return Arrays.hashCode(buckets);
    }

    /**
     * Get the current buckets
     * @return current int[] of buckets
     */
    public int[] getBuckets(){
        return buckets;
    }
}
