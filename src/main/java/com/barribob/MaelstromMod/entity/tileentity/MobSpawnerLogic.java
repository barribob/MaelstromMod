package com.barribob.MaelstromMod.entity.tileentity;

import java.util.List;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
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

public abstract class MobSpawnerLogic
{
    /** The delay to spawn. */
    protected int spawnDelay = 20;
    /** List of potential entities to spawn */
    protected final List<WeightedSpawnerEntity> potentialSpawns = Lists.<WeightedSpawnerEntity>newArrayList();
    protected WeightedSpawnerEntity spawnData = new WeightedSpawnerEntity();
    protected int spawnCount = 4;
    /** Cached instance of the entity to render inside the spawner. */
    protected Entity cachedEntity;
    protected int maxNearbyEntities = 6;
    /** The distance from which a player activates the spawner. */
    protected int activatingRangeFromPlayer = 16;
    /** The range coefficient for spawning entities around. */
    protected int spawnRange = 4;
    protected Supplier<World> world;
    protected Supplier<BlockPos> pos;
    protected Block block;

    public MobSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block)
    {
	this.world = world;
	this.pos = pos;
	this.block = block;
    }

    public void setEntities(@Nullable ResourceLocation id, int count)
    {
	if (id != null)
	{
	    this.spawnData.getNbt().setString("id", id.toString());
	    this.spawnCount = count;
	}
    }

    public abstract void updateSpawner();
    
    /**
     * If there are too many entities of a type in a certain area
     * 
     * @param world
     * @param entity
     * @param blockpos
     * @return
     */
    protected boolean tooManyEntities(World world, Entity entity, BlockPos blockpos)
    {
	int k = world.getEntitiesWithinAABB(entity.getClass(), (new AxisAlignedBB(blockpos.getX(), blockpos.getY(), blockpos.getZ(),
		blockpos.getX() + 1, blockpos.getY() + 1, blockpos.getZ() + 1)).grow(this.spawnRange)).size();

	if (k >= this.maxNearbyEntities)
	{
	    return true;
	}
	return false;
    }
    
    protected boolean tryToSpawnEntity()
    {
	NBTTagCompound nbttagcompound = this.spawnData.getNbt();
	NBTTagList nbttaglist = nbttagcompound.getTagList("Pos", 6);

	// Get a random position
	int i1 = pos.get().getX() + MathHelper.getInt(world.get().rand, 0, this.spawnRange) * MathHelper.getInt(world.get().rand, -1, 1);
	int j1 = pos.get().getY() + MathHelper.getInt(world.get().rand, 0, this.spawnRange) * MathHelper.getInt(world.get().rand, -1, 1);
	int k1 = pos.get().getZ() + MathHelper.getInt(world.get().rand, 0, this.spawnRange) * MathHelper.getInt(world.get().rand, -1, 1);

	if (world.get().getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(world.get(), new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP))
	{
	    // Gets the entity data?
	    Entity entity = EntityList.createEntityByIDFromName(this.getEntityId(), world.get());
	    entity.setLocationAndAngles(i1, j1, k1, world.get().rand.nextFloat() * 360.0F, 0.0F);

	    if (entity != null && world.get().checkNoEntityCollision(entity.getEntityBoundingBox(), entity)
		    && world.get().getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !world.get().containsAnyLiquid(entity.getEntityBoundingBox())
		    && !this.tooManyEntities(world.get(), entity, pos.get()))
	    {
		EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;

		if (entityliving != null)
		{
		    if (this.spawnData.getNbt().getSize() == 1 && this.spawnData.getNbt().hasKey("id", 8) && entity instanceof EntityLiving)
		    {
			((EntityLiving) entity).onInitialSpawn(world.get().getDifficultyForLocation(new BlockPos(entity)), (IEntityLivingData) null);
		    }

		    // A successful spawn of the entity
		    world.get().spawnEntity(entity);
		    world.get().playEvent(2004, pos.get(), 0);
		    entityliving.spawnExplosionParticle();
		    return true;
		}
	    }
	}
	return false;
    }

    public void readFromNBT(NBTTagCompound nbt)
    {
	this.spawnDelay = nbt.getShort("Delay");
	this.spawnCount = nbt.getShort("SpawnCount");
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
	    this.setNextSpawnData(WeightedRandom.getRandomItem(this.world.get().rand, this.potentialSpawns));
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

	if (this.world.get() != null)
	{
	    this.cachedEntity = null;
	}
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
	ResourceLocation resourcelocation = this.getEntityId();

	if (resourcelocation == null)
	{
	    return compound;
	}
	else
	{
	    compound.setShort("Delay", (short) this.spawnDelay);
	    compound.setShort("SpawnCount", (short) this.spawnCount);
	    compound.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
	    compound.setShort("RequiredPlayerRange", (short) this.activatingRangeFromPlayer);
	    compound.setShort("SpawnRange", (short) this.spawnRange);
	    compound.setTag("SpawnData", this.spawnData.getNbt().copy());
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

	    compound.setTag("SpawnPotentials", nbttaglist);
	    return compound;
	}
    }

    public void setNextSpawnData(WeightedSpawnerEntity entity)
    {
	this.spawnData = entity;

	if (this.world.get() != null)
	{
	    IBlockState iblockstate = this.world.get().getBlockState(this.pos.get());
	    this.world.get().notifyBlockUpdate(this.pos.get(), iblockstate, iblockstate, 4);
	}
    }

    @Nullable
    protected ResourceLocation getEntityId()
    {
	String s = this.spawnData.getNbt().getString("id");
	return StringUtils.isNullOrEmpty(s) ? null : new ResourceLocation(s);
    }

    public void broadcastEvent(int id)
    {
	this.world.get().addBlockEvent(this.pos.get(), this.block, id, 0);
    }
}
