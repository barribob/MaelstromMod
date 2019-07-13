package com.barribob.MaelstromMod.util;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.util.math.Vec3d;

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
	return rand.nextFloat() * randSign() * range;
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
     * Returns a vector where each value is a random float between -0.5 and 0.5
     */
    public static Vec3d randVec()
    {
	return new Vec3d(getFloat(0.5f), getFloat(0.5f), getFloat(0.5f));
    }
    
    /**
     * Produces 1 or -1 with equal probablity
     */
    public static int randSign()
    {
	return rand.nextInt(2) == 0 ? 1 : -1;
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
