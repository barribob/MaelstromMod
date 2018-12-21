package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.gen.mineshaft.StructureAzureMineshaftPieces;
import com.barribob.MaelstromMod.world.gen.mineshaft.StructureAzureMineshaftStart;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures 
{
	public static void registerStructures()
	{
		MapGenStructureIO.registerStructure(StructureAzureMineshaftStart.class, "Azure Mineshaft");
		StructureAzureMineshaftPieces.registerStructurePieces();
	}
}
