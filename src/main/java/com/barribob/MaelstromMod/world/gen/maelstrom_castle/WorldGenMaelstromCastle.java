package com.barribob.MaelstromMod.world.gen.maelstrom_castle;

import java.util.Map;
import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.entities.TileEntityMalestromSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * 
 * The world generator for the maelstrom castle, which just offsets it a bit, and replaces certain data blocks
 *
 */
public class WorldGenMaelstromCastle extends WorldGenStructure
{
    public WorldGenMaelstromCastle(String name)
    {
	super(name);
    }
    
    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	if(position.getY() < 50)
	{
	    return false;
	}
	
        return super.generate(worldIn, rand, position.add(new BlockPos(0, -25, 0)));
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
	if (function.startsWith("chest"))
	{
	    worldIn.setBlockToAir(pos);
	    BlockPos blockpos = pos.down();

	    TileEntity tileentity = worldIn.getTileEntity(blockpos);

	    if (tileentity instanceof TileEntityChest)
	    {
		((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_STRONGHOLD_CORRIDOR, rand.nextLong());
	    }
	}
	else if (function.startsWith("portal"))
	{
	    worldIn.setBlockState(pos, ModBlocks.AZURE_PORTAL.getDefaultState(), 2);
	}
	else if (function.startsWith("enemy"))
	{
	    worldIn.setBlockState(pos, ModBlocks.AZURE_MAELSTROM_CORE.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMalestromSpawner)
	    {
		String entityName = rand.nextInt(2) == 0 ? "shade" : "horror";
		((TileEntityMalestromSpawner) tileentity).getSpawnerBaseLogic().setEntityId(new ResourceLocation(Reference.MOD_ID + ":" + entityName));
	    }

	}
    }
}
