package com.barribob.MaelstromMod.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class ModUtils
{
    /**
     * Calls the function n times, passing in the ith iteration
     * @param n
     * @param func
     */
    public static void performNTimes(int n, Consumer<Integer> func)
    {
	for(int i = 0; i < n; i++)
	{
	    func.accept(i);
	}
    }
    
    /**
     * Returns all EntityLivingBase entities in a certain bounding box
     */
    public static List<EntityLivingBase> getEntitiesInBox(Entity entity, AxisAlignedBB bb)
    {
	List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, bb);
	
	if(list != null)
	{	    
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase)i;
	    
	    return list.stream().filter(isInstance).map(cast).collect(Collectors.toList());
	}
	
	return null;
    }
    
    /**
     * Returns the entity's position in vector form
     */
    public static Vec3d entityPos(Entity entity)
    {
	return new Vec3d(entity.posX, entity.posY, entity.posZ);
    }
    
    /*
     * Generates a generator n times in a chunk
     */
    public static void generateN(World worldIn, Random rand, BlockPos pos, int n, int baseY, int randY, WorldGenerator gen)
    {
	randY = randY > 0 ? randY : 1;
	for (int i = 0; i < n; ++i)
	{
	    int x = rand.nextInt(16) + 8;
	    int y = rand.nextInt(randY) + baseY;
	    int z = rand.nextInt(16) + 8;
	    gen.generate(worldIn, rand, pos.add(x, y, z));
	}
    }
    
    public static BlockPos posToChunk(BlockPos pos)
    {
	return new BlockPos(pos.getX() / 16f, pos.getY(), pos.getZ() / 16f);
    }
    
    /**
     * Creates a Vec3 using the pitch and yaw of the entities rotation.
     * Taken from entity, so it can be used anywhere
     */
    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
}
