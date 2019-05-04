package com.barribob.MaelstromMod.entity.tileentity;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.init.ModBlocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.MobSpawnerBaseLogic;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.WeightedSpawnerEntity;
import net.minecraft.util.datafix.DataFixer;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IDataFixer;
import net.minecraft.util.datafix.IDataWalker;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 
 * The tile entity for spawning maelstrom mobs, a one time spawner that sets
 * itself to air
 * 
 * NOTE: Because mincraft uses .newInstance() to instantiate the tile entities,
 * contructors with arguments don't work :(
 *
 */
public abstract class TileEntityMobSpawner extends TileEntity implements ITickable
{
    private final MobSpawnerLogic spawnerLogic = this.getSpawnerLogic();

    public void readFromNBT(NBTTagCompound compound)
    {
	super.readFromNBT(compound);
	this.spawnerLogic.readFromNBT(compound);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound compound)
    {
	super.writeToNBT(compound);
	this.spawnerLogic.writeToNBT(compound);
	return compound;
    }

    /**
     * Like the old updateEntity(), except more generic.
     */
    public void update()
    {
	this.spawnerLogic.updateSpawner();
    }

    @Nullable
    public SPacketUpdateTileEntity getUpdatePacket()
    {
	return new SPacketUpdateTileEntity(this.pos, 1, this.getUpdateTag());
    }

    public NBTTagCompound getUpdateTag()
    {
	NBTTagCompound nbttagcompound = this.writeToNBT(new NBTTagCompound());
	nbttagcompound.removeTag("SpawnPotentials");
	return nbttagcompound;
    }

    public boolean onlyOpsCanSetNbt()
    {
	return true;
    }

    /*
     * Override this to specify what kind of logic you want to use
     */
    protected abstract MobSpawnerLogic getSpawnerLogic();

    public MobSpawnerLogic getSpawnerBaseLogic()
    {
	return this.spawnerLogic;
    }
}