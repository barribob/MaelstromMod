package com.barribob.MaelstromMod.world.gen.mineshaft;

import java.util.List;
import java.util.Random;

import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.FortressTemplate;
import com.barribob.MaelstromMod.world.gen.maelstrom_stronghold.StrongholdTemplate;
import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class AzureMineshaft
{
    private static final List<Tuple<Rotation, BlockPos>> START_POS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(10, 0, 0)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 10)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(10, 0, 10)));

    /**
     * Adds a new piece, with the previous template a reference for position and
     * rotation
     */
    private static AzureMineshaftTemplate addAdjustedPiece(TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, String type, Rotation rot, boolean overwrite)
    {
	AzureMineshaftTemplate newTemplate = new AzureMineshaftTemplate(manager, type, parent.getTemplatePosition(), rot, parent.getDistance() + 1, overwrite);
	BlockPos blockpos = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	adjustAndCenter(parent, newTemplate, rot);
	return newTemplate;
    }

    /**
     * Centers a template to line up on the x, and in the center with z
     */
    private static void adjustAndCenter(AzureMineshaftTemplate parent, AzureMineshaftTemplate child, Rotation rot)
    {
	BlockPos adjustedPos = new BlockPos(parent.getTemplate().getSize().getX(), 0, (parent.getTemplate().getSize().getZ() - child.getTemplate().getSize().getZ()) / 2f)
		.rotate(rot);
	child.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());
    }

    /**
     * Starts the mineshaft growing in four directions
     */
    public static void startMineshaft(World world, TemplateManager manager, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = new AzureMineshaftTemplate(manager, "start", pos, rot, 0, true);
	components.add(template);
	for (Tuple<Rotation, BlockPos> tuple : START_POS)
	{
	    generateRail(world, manager, template, "open_rail", tuple.getSecond(), rot.add(tuple.getFirst()), components);
	}
	AzureMineshaftTemplate.resetTemplateCount();
    }

    /*
     * Generate a rail with a structure attached
     */
    private static boolean structureRail(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, "rail_structure", rot, true);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);

	if (!generateSide(world, manager, template, new BlockPos(4, 0, 0), rot.add(Rotation.CLOCKWISE_90), components) || world.rand.nextInt(2) == 0)
	{
	    generateSide(world, manager, template, new BlockPos(0, 0, 4), rot.add(Rotation.COUNTERCLOCKWISE_90), components);
	}

	generateRail(world, manager, template, "open_rail", BlockPos.ORIGIN, rot, components);

	return true;
    }

    /**
     * Generates the side structure
     */
    private static boolean generateSide(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	String[] types = { "house", "double_house", "log_pile", "ore_pile" };
	String type = types[world.rand.nextInt(types.length)];
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, type, rot, true);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);

	return true;
    }

    private static boolean turnRight(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, "turn_right", rot, true);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	// Offset by 1
	BlockPos adjustedPos = new BlockPos(0, 0, 1).rotate(rot);
	template.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());

	components.add(template);

	generateRail(world, manager, template, "open_rail", new BlockPos(2, 0, 1), rot.add(Rotation.CLOCKWISE_90), components);

	return true;
    }

    private static boolean turnLeft(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, "turn_left", rot, true);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);

	generateRail(world, manager, template, "open_rail", new BlockPos(0, 0, 2), rot.add(Rotation.COUNTERCLOCKWISE_90), components);

	return true;
    }

    private static boolean generateStairs(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, "rail_stairs", rot, true);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	// Offset by -7 in height
	BlockPos adjustedPos = new BlockPos(0, -6, 0).rotate(rot);
	template.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());

	components.add(template);

	generateRail(world, manager, template, "open_rail", BlockPos.ORIGIN, rot, components);

	return true;
    }

    private static boolean endRail(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, "rail_end", rot, true);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);

	return true;
    }

    /**
     * Generates new rails from the parent
     */
    private static boolean generateRail(World world, TemplateManager manager, AzureMineshaftTemplate parent, String railType, BlockPos pos, Rotation rot,
	    List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addAdjustedPiece(manager, parent, pos, railType, rot, true);

	if (parent.getDistance() > 30)
	{
	    endRail(world, manager, parent, pos, rot, components);
	    return false;
	}

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);

	int r = world.rand.nextInt(4);

	// Makes rails generate in long stretches, and turns or stairs happen every railDistance
	// turns
	int railDistance = 5;
	boolean generateOnlyRails = parent.getDistance() % railDistance < railDistance - 1;

	if (r == 0 || generateOnlyRails)
	{
	    if (world.rand.nextInt(10) == 0)
	    {
		// Chest rail (don't want these to be too common)
		generateRail(world, manager, template, "rail_chest", BlockPos.ORIGIN, rot, components);
	    }
	    else if (world.rand.nextInt(3) == 0)
	    {
		structureRail(world, manager, template, BlockPos.ORIGIN, rot, components);
	    }
	    else
	    {
		// Other various rails
		String[] railTypes = { "open_rail", "supported_rail", "supported_rail_right", "supported_rail_left" };
		String type = railTypes[world.rand.nextInt(railTypes.length)];
		generateRail(world, manager, template, type, BlockPos.ORIGIN, rot, components);
	    }
	}
	else if (r == 1)
	{
	    turnLeft(world, manager, template, BlockPos.ORIGIN, rot, components);
	}
	else if (r == 2)
	{
	    turnRight(world, manager, template, BlockPos.ORIGIN, rot, components);
	}
	else if (r == 3 && template.getTemplatePosition().getY() > 10)
	{
	    generateStairs(world, manager, template, BlockPos.ORIGIN, rot, components);
	}

	return true;
    }
}
