package com.barribob.MaelstromMod.util;

import java.util.Random;

public class ModRandom
{
    /**
     * Gets a random float value with expected value 0 and the min and max range
     * @param range The range of the min and max value
     * @return
     */
    public static float getFloat(float range)
    {
	Random rand = new Random();
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
	Random rand = new Random();
	return min + rand.nextInt(max);
    }
}
