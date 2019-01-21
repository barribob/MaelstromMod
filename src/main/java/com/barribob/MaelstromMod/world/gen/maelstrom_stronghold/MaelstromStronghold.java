package com.barribob.MaelstromMod.world.gen.maelstrom_stronghold;

import java.util.List;

import com.barribob.MaelstromMod.world.gen.maelstrom_fortress.FortressTemplate;
import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class MaelstromStronghold
{
    public static void registerPieces()
    {
	MapGenStructureIO.registerStructureComponent(StrongholdTemplate.class, "MSP");
    }
    
    public static void startStronghold(World world, TemplateManager manager, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	StrongholdTemplate template = new StrongholdTemplate(manager, "cross", pos, Rotation.NONE, false);
	components.add(template);
	generateCross(world, manager, template, pos, components);
    }
    
    /**
     * Adds a new piece, with the previous template a reference for position and
     * rotation
     */
    private static StrongholdTemplate addPiece(TemplateManager manager, StrongholdTemplate template, BlockPos pos, String type, Rotation rotation, boolean overwrite)
    {
	StrongholdTemplate newTemplate = new StrongholdTemplate(manager, type, template.getTemplatePosition(), rotation, overwrite);
	BlockPos blockpos = template.getTemplate().calculateConnectedPos(template.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	return newTemplate;
    }
    
    private static final List<Tuple<Rotation, BlockPos>> CROSS = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(-17, 0, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(16, 0, -17)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, 0, 33)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(33, 0, 16)));
    
    public static void generateCross(World world, TemplateManager manager, StrongholdTemplate parent, BlockPos pos, List<StructureComponent> components)
    {
	Rotation rotation = parent.getPlacementSettings().getRotation();

	int bridgesGenerated = 0;

	// Generate the next bridges in all four directions
	for (Tuple<Rotation, BlockPos> tuple : CROSS)
	{
	    if (generateCorridor(world, manager, parent, tuple.getSecond(), rotation.add(tuple.getFirst()), components))
	    {
		bridgesGenerated++;
	    }
	}
    }
    
    public static boolean generateCorridor(World world, TemplateManager manager, StrongholdTemplate parent, BlockPos pos, Rotation rot, List<StructureComponent> components)
    {
	StrongholdTemplate template = addPiece(manager, parent, pos, "hall", rot, false);
	
	// To adjust, offest the x position completely, and the z position by half (because we try to line up the door halfway)
	// The assumptions here, are that the doors are both in the center, and the door is facing in the x direction, as well as
	// there is no y offset required
	BlockPos adjustedPos = new BlockPos(parent.getTemplate().getSize().getX() - template.getTemplate().getSize().getX(), 0, (parent.getTemplate().getSize().getZ() - template.getTemplate().getSize().getZ()) / 2);
	
	// Add the rotation for the adjusted pos
	adjustedPos = adjustedPos.rotate(rot);
	
	// Offset
	template.offset(adjustedPos.getX(), adjustedPos.getY(), adjustedPos.getZ());
	
	components.add(template);
	return true;
    }
}
