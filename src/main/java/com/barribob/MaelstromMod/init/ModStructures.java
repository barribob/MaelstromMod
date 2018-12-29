package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MaelstromFortress;
import com.barribob.MaelstromMod.world.gen.mineshaft.StructureAzureMineshaftPieces;
import com.barribob.MaelstromMod.world.gen.mineshaft.StructureAzureMineshaftStart;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures
{
    public static void registerStructures()
    {
	MapGenStructureIO.registerStructure(StructureAzureMineshaftStart.class, "Azure Mineshaft");
	StructureAzureMineshaftPieces.registerStructurePieces();
	MapGenStructureIO.registerStructure(MapGenMaelstromFortress.Start.class, "Malestrom Fortress");
	MaelstromFortress.registerPieces();
    }
}
