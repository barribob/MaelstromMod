package com.barribob.MaelstromMod.world.gen.nexus;

import java.util.Random;

import com.barribob.MaelstromMod.entity.tileentity.TileEntityDisappearingSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityMobSpawner;
import com.barribob.MaelstromMod.entity.tileentity.TileEntityTeleporter;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * The specific template used for generating the nexus template
 *
 */
public class NexusTemplate extends ModStructureTemplate
{
    public NexusTemplate()
    {
    }

    public NexusTemplate(TemplateManager manager, String type, BlockPos pos, Rotation rotation, boolean overwriteIn)
    {
	super(manager, type, pos, rotation, overwriteIn);
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
    {
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
	else if(function.startsWith("herobrine"))
	{
	    worldIn.setBlockState(pos, ModBlocks.NEXUS_HEROBRINE_SPAWNER.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMobSpawner)
	    {
		((TileEntityMobSpawner) tileentity).getSpawnerBaseLogic().setEntities(new ResourceLocation(Reference.MOD_ID + ":herobrine_controller"), 1);
	    }
	}
	
    }

    @Override
    public String templateLocation()
    {
	return "nexus";
    }
}
