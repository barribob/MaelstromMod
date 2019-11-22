package com.barribob.MaelstromMod.init;

import com.barribob.MaelstromMod.world.gen.cliff.HoleTempleTemplate;
import com.barribob.MaelstromMod.world.gen.cliff.MapGenHoleTemple;
import com.barribob.MaelstromMod.world.gen.golden_ruins.MapGenGoldenRuins;
import com.barribob.MaelstromMod.world.gen.golden_ruins.RuinsTemplate;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.FortressTemplate;
import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.MapGenMaelstromFortress;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.MapGenMaelstromStronghold;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.StrongholdTemplate;
import com.barribob.MaelstromMod.world.gen.mineshaft.AzureMineshaftTemplate;
import com.barribob.MaelstromMod.world.gen.mineshaft.MapGenAzureMineshaft;
import com.barribob.MaelstromMod.world.gen.nexus.MapGenNexusEntrance;
import com.barribob.MaelstromMod.world.gen.nexus.NexusTemplate;

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
	MapGenStructureIO.registerStructureComponent(NexusTemplate.class, "Nexus Template");
	MapGenStructureIO.registerStructure(MapGenNexusEntrance.Start.class, "Nexus Islands");
	MapGenStructureIO.registerStructure(MapGenGoldenRuins.Start.class, "Golden Ruins");
	MapGenStructureIO.registerStructureComponent(RuinsTemplate.class, "GRP");
	MapGenStructureIO.registerStructure(MapGenHoleTemple.Start.class, "Hole Temple");
	MapGenStructureIO.registerStructureComponent(HoleTempleTemplate.class, "HTP");
    }
}
