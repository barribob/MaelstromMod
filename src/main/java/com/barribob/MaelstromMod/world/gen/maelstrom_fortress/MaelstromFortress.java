package com.barribob.MaelstromMod.world.gen.maelstrom_fortress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * Uses mutually recursive function to generate the fortress
 *
 */
public class MaelstromFortress
{
    private static String[] TOWERS = { "dungeon", "jail", "open_tower", "watch_tower" };
    private static String[] BRIDGES = { "bridge", "fat_bridge", "gate_bridge", "infected_bridge" };
    private static final int SIZE = 10;

    /**
     * Begin the fortress generation with the main boss tower
     */
    public static void startFortress(World world, TemplateManager manager, BlockPos pos, Rotation rotation, List<StructureComponent> components, Random rand)
    {
	FortressTemplate template = new FortressTemplate(manager, "boss_tower", 0, pos, rotation, false);
	components.add(template);

	generateTowerBase(manager, template, components, rand, new BlockPos(8, 0, 8), rotation);

	generateMainTowerTowers(manager, template, components, rand);
	FortressTemplate.resetTemplateCount();
    }

    /**
     * Adds a new piece, with the previous template a reference for position and
     * rotation
     */
    private static FortressTemplate addPiece(TemplateManager manager, FortressTemplate template, BlockPos pos, String type, Rotation rotation, boolean overwrite)
    {
	FortressTemplate newTemplate = new FortressTemplate(manager, type, template.getDistance() + 1, template.getTemplatePosition(), rotation, overwrite);
	BlockPos blockpos = template.getTemplate().calculateConnectedPos(template.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	return newTemplate;
    }

    /**
     * Generates a single tower and continues to try to generate bridges
     */
    private static boolean generateTower(TemplateManager manager, FortressTemplate parent, List<StructureComponent> structures, Random rand)
    {
	Rotation rotation = parent.getPlacementSettings().getRotation();

	FortressTemplate towerTemplate = MaelstromFortress.addPiece(manager, parent, new BlockPos(8, 0, 0), TOWERS[rand.nextInt(TOWERS.length)], rotation, false);

	if (!isColliding(manager, towerTemplate, structures, rand))
	{
	    structures.add(towerTemplate);

	    if (rand.nextInt(3) == 0)
	    {
		generateStairs(manager, towerTemplate, structures, rand);
	    }
	    else
	    {
		generateBridges(manager, towerTemplate, structures, rand);
	    }

	    generateTowerBase(manager, towerTemplate, structures, rand, BlockPos.ORIGIN, rotation);

	    return true;
	}

	return false;
    }

    /**
     * Generates stairs and continues to try to generate a tower
     */
    private static boolean generateStairs(TemplateManager manager, FortressTemplate template, List<StructureComponent> structures, Random rand)
    {
	Rotation rotation = template.getPlacementSettings().getRotation();

	FortressTemplate stairTemplate = MaelstromFortress.addPiece(manager, template, new BlockPos(8, -8, 0), "stairs", rotation, false);

	if (stairTemplate.getDistance() < SIZE && !isColliding(manager, stairTemplate, structures, rand))
	{
	    structures.add(stairTemplate);

	    if (generateTower(manager, stairTemplate, structures, rand))
	    {
		return true;
	    }
	    else
	    {
		structures.remove(stairTemplate);
		return false;
	    }
	}

	return false;
    }

    /**
     * Generates bridges if they can continue to generate new towers/stairs
     */
    private static boolean generateSingleBridge(TemplateManager manager, FortressTemplate parent, List<StructureComponent> structures, Random rand,
	    Rotation rotation, BlockPos pos)
    {
	FortressTemplate bridgeTemplate = addPiece(manager, parent, pos, BRIDGES[rand.nextInt(BRIDGES.length)], rotation, false);

	if (bridgeTemplate.getDistance() < SIZE && !isColliding(manager, bridgeTemplate, structures, rand))
	{
	    structures.add(bridgeTemplate);

	    int i = rand.nextInt(10);

	    if (i >= 7)
	    {
		if (generateStairs(manager, bridgeTemplate, structures, rand))
		{
		    return true;
		}
	    }
	    else if (i >= 6)
	    {
		if (generateSingleBridge(manager, bridgeTemplate, structures, rand, rotation, BlockPos.ORIGIN))
		{
		    return true;
		}
	    }
	    else
	    {
		if (generateTower(manager, bridgeTemplate, structures, rand))
		{
		    return true;
		}
	    }

	    structures.remove(bridgeTemplate);
	}
	return false;
    }

    private static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(8, 0, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(7, 0, 8)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, -1)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(-1, 0, 7)));

    /**
     * Generates bridges if they can continue to generate new towers/stairs
     */
    private static boolean generateBridges(TemplateManager manager, FortressTemplate parent, List<StructureComponent> structures, Random rand)
    {
	Rotation rotation = parent.getPlacementSettings().getRotation();

	int bridgesGenerated = 0;

	List<Tuple<Rotation, BlockPos>> bridges = new ArrayList(TOWER_BRIDGES);
	Collections.shuffle(bridges);

	// Generate the next bridges in all four directions
	for (Tuple<Rotation, BlockPos> tuple : bridges)
	{
	    if (generateSingleBridge(manager, parent, structures, rand, rotation.add(tuple.getFirst()), tuple.getSecond()))
	    {
		bridgesGenerated++;
	    }
	}

	return bridgesGenerated > 0;
    }

    private static final List<Tuple<Rotation, BlockPos>> MAIN_TOWER = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(24, -4, 8)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(15, -4, 24)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(8, -4, -1)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(-1, -4, 15)));

    /**
     * Generate the first four towers adjacent to the main tower
     */
    private static void generateMainTowerTowers(TemplateManager manager, FortressTemplate template, List<StructureComponent> structures, Random rand)
    {
	Rotation rotation = template.getPlacementSettings().getRotation();

	List<Tuple<Rotation, BlockPos>> towers = new ArrayList(MAIN_TOWER);
	Collections.shuffle(towers);

	// Generate the next towers in all four directions
	for (Tuple<Rotation, BlockPos> tuple : towers)
	{
	    FortressTemplate towerTemplate = addPiece(manager, template, tuple.getSecond(), TOWERS[rand.nextInt(TOWERS.length)], rotation.add(tuple.getFirst()), false);
	    structures.add(towerTemplate);
	    generateTowerBase(manager, towerTemplate, structures, rand, BlockPos.ORIGIN, rotation.add(tuple.getFirst()));
	    generateBridges(manager, towerTemplate, structures, rand);
	}
    }

    /**
     * Generate the base of the towers
     */
    private static void generateTowerBase(TemplateManager manager, FortressTemplate parent, List<StructureComponent> structures, Random rand, BlockPos pos,
	    Rotation additionalRotation)
    {
	int adjustedY = -2;

	for (int y = parent.getTemplatePosition().getY() - 2; y > 10; y -= 2)
	{
	    FortressTemplate towerBase = MaelstromFortress.addPiece(manager, parent, new BlockPos(0, adjustedY, 0).add(pos), "base", additionalRotation, false);
	    structures.add(towerBase);
	    adjustedY -= 2;
	}
    }

    /**
     * Determines if the new template is overlapping with another template
     */
    private static boolean isColliding(TemplateManager manager, FortressTemplate childTemplate, List<StructureComponent> structures, Random rand)
    {
	StructureComponent collision = FortressTemplate.findIntersectingExclusive(structures, childTemplate.getBoundingBox());

	if (collision != null)
	{
	    return true;
	}

	return false;
    }
}