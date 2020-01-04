package com.barribob.MaelstromMod.util;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

import net.minecraft.util.math.Vec3d;

public class ModRandom
{
    private static Random rand = new Random();

    /**
     * Gets a random float value with expected value 0 and the min and max range
     * 
     * @param range
     *            The range of the min and max value
     * @return
     */
    public static float getFloat(float range)
    {
	return rand.nextFloat() * randSign() * range;
    }

    /**
     * Chooses a random integer between the min [inclusive] and the max [exclusive]
     * 
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
     * 
     * @param array
     * @return
     */
    public static <T> T choice(T[] array)
    {
	Random rand = new Random();
	return choice(array, rand);
    }

    public static <T> T choice(T[] array, Random rand)
    {
	int i = rand.nextInt(array.length);
	return array[i];
    }

    public static <T> RandomCollection<T> choice(T[] array, Random rand, double[] weights)
    {
	if (array.length != weights.length)
	{
	    throw new IllegalArgumentException("Lengths of items and weights arrays inequal");
	}

	RandomCollection<T> weightedRandom = new RandomCollection<T>(rand);
	for (int i = 0; i < array.length; i++)
	{
	    weightedRandom.add(weights[i], array[i]);
	}

	return weightedRandom;
    }

    public static <T> RandomCollection<T> choice(T[] array, Random rand, int[] weights)
    {
	double[] converted = new double[weights.length];
	for (int i = 0; i < weights.length; i++)
	{
	    converted[i] = weights[i];
	}
	return choice(array, rand, converted);
    }

    /**
     * Weighted random collection taken from
     * https://stackoverflow.com/questions/6409652/random-weighted-selection-in-java
     */
    public static class RandomCollection<E>
    {
	private final NavigableMap<Double, E> map = new TreeMap<Double, E>();
	private final Random random;
	private double total = 0;

	public RandomCollection()
	{
	    this(new Random());
	}

	public RandomCollection(Random random)
	{
	    this.random = random;
	}

	public RandomCollection<E> add(double weight, E result)
	{
	    if (weight <= 0)
		return this;
	    total += weight;
	    map.put(total, result);
	    return this;
	}

	public E next()
	{
	    double value = random.nextDouble() * total;
	    return map.higherEntry(value).getValue();
	}
    }
}
