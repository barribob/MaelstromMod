package com.barribob.MaelstromMod.world.dimension.dark_nexus;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.MobSpawnerLogic.MobSpawnData;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenDarkNexus extends WorldGenStructure
{
    public WorldGenDarkNexus()
    {
	super("nexus/dark_nexus");
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	this.generateStructure(worldIn, position, false);
	return true;
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
	worldIn.setBlockToAir(pos);
	if (function.startsWith("herobrine"))
	{
	    worldIn.setBlockState(pos, ModBlocks.NEXUS_HEROBRINE_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setData(new MobSpawnData(Reference.MOD_ID + ":herobrine_controller", Element.NONE), 1, 1.0f, 20);
	    }
	}
    }
}
