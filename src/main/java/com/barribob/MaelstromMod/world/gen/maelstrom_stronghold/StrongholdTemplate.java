package com.barribob.MaelstromMod.world.gen.maelstrom_stronghold;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * The specific template used for generating the maelstrom fortress
 *
 */
public class StrongholdTemplate extends ModStructureTemplate
{
    public StrongholdTemplate()
    {
    }

    public StrongholdTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rotation, int distance, boolean overwriteIn)
    {
	super(manager, type, pos, distance, rotation, overwriteIn);
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
    {
	if (function.startsWith("chest"))
	{
	    worldIn.setBlockToAir(pos);
	    BlockPos blockpos = pos.down();

	    if (sbb.isVecInside(blockpos))
	    {
		TileEntity tileentity = worldIn.getTileEntity(blockpos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.AZURE_FORTRESS, rand.nextLong());
		}
	    }
	}
	else if (function.startsWith("random_chest"))
	{
	    worldIn.setBlockToAir(pos);
	    BlockPos blockpos = pos.down();

	    if (worldIn.rand.nextFloat() < 0.35f)
	    {
		if (sbb.isVecInside(blockpos))
		{
		    TileEntity tileentity = worldIn.getTileEntity(blockpos);

		    if (tileentity instanceof TileEntityChest)
		    {
			((TileEntityChest) tileentity).setLootTable(LootTableHandler.AZURE_FORTRESS, rand.nextLong());
		    }
		}
	    }
	    else
	    {
		worldIn.setBlockToAir(blockpos);
	    }
	}
	else if (function.startsWith("final_chest"))
	{
	    worldIn.setBlockToAir(pos);
	    BlockPos blockpos = pos.down();

	    if (sbb.isVecInside(blockpos))
	    {
		TileEntity tileentity = worldIn.getTileEntity(blockpos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.STRONGHOLD_KEY_CHEST, rand.nextLong());
		}
	    }
	}
	else if (function.startsWith("enemy"))
	{
	    worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		String[] entities = { "shade", "horror", "floating_skull" };
		String entityName = ModRandom.choice(entities);
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":" + entityName), ModRandom.range(1, 4));
	    }
	}
	else if (function.startsWith("boss"))
	{
	    worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":beast"), 1);
	    }
	}
    }

    @Override
    public String templateLocation()
    {
	return "maelstrom_stronghold";
    }
}
