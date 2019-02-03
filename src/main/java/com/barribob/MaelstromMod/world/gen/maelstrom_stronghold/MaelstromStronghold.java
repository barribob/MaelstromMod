package com.barribob.MaelstromMod.world.gen.maelstrom_stronghold;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 * A stronghold generated in similar fashion to the azure mineshaft. It has some
 * list manipulation to make sure all loose ends become exits.
 *
 */
public class MaelstromStronghold
{
    private List<StructureComponent> components;
    private World world;
    private TemplateManager manager;
    private int exitsGenerated;
    private static final int MIN_EXITS = 4;
    private static final int SIZE = 18;
    private static final int ARCHES = 6;

    private static final List<Tuple<Rotation, BlockPos>> CROSS_POS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(16, 0, 0)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 16)));

    public MaelstromStronghold(World world, TemplateManager manager, List<StructureComponent> components)
    {
	this.components = components;
	this.world = world;
	this.manager = manager;
    }

    public void startStronghold(BlockPos pos, Rotation rot)
    {
	StrongholdTemplate template = new StrongholdTemplate(manager, "boss_room", pos, rot, 0, true);
	components.add(template);
	generateArchHallway(template, new BlockPos(0, 2, 0), rot);
	StrongholdTemplate.resetTemplateCount();
    }

    private void generateArchHallway(StrongholdTemplate parent, BlockPos pos, Rotation rot)
    {
	StrongholdTemplate template = addAdjustedPiece(parent, pos, "arch", rot);
	components.add(template);

	parent = template;

	String[] archTypes = { "arch_waterfall", "arch", "arch_chest" };
	for (int i = 0; i < ARCHES; i++)
	{
	    template = addAdjustedPiece(parent, BlockPos.ORIGIN, archTypes[i % archTypes.length], rot);
	    components.add(template);
	    parent = template;
	}

	template = addAdjustedPiece(parent, BlockPos.ORIGIN, "arch_end", rot);
	components.add(template);

	generateCross(template, BlockPos.ORIGIN, rot);
    }

    private boolean generateCross(StrongholdTemplate parent, BlockPos pos, Rotation rot)
    {
	StrongholdTemplate template = addAdjustedPiece(parent, pos, "cross", rot);

	if (template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE)
	{
	    return false;
	}

	List<StructureComponent> structures = new ArrayList(components);

	components.add(template);

	for (Tuple<Rotation, BlockPos> tuple : CROSS_POS)
	{
	    if (!generateHall(template, tuple.getSecond(), rot.add(tuple.getFirst())))
	    {
		/**
		 * If any of the halls fail, turns this into an end instead
		 */
		components.clear();
		components.addAll(structures);
		boolean successful = generateEnd(parent, pos.add(new BlockPos(0, 0, 0)), rot);
		return successful;
	    }
	}

	return true;
    }

    private boolean generateHall(StrongholdTemplate parent, BlockPos pos, Rotation rot)
    {
	StrongholdTemplate template = addAdjustedPiece(parent, pos, "hall", rot);

	if (template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE)
	{
	    return false;
	}

	components.add(template);

	int r = world.rand.nextInt(5);
	boolean genSuccess;

	if (r == 0)
	{
	    genSuccess = generateCross(template, BlockPos.ORIGIN, rot);
	}
	else if (r == 1)
	{
	    genSuccess = generateArena(template, new BlockPos(0, -2, 0), rot);
	}
	else
	{
	    genSuccess = generateHall(template, BlockPos.ORIGIN, rot);
	}

	/*
	 * If furthering the hallways failed, then turn this template into an end
	 */
	if (!genSuccess)
	{
	    components.remove(template);
	    return generateEnd(parent, pos, rot);
	}

	return true;
    }

    private boolean generateArena(StrongholdTemplate parent, BlockPos pos, Rotation rot)
    {
	StrongholdTemplate template = addAdjustedPiece(parent, pos, "arena_room", rot);

	if (template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE)
	{
	    return false;
	}

	components.add(template);

	/*
	 * If furthering the hallways failed, then turn this template into an end
	 */
	if (!generateHall(template, new BlockPos(0, 2, 0), rot))
	{
	    components.remove(template);
	    return generateEnd(parent, pos, rot);
	}

	return true;
    }

    /**
     * The end cap of the stronghold (either stairs or a dead end)
     */
    private boolean generateEnd(StrongholdTemplate parent, BlockPos pos, Rotation rot)
    {
	String[] types = { "end_chest", "end", "start" };
	String type = types[world.rand.nextInt(types.length)];
	type = this.exitsGenerated < MIN_EXITS ? "start" : type;
	StrongholdTemplate template = addAdjustedPiece(parent, pos, type, rot);

	if (template.isCollidingExcParent(manager, parent, components))
	{
	    return false;
	}

	components.add(template);
	this.exitsGenerated++;

	return true;
    }

    /**
     * Adds a new piece, with the previous template a reference for position and
     * rotation
     */
    private StrongholdTemplate addAdjustedPiece(StrongholdTemplate parent, BlockPos pos, String type, Rotation rot)
    {
	StrongholdTemplate newTemplate = new StrongholdTemplate(manager, type, parent.getTemplatePosition(), rot, parent.getDistance() + 1, true);
	BlockPos blockpos = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	adjustAndCenter(parent, newTemplate, rot);
	return newTemplate;
    }

    /**
     * Centers a template to line up on the x, and in the center with z
     */
    private void adjustAndCenter(StrongholdTemplate parent, StrongholdTemplate child, Rotation rot)
    {
	BlockPos adjustedPos = new BlockPos(parent.getTemplate().getSize().getX(), 0, (parent.getTemplate().getSize().getZ() - child.getTemplate().getSize().getZ()) / 2f)
		.rotate(rot);
	child.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());
    }
}
