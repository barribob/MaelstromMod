package com.barribob.MaelstromMod.world.gen.mineshaft;

import java.util.Random;

import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureStart;

/*
 * Start the mineshaft structure with a room
 */
public class StructureAzureMineshaftStart extends StructureStart
{
    public StructureAzureMineshaftStart()
    {
    }

    public StructureAzureMineshaftStart(World world, Random rand, int p_i47149_3_, int p_i47149_4_)
    {
        super(p_i47149_3_, p_i47149_4_);
        StructureAzureMineshaftPieces.Room structuremineshaftpieces$room = new StructureAzureMineshaftPieces.Room(0, rand, (p_i47149_3_ << 4) + 2, (p_i47149_4_ << 4) + 2);
        this.components.add(structuremineshaftpieces$room);
        structuremineshaftpieces$room.buildComponent(structuremineshaftpieces$room, this.components, rand);
        this.updateBoundingBox();
        this.markAvailableHeight(world, rand, 10);
    }
}