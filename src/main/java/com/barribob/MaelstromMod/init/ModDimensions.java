package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.dimension.azure_dimension.DimensionAzure;

import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class ModDimensions
{
    public static final int DIMENSION_AZURE_ID = 125;
    public static final DimensionType AZURE = DimensionType.register("azure", "_azure", DIMENSION_AZURE_ID, DimensionAzure.class, false);

    public static void registerDimensions()
    {
	DimensionManager.registerDimension(DIMENSION_AZURE_ID, AZURE);
    }
}
