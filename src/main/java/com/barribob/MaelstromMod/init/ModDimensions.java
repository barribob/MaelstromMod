package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.world.dimension.azure_dimension.DimensionAzure;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions
{
    public static final DimensionType AZURE = DimensionType.register("azure", "_azure", ModConfig.fracture_dimension_id, DimensionAzure.class, false);

    public static void registerDimensions()
    {
	DimensionManager.registerDimension(ModConfig.fracture_dimension_id, AZURE);
    }
}
