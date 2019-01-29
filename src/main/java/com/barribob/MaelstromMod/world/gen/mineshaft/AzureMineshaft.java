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
    public static void registerPieces()
    {
	MapGenStructureIO.registerStructureComponent(AzureMineshaftTemplate.class, "AMP");
    }

    /**
     * Adds a new piece, with the previous template a reference for position and
     * rotation
     */
    private static AzureMineshaftTemplate addPiece(TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, String type, Rotation rotation, boolean overwrite)
    {
	AzureMineshaftTemplate newTemplate = new AzureMineshaftTemplate(manager, type, parent.getTemplatePosition(), rotation, parent.getDistance() + 1, overwrite);
	BlockPos blockpos = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
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

    public static void startMineshaft(World world, TemplateManager manager, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = new AzureMineshaftTemplate(manager, "start", pos, rot, 0, true);
	components.add(template);
	generateStart(world, manager, template, pos, components);
	AzureMineshaftTemplate.resetTemplateCount();
    }

    private static final List<Tuple<Rotation, BlockPos>> START_POS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(10, 0, 0)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 10)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(10, 0, 10)));

    public static void generateStart(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, List<StructureComponent> components)
    {
	Rotation rotation = parent.getPlacementSettings().getRotation();

	int bridgesGenerated = 0;

	// Generate the next bridges in all four directions
	for (Tuple<Rotation, BlockPos> tuple : START_POS)
	{
	    if (generateRail(world, manager, parent, "open_rail", tuple.getSecond(), rotation.add(tuple.getFirst()), components))
	    {
		bridgesGenerated++;
		return;
	    }
	}
    }

    public static boolean turnRight(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addPiece(manager, parent, pos, "turn_right", rot, true);

	adjustAndCenter(parent, template, rot);

	// Offset by 1
	BlockPos adjustedPos = new BlockPos(0, 0, 1).rotate(rot);
	template.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());

	components.add(template);

	generateRail(world, manager, template, "open_rail", new BlockPos(2, 0, 1), rot.add(Rotation.CLOCKWISE_90), components);

	return true;
    }

    public static boolean turnLeft(World world, TemplateManager manager, AzureMineshaftTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addPiece(manager, parent, pos, "turn_left", rot, true);

	adjustAndCenter(parent, template, rot);

	components.add(template);

	generateRail(world, manager, template, "open_rail", new BlockPos(0, 0, 2), rot.add(Rotation.COUNTERCLOCKWISE_90), components);

	return true;
    }

    /**
     * Generates new rails from the parent
     */
    public static boolean generateRail(World world, TemplateManager manager, AzureMineshaftTemplate parent, String railType, BlockPos pos, Rotation rot,
	    List<StructureComponent> components)
    {
	AzureMineshaftTemplate template = addPiece(manager, parent, pos, railType, rot, true);
	adjustAndCenter(parent, template, rot);

	if (parent.getDistance() > 30 || template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);

	int r = world.rand.nextInt(3);

	// Makes rails generate in long stretches, and turns happen every railDistance
	// turns
	int railDistance = 5;
	boolean generateOnlyRails = parent.getDistance() % railDistance < railDistance - 1;

	// Only when distance becomes
	if (r == 0 || generateOnlyRails)
	{
	    if (world.rand.nextInt(10) == 0)
	    {
		generateRail(world, manager, template, "rail_chest", BlockPos.ORIGIN, rot, components);
	    }
	    else
	    {
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

	return true;
    }
}
