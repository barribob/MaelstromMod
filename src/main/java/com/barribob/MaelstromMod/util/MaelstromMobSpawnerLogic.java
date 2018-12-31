package com.barribob.MaelstromMod.util;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StringUtils;
import net.minecraft.util.WeightedRandom;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.storage.AnvilChunkLoader;

/**
 * 
 * The tile entity spawner logic for the maelstrom core. Has similar
 * functionality to the vanilla minecraft mob spawner
 *
 */
public abstract class MaelstromMobSpawnerLogic
{
    /** The delay to spawn. */
    private int spawnDelay = 20;
    /** List of potential entities to spawn */
    private final List<WeightedSpawnerEntity> potentialSpawns = Lists.<WeightedSpawnerEntity>newArrayList();
    private WeightedSpawnerEntity spawnData = new WeightedSpawnerEntity();
    private int minSpawnDelay = 600;
    private int maxSpawnDelay = 800;
    private int spawnCount = 4;
    /** Cached instance of the entity to render inside the spawner. */
    private Entity cachedEntity;
    private int maxNearbyEntities = 6;
    /** The distance from which a player activates the spawner. */
    private int activatingRangeFromPlayer = 16;
    /** The range coefficient for spawning entities around. */
    private int spawnRange = 4;

    @Nullable
    private ResourceLocation getEntityId()
    {
	String s = this.spawnData.getNbt().getString("id");
	return StringUtils.isNullOrEmpty(s) ? null : new ResourceLocation(s);
    }

    public void setEntityId(@Nullable ResourceLocation id)
    {
	if (id != null)
	{
	    this.spawnData.getNbt().setString("id", id.toString());
	}
    }

    /**
     * Returns true if there's a player close enough to this mob spawner to activate
     * it.
     */
    private boolean isActivated()
    {
	BlockPos blockpos = this.getSpawnerPosition();
	return this.getSpawnerWorld().isAnyPlayerWithinRangeAt((double) blockpos.getX() + 0.5D, (double) blockpos.getY() + 0.5D,
		(double) blockpos.getZ() + 0.5D, (double) this.activatingRangeFromPlayer);
    }

