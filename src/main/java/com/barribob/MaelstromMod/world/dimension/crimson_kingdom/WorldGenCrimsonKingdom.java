package com.barribob.MaelstromMod.world.dimension.crimson_kingdom;

import java.util.Random;

import com.barribob.MaelstromMod.util.IStructure;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenCrimsonKingdom extends WorldGenerator implements IStructure
{
    public static final int SLICES = 32;

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
	for (int slice = 0; slice < SLICES; slice++)
	{
	    WorldGenStructure structure = new WorldGenStructure("crimson_kingdom/crimson_kingdom_" + slice)
	    {
		@Override
		public boolean generate(World worldIn, Random rand, BlockPos position)
		{
		    generateStructure(worldIn, position, Rotation.NONE);
		    return true;
		}
	    };
	    structure.generate(worldIn, rand, position);
	    position = position.up(structure.getSize(worldIn).getY());
	}
	return true;
    }
}
