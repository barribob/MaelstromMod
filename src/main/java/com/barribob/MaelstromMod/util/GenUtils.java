package com.barribob.MaelstromMod.util;

import com.barribob.MaelstromMod.world.dimension.WorldChunkGenerator;
import com.barribob.MaelstromMod.world.gen.ModStructureTemplate;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class GenUtils
{
    public static int getGroundHeight(ModStructureTemplate template, WorldChunkGenerator gen, Rotation rotation)
    {
	StructureBoundingBox box = template.getBoundingBox();
	int corner1 = getGroundHeight(new BlockPos(box.maxX, 0, box.maxZ), gen, rotation);
	int corner2 = getGroundHeight(new BlockPos(box.minX, 0, box.maxZ), gen, rotation);
	int corner3 = getGroundHeight(new BlockPos(box.maxX, 0, box.minZ), gen, rotation);
	int corner4 = getGroundHeight(new BlockPos(box.minX, 0, box.minZ), gen, rotation);
	return Math.min(Math.min(corner3, corner4), Math.max(corner2, corner1));
    }

    /*
     * From MapGenEndCity: determines the ground height
     */
    public static int getGroundHeight(BlockPos pos, WorldChunkGenerator gen, Rotation rotation)
    {
	BlockPos chunk = ModUtils.posToChunk(pos);
	ChunkPrimer chunkprimer = new ChunkPrimer();
	gen.setBlocksInChunk(chunk.getX(), chunk.getZ(), chunkprimer);
	int i = 5;
	int j = 5;

	if (rotation == Rotation.CLOCKWISE_90)
	{
	    i = -5;
	}
	else if (rotation == Rotation.CLOCKWISE_180)
	{
	    i = -5;
	    j = -5;
	}
	else if (rotation == Rotation.COUNTERCLOCKWISE_90)
	{
	    j = -5;
	}

	int k = chunkprimer.findGroundBlockIdx(7, 7);
	int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
	int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
	int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
	int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
	return k1;
    }
}
