package com.barribob.MaelstromMod.entity.tileentity;

import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public abstract class MobSpawnerLogic {
    /** The delay to spawn. */
    protected int spawnDelay = 20;
    protected int maxNearbyEntities = 6;
    /** The distance from which a player activates the spawner. */
    protected int activatingRangeFromPlayer = 16;

    // tallies entities through their counts. The max count is the maximum, and the
    // count is the current amound
    protected int maxCount = 4;
    protected int count = 0;

    /** The range coefficient for spawning entities around. */
    protected int spawnRange = 4;
    protected Supplier<World> world;
    protected Supplier<BlockPos> pos;
    protected Block block;
    protected float level;
    protected MobSpawnData[] mobs = { new MobSpawnData(Reference.MOD_ID + ":dream_elk", Element.NONE) };
    protected int[] mobWeights = { 1 };

    /**
     * 
     * Stores data for more advanced spawner logic
     *
     * The count is the amound of spaces that spawning the mob takes up. So, if a certain mob is much more powerful than the other mobs, and not too many should be spawned, make its count higher.
     * 
     * Basically the formula is number of mobs spawned = spawner.maxCount / average MobSpawnData.count
     * 
     * The NBT is semi separate, and allows one to specify other attributes as well as the level and element (though not at random)
     */
    public static class MobSpawnData {
	public String mobId;
	int count;
	public Element[] possibleElements;
	public int[] elementalWeights;
	public NBTTagCompound mobData;

	/**
	 * Currently nbt is limited in that you can't specify the element
	 * 
	 * TODO: Rewrite the spawning to only use nbt tag compounds
	 * 
	 * @param entityData
	 */
	public MobSpawnData(NBTTagCompound entityData) {
	    this(entityData.getString("id"), Element.NONE);
	    this.mobData = entityData;
	}

	public MobSpawnData(String mobId, Element element) {
	    this(mobId, element, 1);
	}

	public MobSpawnData(String mobId, Element element, int count) {
	    this(mobId, new Element[] { element }, new int[] { 1 }, count);
	}

	public MobSpawnData(String mobId, Element[] possibleElements, int[] elementalWeights, int count) {
	    super();
	    this.mobId = mobId;

	    if (possibleElements.length != elementalWeights.length) {
		throw new IllegalArgumentException("Element and weight arrays not the same length.");
	    }

	    this.possibleElements = possibleElements;
	    this.elementalWeights = elementalWeights;
	    this.count = count;
	}
    };

    public MobSpawnerLogic(Supplier<World> world, Supplier<BlockPos> pos, Block block) {
	this.world = world;
	this.pos = pos;
	this.block = block;
    }

    public void setData(String mob, int maxCount, float level, int activationRange) {
	this.setData(new MobSpawnData(mob, Element.NONE, 1), maxCount, level, activationRange);
    }

    public void setData(MobSpawnData mob, int maxCount, float level, int activationRange) {
	this.setData(new MobSpawnData[] { mob }, new int[] { 1 }, maxCount, level, activationRange);
    }

    public void setData(MobSpawnData[] mobs, int[] mobWeights, int maxCount, float level, int activationRange) {
	if (mobs.length != mobWeights.length) {
	    throw new IllegalArgumentException("Mobs and weight arrays for spawner not the same length.");
	}
	this.mobs = mobs;
	this.mobWeights = mobWeights;
	this.activatingRangeFromPlayer = activationRange;
	this.maxCount = maxCount;
	this.level = level;
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
    protected boolean tooManyEntities(World world, Entity entity, BlockPos blockpos) {
	int k = world.getEntitiesWithinAABB(entity.getClass(), (new AxisAlignedBB(blockpos.getX(), blockpos.getY(), blockpos.getZ(),
		blockpos.getX() + 1, blockpos.getY() + 1, blockpos.getZ() + 1)).grow(this.spawnRange)).size();

	if (k >= this.maxNearbyEntities) {
	    return true;
	}
	return false;
    }

    protected boolean tryToSpawnEntity() {
	MobSpawnData data = getEntityData();
	// Get a random position
	int x = pos.get().getX() + MathHelper.getInt(world.get().rand, 0, this.spawnRange) * MathHelper.getInt(world.get().rand, -1, 1);
	int y = pos.get().getY() + MathHelper.getInt(world.get().rand, 0, this.spawnRange) * MathHelper.getInt(world.get().rand, -1, 1);
	int z = pos.get().getZ() + MathHelper.getInt(world.get().rand, 0, this.spawnRange) * MathHelper.getInt(world.get().rand, -1, 1);

	if (world.get().getBlockState(new BlockPos(x, y - 1, z)).isSideSolid(world.get(), new BlockPos(x, y - 1, z), net.minecraft.util.EnumFacing.UP)) {
	    Entity entity = ModUtils.createMobFromSpawnData(data, world.get(), x, y, z);

	    if (entity == null) {
		return false;
	    }

	    if (world.get().checkNoEntityCollision(entity.getEntityBoundingBox(), entity)
		    && world.get().getCollisionBoxes(entity, entity.getEntityBoundingBox()).isEmpty() && !world.get().containsAnyLiquid(entity.getEntityBoundingBox())
		    && !this.tooManyEntities(world.get(), entity, pos.get())) {
		EntityLiving entityliving = entity instanceof EntityLiving ? (EntityLiving) entity : null;

		if (entityliving != null) {
		    // A successful spawn of the entity
		    world.get().spawnEntity(entity);
		    world.get().playEvent(2004, pos.get(), 0);
		    entityliving.spawnExplosionParticle();

		    if (entity instanceof EntityLeveledMob) {
			EntityLeveledMob leveledMob = (EntityLeveledMob) entity;
			leveledMob.setElement(ModRandom.choice(data.possibleElements, this.world.get().rand, data.elementalWeights).next());
			leveledMob.setLevel(level);
		    }

		    this.count += data.count;

		    return true;
		}
	    }
	}
	return false;
    }

    public void readFromNBT(NBTTagCompound nbt) {
	this.spawnDelay = nbt.getShort("Delay");

	if (nbt.hasKey("MobWeights")) {
	    this.mobWeights = nbt.getIntArray("MobWeights");
	}

	if (nbt.hasKey("MaxCount")) {
	    this.maxCount = nbt.getShort("MaxCount");
	}

	if (nbt.hasKey("Level")) {
	    this.level = nbt.getFloat("Level");
	}

	if (nbt.hasKey("MaxNearbyEntities", 99)) {
	    this.maxNearbyEntities = nbt.getShort("MaxNearbyEntities");
	    this.activatingRangeFromPlayer = nbt.getShort("RequiredPlayerRange");
	}

	if (nbt.hasKey("SpawnRange", 99)) {
	    this.spawnRange = nbt.getShort("SpawnRange");
	}

	if (nbt.hasKey("MobSpawnData")) {
	    // 10 is for NBTTagCompound
	    NBTTagList nbttaglist = nbt.getTagList("MobSpawnData", 10);

	    this.mobs = new MobSpawnData[nbttaglist.tagCount()];
	    for (int i = 0; i < nbttaglist.tagCount(); i++) {
		NBTTagCompound compound = nbttaglist.getCompoundTagAt(i);

		int[] elementIds = compound.getIntArray("Elements");
		Element[] elements = new Element[elementIds.length];
		for (int j = 0; j < elementIds.length; j++) {
		    elements[j] = Element.getElementFromId(elementIds[j]);
		}

		MobSpawnData spawnData = new MobSpawnData(compound.getString("EntityId"), elements, compound.getIntArray("ElementWeights"), compound.getInteger("Count"));

		if (compound.hasKey("SpawnData")) {
		    spawnData.mobData = compound.getCompoundTag("SpawnData");
		}

		mobs[i] = spawnData;
	    }
	}

	if (this.mobs.length != this.mobWeights.length) {
	    System.err.println("Error loading spawner tile entity data.");

	    // Replace with a pig spawner
	    MobSpawnData[] brokenMobs = { new MobSpawnData("minecraft:pig", Element.NONE) };
	    int[] brokenMobWeights = { 1 };
	    this.mobs = brokenMobs;
	    this.mobWeights = brokenMobWeights;
	}
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound) {

	if (mobs == null) {
	    return compound;
	}
	compound.setIntArray("MobWeights", mobWeights);

	compound.setShort("Delay", (short) this.spawnDelay);
	compound.setShort("MaxNearbyEntities", (short) this.maxNearbyEntities);
	compound.setShort("RequiredPlayerRange", (short) this.activatingRangeFromPlayer);
	compound.setShort("SpawnRange", (short) this.spawnRange);
	compound.setShort("MaxCount", (short) this.maxCount);
	compound.setFloat("Level", this.level);

	NBTTagList nbttaglist = new NBTTagList();
	for (MobSpawnData data : this.mobs) {

	    NBTTagCompound dataCompound = new NBTTagCompound();
	    if (data.mobData != null) {
		dataCompound.setTag("SpawnData", data.mobData);
	    }

	    int[] elements = new int[data.possibleElements.length];
	    for (int i = 0; i < data.possibleElements.length; i++) {
		elements[i] = data.possibleElements[i].id;
	    }
	    dataCompound.setIntArray("Elements", elements);

	    dataCompound.setIntArray("ElementWeights", data.elementalWeights);

	    dataCompound.setString("EntityId", data.mobId);
	    dataCompound.setInteger("Count", data.count);
	    nbttaglist.appendTag(dataCompound);
	}

	compound.setTag("MobSpawnData", nbttaglist);
	return compound;
    }

    protected MobSpawnData getEntityData() {
	return ModRandom.choice(this.mobs, this.world.get().rand, this.mobWeights).next();
    }

    public void broadcastEvent(int id) {
	this.world.get().addBlockEvent(this.pos.get(), this.block, id, 0);
    }
}