    public void updateSpawner()
    {

	BlockPos blockpos = this.getSpawnerPosition();

	// Currently does not deal with any server stuff, although this might be a
	// mistake, so potentially this may have to revert back to the vanilla logic
	if (this.getSpawnerWorld().isRemote || !this.isActivated())
	{
	    return;
	}

	if (this.spawnDelay == -1)
	{
	    this.resetTimer();
	}

	if (this.spawnDelay > 0)
	{
	    --this.spawnDelay;
	    return;
	}

	boolean flag = false;
	for (int i = 0; i < this.spawnCount; i++)
	{
	    // Try multiple times to spawn the entity in a good spot
	    int tries = 20;
	    for (int t = 0; t < tries; t++)
	    {
		NBTTagCompound nbttagcompound = this.spawnData.getNbt();
		NBTTagList nbttaglist = nbttagcompound.getTagList("Pos", 6);
		World world = this.getSpawnerWorld();

		// Get a random position
		int i1 = blockpos.getX() + MathHelper.getInt(world.rand, 0, this.spawnRange) * MathHelper.getInt(world.rand, -1, 1);
		int j1 = blockpos.getY() + MathHelper.getInt(world.rand, 0, this.spawnRange) * MathHelper.getInt(world.rand, -1, 1);
		int k1 = blockpos.getZ() + MathHelper.getInt(world.rand, 0, this.spawnRange) * MathHelper.getInt(world.rand, -1, 1);

		if (world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(world, new BlockPos(i1, j1 - 1, k1),
			net.minecraft.util.EnumFacing.UP))
		{
		    // Gets the entity data?
		    Entity entity = AnvilChunkLoader.readWorldEntityPos(nbttagcompound, world, i1, j1, k1, false);

		    if (entity != null && world.checkNoEntityCollision(entity.getEntityBoundingBox(), entity)
			    && world.getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty()
			    && !world.containsAnyLiquid(entity.getEntityBoundingBox()) && !this.tooManyEntities(world, entity, blockpos))
		    {
			EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;
			entity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, world.rand.nextFloat() * 360.0F, 0.0F);

			if (entityliving != null)
			{
			    if (this.spawnData.getNbt().getSize() == 1 && this.spawnData.getNbt().hasKey("id", 8) && entity instanceof EntityLiving)
			    {
				((EntityLiving) entity).onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entity)),
					(IEntityLivingData) null);
			    }

			    // A successful spawn of the entity
			    AnvilChunkLoader.spawnEntity(entity, world);
			    world.playEvent(2004, blockpos, 0);
			    entityliving.spawnExplosionParticle();
			    break;
			}
		    }
		}
	    }
	}

	this.resetTimer();
    }

    /**
     * If there are too many entities of a type in a certain area
     * @param world
     * @param entity
     * @param blockpos
     * @return
     */
    private boolean tooManyEntities(World world, Entity entity, BlockPos blockpos)
    {
	int k = world
		.getEntitiesWithinAABB(entity.getClass(),
			(new AxisAlignedBB((double) blockpos.getX(), (double) blockpos.getY(), (double) blockpos.getZ(),
				(double) (blockpos.getX() + 1), (double) (blockpos.getY() + 1), (double) (blockpos.getZ() + 1)))
					.grow((double) this.spawnRange))
		.size();

	if (k >= this.maxNearbyEntities)
	{
	    return true;
	}
	return false;
    }

    /**
     * Resets the timer back to a random number between max and min spawn delay
     */
    private void resetTimer()
    {
	if (this.maxSpawnDelay <= this.minSpawnDelay)
	{
	    this.spawnDelay = this.minSpawnDelay;
	}
	else
	{
	    int i = this.maxSpawnDelay - this.minSpawnDelay;
	    this.spawnDelay = this.minSpawnDelay + this.getSpawnerWorld().rand.nextInt(i);
	}

	if (!this.potentialSpawns.isEmpty())
	{
	    this.setNextSpawnData((WeightedSpawnerEntity) WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.potentialSpawns));
	}

	this.broadcastEvent(1);
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
	this.spawnDelay = nbt.getShort("Delay");
	this.potentialSpawns.clear();

	if (nbt.hasKey("SpawnPotentials", 9))
	{
	    NBTTagList nbttaglist = nbt.getTagList("SpawnPotentials", 10);

	    for (int i = 0; i < nbttaglist.tagCount(); ++i)
	    {
		this.potentialSpawns.add(new WeightedSpawnerEntity(nbttaglist.getCompoundTagAt(i)));
	    }
	}

	if (nbt.hasKey("SpawnData", 10))
	{
	    this.setNextSpawnData(new WeightedSpawnerEntity(1, nbt.getCompoundTag("SpawnData")));
	}
	else if (!this.potentialSpawns.isEmpty())
	{
	    this.setNextSpawnData((WeightedSpawnerEntity) WeightedRandom.getRandomItem(this.getSpawnerWorld().rand, this.potentialSpawns));
	}

	if (nbt.hasKey("MinSpawnDelay", 99))
	{
	    this.minSpawnDelay = nbt.getShort("MinSpawnDelay");
	    this.maxSpawnDelay = nbt.getShort("MaxSpawnDelay");
	    this.spawnCount = nbt.getShort("SpawnCount");
	}

	if (nbt.hasKey("MaxNearbyEntities", 99))
	{
	    this.maxNearbyEntities = nbt.getShort("MaxNearbyEntities");
	    this.activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange");
	}

	if (nbt.hasKey("SpawnRange", 99))
	{
	    this.spawnRange = nbt.getShort("SpawnRange");
	}

	if (this.getSpawnerWorld() != null)
	{
	    this.cachedEntity = null;
	}
    }

    public NBTTagCompound writeToNBT(NBTTagCompound p_189530_1_)
    {
	ResourceLocation resourcelocation = this.getEntityId();

	if (resourcelocation == null)
	{
	    return p_189530_1_;
	}
	else
	{
	    p_189530_1_.setShort("Delay", (short) this.spawnDelay);
	    p_189530_1_.setShort("MinSpawnDelay", (short) this.minSpawnDelay);
	    p_189530_1_.setShort("MaxSpawnDelay", (short) this.maxSpawnDelay);
	    p_189530_1_.setShort("SpawnCount", (short) this.spawnCount);
	    p_189530_1_.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
	    p_189530_1_.setShort("RequiredPlayerRange", (short) this.activatingRangeFromPlayer);
	    p_189530_1_.setShort("SpawnRange", (short) this.spawnRange);
	    p_189530_1_.setTag("SpawnData", this.spawnData.getNbt().copy());
	    NBTTagList nbttaglist = new NBTTagList();

	    if (this.potentialSpawns.isEmpty())
	    {
		nbttaglist.appendTag(this.spawnData.toCompoundTag());
	    }
	    else
	    {
		for (WeightedSpawnerEntity weightedspawnerentity : this.potentialSpawns)
		{
		    nbttaglist.appendTag(weightedspawnerentity.toCompoundTag());
		}
	    }

	    p_189530_1_.setTag("SpawnPotentials", nbttaglist);
	    return p_189530_1_;
	}
    }
    
    public void setNextSpawnData(WeightedSpawnerEntity p_184993_1_)
    {
	this.spawnData = p_184993_1_;
    }

    public abstract void broadcastEvent(int id);

    public abstract World getSpawnerWorld();

    public abstract BlockPos getSpawnerPosition();

    /*
     * ======================================== FORGE START
     * =====================================
     */
    @Nullable
    public Entity getSpawnerEntity()
    {
	return null;
    }
}