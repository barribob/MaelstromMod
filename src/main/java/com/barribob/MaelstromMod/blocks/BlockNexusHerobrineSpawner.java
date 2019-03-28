package com.barribob.MaelstromMod.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class BlockNexusHerobrineSpawner extends BlockBase
{
    public BlockNexusHerobrineSpawner(String name)
    {
	super(name, Material.ROCK, 1000, 1000, SoundType.STONE);
	this.setBlockUnbreakable();
    }
}
