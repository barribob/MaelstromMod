package com.barribob.MaelstromMod.world.gen.maelstrom_fortress;

import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
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
public class FortressTemplate extends ModStructureTemplate
{
    private int distance;

    public FortressTemplate()
    {
    }

    public FortressTemplate(TemplateManager manager, String type, int distance, BlockPos pos, Rotation rotation, boolean overwriteIn)
    {
	super(manager, type, pos, rotation, overwriteIn);
	this.distance = distance;
    }

    public int getDistance()
    {
	return this.distance;
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
    {
	if (function.startsWith("chest"))
	{
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
	else if (function.startsWith("boss"))
	{
	    EntityMaelstromIllager entity = new EntityMaelstromIllager(worldIn);
	    entity.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
	    worldIn.spawnEntity(entity);
	}
	else if (function.startsWith("enemy"))
	{
	    worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		String[] entities = {"shade", "horror", "maelstrom_mage"};
		String entityName = ModRandom.choice(entities);
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":" + entityName), ModRandom.range(2, 3));
	    }

	}
    }

    @Override
    public String templateLocation()
    {
	return "maelstrom_fortress";
    }
}
