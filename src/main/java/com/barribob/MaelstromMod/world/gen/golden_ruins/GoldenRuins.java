package com.barribob.MaelstromMod.world.gen.golden_ruins;

import java.util.ArrayList;
import java.util.List;

import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.world.dimension.cliff.ChunkGeneratorCliff;
import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

/**
 * 
 *
 */
public class GoldenRuins
{
    private List<StructureComponent> components;
    private World world;
    private TemplateManager manager;
    private ChunkGeneratorCliff provider;
    private static final int SIZE = 10;
    private int requiredGroundHeight = 164;

    private static final List<Tuple<Rotation, BlockPos>> CROSS_POS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(0, 0, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(30, 0, 0)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 30)));

    public GoldenRuins(World world, TemplateManager manager, ChunkGeneratorCliff provider, List<StructureComponent> components)
    {
	this.components = components;
	this.world = world;
	this.manager = manager;
	this.provider = provider;
    }

    public void startStronghold(BlockPos pos, Rotation rot)
    {
	RuinsTemplate template = new RuinsTemplate(manager, "boss", pos, rot, 0, true);
	if (this.getGroundHeight(template, provider, rot) > requiredGroundHeight && !ModUtils.chunksGenerated(template.getBoundingBox(), world))
	{
	    components.add(template);
	    generateCross(template, BlockPos.ORIGIN, rot);
	    RuinsTemplate.resetTemplateCount();
	}
    }

    private boolean generateCross(RuinsTemplate parent, BlockPos pos, Rotation rot)
    {
	String[] rooms = { "broken_four_way_bridge", "four_way_bridge", "stair_room", "entrance" };
	RuinsTemplate template = addAdjustedPiece(parent, pos, ModRandom.choice(rooms), rot);

	if (template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE || ModUtils.chunksGenerated(template.getBoundingBox(), world))
	{
	    return false;
	}

	if (this.getGroundHeight(template, provider, rot) < requiredGroundHeight)
	{
	    return this.generateEnd(parent, pos, rot);
	}

	List<StructureComponent> structures = new ArrayList(components);

	components.add(template);

	int failedHalls = 0;
	for (Tuple<Rotation, BlockPos> tuple : CROSS_POS)
	{
	    if (!generateHall(template, tuple.getSecond(), rot.add(tuple.getFirst())))
	    {
		failedHalls++;
	    }
	}

	if (failedHalls >= 3)
	{
	    components.clear();
	    components.addAll(structures);
	    return this.generateEnd(parent, pos, rot);
	}

	return true;
    }

    private boolean generateHall(RuinsTemplate parent, BlockPos pos, Rotation rot)
    {
	String[] rooms = { "bridge", "broken_bridge", "broken_column_platforms", "chandelier", "column_platforms", "pillar_bridge" };
	RuinsTemplate template = addAdjustedPiece(parent, pos, ModRandom.choice(rooms), rot);

	if (template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE || ModUtils.chunksGenerated(template.getBoundingBox(), world))
	{
	    return false;
	}

	if (this.getGroundHeight(template, provider, rot) < requiredGroundHeight)
	{
	    return this.generateEnd(parent, pos, rot);
	}

	components.add(template);

	int r = world.rand.nextInt(3);
	boolean genSuccess;

	if (r == 0)
	{
	    genSuccess = generateHall(template, BlockPos.ORIGIN, rot);
	}
	else
	{
	    genSuccess = generateCross(template, BlockPos.ORIGIN, rot);
	}

	/*
	 * If furthering the hallways failed, then turn this template into an end
	 */
	if (!genSuccess)
	{
	    components.remove(template);
	    return this.generateEnd(parent, pos, rot);
	}

	return true;
    }

    private boolean generateEnd(RuinsTemplate parent, BlockPos pos, Rotation rot)
    {
	RuinsTemplate template = addAdjustedPiece(parent, pos, "entrance", rot);
	if (template.isCollidingExcParent(manager, parent, components) || template.getDistance() > SIZE || ModUtils.chunksGenerated(template.getBoundingBox(), world))
	{
	    return false;
	}
	components.add(template);
	return true;
    }

    /**
     * Adds a new piece, with the previous template a reference for position and
     * rotation
     */
    private RuinsTemplate addAdjustedPiece(RuinsTemplate parent, BlockPos pos, String type, Rotation rot)
    {
	RuinsTemplate newTemplate = new RuinsTemplate(manager, type, parent.getTemplatePosition(), rot, parent.getDistance() + 1, true);
	BlockPos blockpos = parent.getTemplate().calculateConnectedPos(parent.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	adjustAndCenter(parent, newTemplate, rot);
	return newTemplate;
    }

    /**
     * Centers a template to line up on the x, and in the center with z
     */
    private void adjustAndCenter(RuinsTemplate parent, RuinsTemplate child, Rotation rot)
    {
	BlockPos adjustedPos = new BlockPos(parent.getTemplate().getSize().getX(), 0, (parent.getTemplate().getSize().getZ() - child.getTemplate().getSize().getZ()) / 2f)
		.rotate(rot);
	child.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());
    }

    private int getGroundHeight(RuinsTemplate template, ChunkGeneratorCliff gen, Rotation rotation)
    {
	StructureBoundingBox box = template.getBoundingBox();
	int corner1 = this.getGroundHeight(new BlockPos(box.maxX, 0, box.maxZ), gen, rotation);
	int corner2 = this.getGroundHeight(new BlockPos(box.minX, 0, box.maxZ), gen, rotation);
	int corner3 = this.getGroundHeight(new BlockPos(box.maxX, 0, box.minZ), gen, rotation);
	int corner4 = this.getGroundHeight(new BlockPos(box.minX, 0, box.minZ), gen, rotation);
	return Math.min(Math.min(corner3, corner4), Math.max(corner2, corner1));
    }

    /*
     * From MapGenEndCity: determines the ground height
     */
    private int getGroundHeight(BlockPos pos, ChunkGeneratorCliff gen, Rotation rotation)
    {
	BlockPos chunk = ModUtils.posToChunk(pos);
	ChunkPrimer chunkprimer = new ChunkPrimer();
	gen.setBlocksInChunk(chunk.getX(), chunk.getZ(), chunkprimer);
	int i = 5;
	int j = 5;

	if (rotation == Rotation.CLOCKWISE_90)
	{
	    i = -5;
	}
	else if (rotation == Rotation.CLOCKWISE_180)
	{
	    i = -5;
	    j = -5;
	}
	else if (rotation == Rotation.COUNTERCLOCKWISE_90)
	{
	    j = -5;
	}

	int k = chunkprimer.findGroundBlockIdx(7, 7);
	int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
	int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
	int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
	int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
	return k1;
    }
}
