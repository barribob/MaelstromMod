package com.barribob.MaelstromMod.entity.entities;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.MaelstromMobSpawnerLogic;

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
 * The tile entity for spawning maelstrom mobs
 *
 */
public class TileEntityMalestromSpawner extends TileEntity implements ITickable
{
    /**
     * All of the logic for spawning the mobs is contained within
     * MaelstromMobSpawnerLogic, which is based on the original minecraft mob
     * spawner logic
     */
    private final MaelstromMobSpawnerLogic spawnerLogic = new MaelstromMobSpawnerLogic()
    {
	public void broadcastEvent(int id)
	{
	    TileEntityMalestromSpawner.this.world.addBlockEvent(TileEntityMalestromSpawner.this.pos, ModBlocks.AZURE_MAELSTROM_CORE, id, 0);
	}

	public World getSpawnerWorld()
	{
	    return TileEntityMalestromSpawner.this.world;
	}

	public BlockPos getSpawnerPosition()
	{
	    return TileEntityMalestromSpawner.this.pos;
	}

	public void setNextSpawnData(WeightedSpawnerEntity p_184993_1_)
	{
	    super.setNextSpawnData(p_184993_1_);

	    if (this.getSpawnerWorld() != null)
	    {
		IBlockState iblockstate = this.getSpawnerWorld().getBlockState(this.getSpawnerPosition());
		this.getSpawnerWorld().notifyBlockUpdate(TileEntityMalestromSpawner.this.pos, iblockstate, iblockstate, 4);
	    }
	}
    };

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

    public MaelstromMobSpawnerLogic getSpawnerBaseLogic()
    {
	return this.spawnerLogic;
    }
}