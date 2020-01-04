package com.barribob.MaelstromMod.world.gen.cliff;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;

import net.minecraft.tileentity.TileEntity;
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
public class HoleTempleTemplate extends ModStructureTemplate
{
    public HoleTempleTemplate()
    {
    }

    public HoleTempleTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rotation, int distance, boolean overwriteIn)
    {
	super(manager, type, pos, distance, rotation, overwriteIn);
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
    {
	worldIn.setBlockState(pos, ModBlocks.DISAPPEARING_SPAWNER.getDefaultState(), 2);
	TileEntity tileentity = worldIn.getTileEntity(pos);

	if (tileentity instanceof TileEntityMobSpawner)
	{
	    ((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(Reference.MOD_ID + ":beast", 1, 2.0f, 20);
	}
    }

    @Override
    public String templateLocation()
    {
	return "cliff";
    }
}
