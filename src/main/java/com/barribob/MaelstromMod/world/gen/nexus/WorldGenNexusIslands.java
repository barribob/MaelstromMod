package com.barribob.MaelstromMod.world.gen.nexus;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class WorldGenNexusIslands extends WorldGenStructure
{
    public WorldGenNexusIslands()
    {
	super("nexus/nexus_islands");
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	this.generateStructure(worldIn, position, Rotation.NONE);
	return true;
    }

    @Override
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand)
    {
	worldIn.setBlockToAir(pos);
	if (function.startsWith("teleport"))
	{
	    worldIn.setBlockState(pos, ModBlocks.NEXUS_TELEPORTER.getDefaultState());
	    String[] params = function.split(" ");
	    Vec3d relTeleport = new Vec3d(Integer.parseInt(params[1]) + 0.5, Integer.parseInt(params[2]), Integer.parseInt(params[3]) + 0.5);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityTeleporter)
	    {
		((TileEntityTeleporter) tileentity).setRelTeleportPos(relTeleport);
	    }
	}
	else if (function.startsWith("mage"))
	{
	    ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_mage"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	}
	else if (function.startsWith("saiyan"))
	{
	    ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_saiyan"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	}
	else if (function.startsWith("bladesmith"))
	{
	    ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_bladesmith"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	}
	else if (function.startsWith("armorer"))
	{
	    ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_armorer"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	}
	else if (function.startsWith("gunsmith"))
	{
	    ItemMonsterPlacer.spawnCreature(worldIn, new ResourceLocation(Reference.MOD_ID + ":nexus_gunsmith"), pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
	}
    }
}
