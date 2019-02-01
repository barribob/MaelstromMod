package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.FortressTemplate;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.MaelstromStronghold;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.MapGenMaelstromStronghold;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.StrongholdTemplate;
import com.barribob.MaelstromMod.world.gen.mineshaft.AzureMineshaft;
import com.barribob.MaelstromMod.world.gen.mineshaft.AzureMineshaftTemplate;
import com.barribob.MaelstromMod.world.gen.mineshaft.MapGenAzureMineshaft;

import net.minecraft.world.gen.structure.MapGenStructureIO;

public class ModStructures
{
    public static void registerStructures()
    {
	MapGenStructureIO.registerStructure(MapGenAzureMineshaft.Start.class, "Azure Mineshaft");
	MapGenStructureIO.registerStructureComponent(AzureMineshaftTemplate.class, "AMP");
	MapGenStructureIO.registerStructure(MapGenMaelstromFortress.Start.class, "Maelstrom Fortress");
	MapGenStructureIO.registerStructureComponent(FortressTemplate.class, "MFP");
	MapGenStructureIO.registerStructure(MapGenMaelstromStronghold.Start.class, "Maelstrom Stronghold");
	MapGenStructureIO.registerStructureComponent(StrongholdTemplate.class, "MSP");
    }
}
