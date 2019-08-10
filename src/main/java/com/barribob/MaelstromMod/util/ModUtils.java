package com.barribob.MaelstromMod.util;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
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
     * 
     * @param n
     * @param func
     */
    public static void performNTimes(int n, Consumer<Integer> func)
    {
	for (int i = 0; i < n; i++)
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

	if (list != null)
	{
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase) i;

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
     * Creates a Vec3 using the pitch and yaw of the entities rotation. Taken from
     * entity, so it can be used anywhere
     */
    public static Vec3d getVectorForRotation(float pitch, float yaw)
    {
	float f = MathHelper.cos(-yaw * 0.017453292F - (float) Math.PI);
	float f1 = MathHelper.sin(-yaw * 0.017453292F - (float) Math.PI);
	float f2 = -MathHelper.cos(-pitch * 0.017453292F);
	float f3 = MathHelper.sin(-pitch * 0.017453292F);
	return new Vec3d((double) (f1 * f2), (double) f3, (double) (f * f2));
    }

    public static Vec3d yVec(float y)
    {
	return new Vec3d(0, y, 0);
    }

    public static void makeExplosion(float radius, float maxDamage, EntityLivingBase source, Vec3d pos)
    {
	for (int i = 0; i < Math.floor(Math.pow(radius, 2)); i++)
	{
	    ParticleManager.spawnMaelstromExplosion(source.world, source.world.rand, pos.add(ModRandom.randVec().scale(radius)));
	    ParticleManager.spawnMaelstromSmoke(source.world, source.world.rand, pos.add(ModRandom.randVec().scale(radius)), true);
	}

	source.world.playSound((EntityPlayer) null, source.posX, source.posY, source.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, source.getSoundCategory(), 1.0F, 0.9F);

	List<Entity> list = source.world.getEntitiesWithinAABBExcludingEntity(source, new AxisAlignedBB(pos, pos).grow(radius));

	if (list != null)
	{
	    Predicate<Entity> isInstance = i -> i instanceof EntityLivingBase;
	    Function<Entity, EntityLivingBase> cast = i -> (EntityLivingBase) i;

	    list.stream().filter(isInstance).map(cast).forEach((entity) -> {
		double radiusSq = Math.pow(radius, 2);
		float distanceFromExplosion = (float) entity.getDistanceSq(new BlockPos(pos));
		float damage = (float) (maxDamage - distanceFromExplosion);
		if (damage > 0 && distanceFromExplosion < radiusSq)
		{
		    entity.attackEntityFrom(DamageSource.causeExplosionDamage(source), damage);
		    Vec3d velocity = entity.getPositionVector().subtract(pos).normalize().scale((radiusSq - distanceFromExplosion) / radiusSq);
		    entity.addVelocity(velocity.x, velocity.y, velocity.z);
		}
	    });
	}
    }
    
    public static void throwProjectile(EntityLeveledMob actor, EntityLivingBase target, Projectile projectile)
    {
        double d0 = target.posY + (double)target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - actor.posX;
        double d2 = d0 - projectile.posY;
        double d3 = target.posZ - actor.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        projectile.shoot(d1, d2 + (double)f, d3, 1.6F, 12.0F);
        actor.world.spawnEntity(projectile);	
    }
}
