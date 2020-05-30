package com.barribob.MaelstromMod.world.gen.cliff;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.world.gen.WorldGenCustomStructures.CliffMaelstromStructure;
import com.barribob.MaelstromMod.world.gen.WorldGenStructure;

import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WorldGenCliffStructureLedge extends WorldGenCliffLedge
{
    private static WorldGenStructure[] secondRuin = { new CliffMaelstromStructure("gazebo"), new CliffMaelstromStructure("brazier"),
	    new CliffMaelstromStructure("holy_tower"), new CliffMaelstromStructure("broken_arch"), new CliffMaelstromStructure("statue_of_nirvana") };

    public WorldGenCliffStructureLedge()
    {
	super("cliff/xxl_boardwalk", 10);
    }

    @Override
    public void generateStructure(World world, BlockPos pos, Rotation rotation)
    {
	super.generateStructure(world, pos, rotation);
	if (world.rand.nextInt(2) == 0)
	{
	    WorldGenStructure ruin = ModRandom.choice(secondRuin);
	    BlockPos offset = this.getCenter(world).subtract(ruin.getCenter(world));
	    ruin.generate(world, world.rand, pos.add(offset.getX(), this.getSize(world).getY(), offset.getZ()));
	}
    }
}
