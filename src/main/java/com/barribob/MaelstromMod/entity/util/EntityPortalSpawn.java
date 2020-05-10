package com.barribob.MaelstromMod.entity.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * An entity that spawns a portal from a key block
 */
public abstract class EntityPortalSpawn extends Entity
{
    private List<BlockPosTuple> portal;
    protected int blockPlaceTime = 3;
    private int counter;
    private static final byte PARTICLE_BYTE = 3;
    
    protected static class BlockPosTuple
    {
	public Block block;
	public BlockPos pos;
	
	public BlockPosTuple(Block block, BlockPos pos)
	{
	    this.block = block;
	    this.pos = pos;
	}
    }
    
    protected abstract void spawnParticles();
    protected abstract Block getRimBlock();
    protected abstract Block getPortalBlock();
    
    public EntityPortalSpawn(World worldIn)
    {
	super(worldIn);
	this.setNoGravity(true);
	portal = getPortal();
    }
    
    public EntityPortalSpawn(World worldIn, double x, double y, double z)
    {
	this(worldIn);
	this.setPosition(x, y, z);
    }

    /*
     * Lay out the blocks to be generated in order
     */
    protected List<BlockPosTuple> getPortal()
    {
	List<BlockPosTuple> list = new ArrayList<BlockPosTuple>();
	int offset = 2;
	int yOffset = -1;
	
	for(int x = -1; x < 6; x++)
	{
	    for(int y = -2; y < 6; y++)
	    {
		for(int z = -1; z < 6; z++)
		{
		    if((x <= 0 && z <= 0) || (x <= 0 && z >= 4) || (x >= 4 && z <= 0) || (x >= 4 && z >= 4))
		    {
			list.add(new BlockPosTuple(this.getRimBlock(), new BlockPos(x - offset, y + yOffset, z - offset)));
		    }
		}
	    }
	}
	
	for(int x = 0; x < 5; x++)
	{
	    for(int z = 0; z < 5; z++)
	    {
		if(x == 0 || z == 0 || x == 4 || z == 4)
		{
		    list.add(new BlockPosTuple(this.getRimBlock(), new BlockPos(x - offset, yOffset, z - offset)));
		}
	    }
	}
	
	for(int x = 1; x < 4; x++)
	{
	    for(int z = 1; z < 4; z++)
	    {
		list.add(new BlockPosTuple(this.getPortalBlock(), new BlockPos(x - offset, yOffset, z - offset)));
	    }
	}
	
	return list;
    }
    
    @Override
    public void onUpdate()
    {
	super.onUpdate();
	this.world.setEntityState(this, this.PARTICLE_BYTE);

	// Place the portal block by block
	if(this.ticksExisted % this.blockPlaceTime == 0 && counter < portal.size())
	{
	    world.setBlockState(portal.get(counter).pos.add(new BlockPos(this.posX, this.posY, this.posZ)), portal.get(counter).block.getDefaultState());
	    counter++;
	    if(counter == portal.size())
	    {
		this.setDead();
	    }
	}
    }
    
    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if(id == this.PARTICLE_BYTE)
	{
	    this.spawnParticles();
	}
        super.handleStatusUpdate(id);
    }
    
    @Override
    protected void entityInit()
    {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound)
    {
	if(compound.hasKey("counter"))
	{
	    counter = compound.getInteger("counter");
	}
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound)
    {	
	compound.setInteger("counter", counter);
    }
}
