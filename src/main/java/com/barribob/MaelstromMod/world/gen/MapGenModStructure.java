package com.barribob.MaelstromMod.world.gen;

import java.util.Random;

import com.barribob.MaelstromMod.world.gen.mineshaft.AzureMineshaft;
import com.barribob.MaelstromMod.world.gen.mineshaft.MapGenAzureMineshaft;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.StructureStart;

public abstract class MapGenModStructure extends MapGenStructure
{
    private int spacing;
    private int offset;
    private int odds;

    public MapGenModStructure(int spacing, int offset, int odds)
    {
	this.spacing = spacing;
	this.offset = offset;
	this.odds = odds;
    }

    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
	return Math.abs(chunkX % spacing) == offset && Math.abs(chunkZ % spacing) == offset && rand.nextInt(odds) == 0;
    }

    public BlockPos getNearestStructurePos(World worldIn, BlockPos pos, boolean findUnexplored)
    {
	this.world = worldIn;
	return findNearestStructurePosBySpacing(worldIn, this, pos, 20, 11, 10387313, true, 100, findUnexplored);
    }
}
