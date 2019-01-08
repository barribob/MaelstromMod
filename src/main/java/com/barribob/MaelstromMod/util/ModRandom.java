package com.barribob.MaelstromMod.util;

import java.util.ArrayList;
import java.util.Random;

public class ModRandom
{
    private static Random rand = new Random();
    
    /**
     * Gets a random float value with expected value 0 and the min and max range
     * @param range The range of the min and max value
     * @return
     */
    public static float getFloat(float range)
    {
	return (rand.nextFloat() - rand.nextFloat()) * 0.5f * range;
    }
    
    /**
     * Chooses a random integer between the min [inclusive] and the max [exclusive]
     * @param min
     * @param max
     * @return
     */
    public static int range(int min, int max)
    {
	return min + rand.nextInt(max);
    }
    
    /**
     * Choose a random element in the array
     * @param array
     * @return
     */
    public static <T> T choice(T[] array)
    {
	Random rand = new Random();
	int i = rand.nextInt(array.length);
	return array[i];
    }
}
