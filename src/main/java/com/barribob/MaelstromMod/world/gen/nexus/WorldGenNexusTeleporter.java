package com.barribob.MaelstromMod.world.gen.nexus;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WorldGenNexusTeleporter extends WorldGenStructure
{
    Vec3d offset;

    public WorldGenNexusTeleporter(Vec3d offset)
    {
	super("nexus/nexus_teleporter");
	this.offset = offset;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position, Rotation rotation)
    {
	generateStructure(worldIn, position, rotation);
	return true;
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World world, Random rand)
    {
	if (function.startsWith("teleporter"))
	{
	    world.setBlockState(pos, ModBlocks.NEXUS_TELEPORTER.getDefaultState());
	    TileEntity tileentity = world.getTileEntity(pos);
	    if (tileentity instanceof TileEntityTeleporter)
	    {
		((TileEntityTeleporter) tileentity).setRelTeleportPos(offset);
	    }
	}
    }
}
