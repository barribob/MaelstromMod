package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.world.dimension.azure_dimension.DimensionAzure;
import com.barribob.MaelstromMod.world.dimension.cliff.DimensionCliff;
import com.barribob.MaelstromMod.world.dimension.nexus.DimensionNexus;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions
{
    public static final DimensionType AZURE = DimensionType.register("azure", "_azure", ModConfig.world.fracture_dimension_id, DimensionAzure.class, false);
    public static final DimensionType NEXUS = DimensionType.register("nexus", "_nexus", ModConfig.world.nexus_dimension_id, DimensionNexus.class, false);
    public static final DimensionType CLIFF = DimensionType.register("cliff", "_cliff", ModConfig.world.cliff_dimension_id, DimensionCliff.class, false);

    public static void registerDimensions()
    {
	DimensionManager.registerDimension(ModConfig.world.fracture_dimension_id, AZURE);
	DimensionManager.registerDimension(ModConfig.world.nexus_dimension_id, NEXUS);
	DimensionManager.registerDimension(ModConfig.world.cliff_dimension_id, CLIFF);
    }
}
