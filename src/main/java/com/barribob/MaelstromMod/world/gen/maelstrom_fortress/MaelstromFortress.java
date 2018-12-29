package com.barribob.MaelstromMod.world.gen.maelstrom_fortress;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.util.Rotation;
import net.minecraft.util.Tuple;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.template.TemplateManager;

public class MaelstromFortress
{
    private static final IGenerator HOUSE_TOWER_GENERATOR = new IGenerator()
    {
	public void init()
	{
	}

	public boolean generate(TemplateManager manager, int structuresGenerated, FortressTemplate template, BlockPos pos, List<StructureComponent> structures, Random rand)
	{
	    if (structuresGenerated > 8)
	    {
		return false;
	    }
	    else
	    {
		Rotation rotation = template.getPlacementSettings().getRotation();
		FortressTemplate structureendcitypieces$citytemplate = MaelstromFortress.add(structures,
			MaelstromFortress.addPiece(manager, template, pos, "base_floor", rotation, true));
		int i = rand.nextInt(3);

		if (i == 0)
		{
		    MaelstromFortress.add(structures,
			    MaelstromFortress.addPiece(manager, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "base_roof", rotation, true));
		}
		else if (i == 1)
		{
		    structureendcitypieces$citytemplate = MaelstromFortress.add(structures,
			    MaelstromFortress.addPiece(manager, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
		    structureendcitypieces$citytemplate = MaelstromFortress.add(structures,
			    MaelstromFortress.addPiece(manager, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "second_roof", rotation, false));
		    MaelstromFortress.recursiveChildren(manager, MaelstromFortress.TOWER_GENERATOR, structuresGenerated + 1, structureendcitypieces$citytemplate,
			    (BlockPos) null, structures, rand);
		}
		else if (i == 2)
		{
		    structureendcitypieces$citytemplate = MaelstromFortress.add(structures,
			    MaelstromFortress.addPiece(manager, structureendcitypieces$citytemplate, new BlockPos(-1, 0, -1), "second_floor_2", rotation, false));
		    structureendcitypieces$citytemplate = MaelstromFortress.add(structures,
			    MaelstromFortress.addPiece(manager, structureendcitypieces$citytemplate, new BlockPos(-1, 4, -1), "third_floor_c", rotation, false));
		    structureendcitypieces$citytemplate = MaelstromFortress.add(structures,
			    MaelstromFortress.addPiece(manager, structureendcitypieces$citytemplate, new BlockPos(-1, 8, -1), "third_roof", rotation, true));
		    MaelstromFortress.recursiveChildren(manager, MaelstromFortress.TOWER_GENERATOR, structuresGenerated + 1, structureendcitypieces$citytemplate,
			    (BlockPos) null, structures, rand);
		}

		return true;
	    }
	}
    };
    private static final List<Tuple<Rotation, BlockPos>> TOWER_BRIDGES = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(1, -1, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(6, -1, 1)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 5)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(5, -1, 6)));

    private static final IGenerator TOWER_GENERATOR = new IGenerator()
    {
	public void init()
	{
	}

	public boolean generate(TemplateManager manager, int structureAmount, FortressTemplate template, BlockPos pos, List<StructureComponent> structures,
		Random rand)
	{
	    Rotation rotation = template.getPlacementSettings().getRotation();
	    // Add tower base
	    FortressTemplate lvt_8_1_ = MaelstromFortress.add(structures, MaelstromFortress.addPiece(manager, template,
		    new BlockPos(3 + rand.nextInt(2), -3, 3 + rand.nextInt(2)), "tower_base", rotation, true));
	    
	    // Add tower piece
	    lvt_8_1_ = MaelstromFortress.add(structures, MaelstromFortress.addPiece(manager, lvt_8_1_, new BlockPos(0, 7, 0), "tower_piece", rotation, true));
	    FortressTemplate newTemplate = rand.nextInt(3) == 0 ? lvt_8_1_ : null; // Randomly stop the generation?
	    int i = 1 + rand.nextInt(3);

	    // Add a random amount of tower pieces
	    for (int j = 0; j < i; ++j)
	    {
		lvt_8_1_ = MaelstromFortress.add(structures, MaelstromFortress.addPiece(manager, lvt_8_1_, new BlockPos(0, 4, 0), "tower_piece", rotation, true));

		if (j < i - 1 && rand.nextBoolean())
		{
		    newTemplate = lvt_8_1_;
		}
	    }

	    // 
	    if (newTemplate != null)
	    {
		for (Tuple<Rotation, BlockPos> tuple : MaelstromFortress.TOWER_BRIDGES)
		{
		    if (rand.nextBoolean())
		    {
			FortressTemplate structureendcitypieces$citytemplate2 = MaelstromFortress.add(structures, MaelstromFortress.addPiece(manager,
				newTemplate, tuple.getSecond(), "bridge_end", rotation.add(tuple.getFirst()), true));
			MaelstromFortress.recursiveChildren(manager, MaelstromFortress.TOWER_BRIDGE_GENERATOR, structureAmount + 1, structureendcitypieces$citytemplate2,
				(BlockPos) null, structures, rand);
		    }
		}

		MaelstromFortress.add(structures, MaelstromFortress.addPiece(manager, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
	    }
	    else
	    {
		if (structureAmount != 7)
		{
		    return MaelstromFortress.recursiveChildren(manager, MaelstromFortress.FAT_TOWER_GENERATOR, structureAmount + 1, lvt_8_1_, (BlockPos) null, structures,
			    rand);
		}

		MaelstromFortress.add(structures, MaelstromFortress.addPiece(manager, lvt_8_1_, new BlockPos(-1, 4, -1), "tower_top", rotation, true));
	    }

	    return true;
	}
    };
    private static final IGenerator TOWER_BRIDGE_GENERATOR = new IGenerator()
    {
	public boolean shipCreated;

	public void init()
	{
	    this.shipCreated = false;
	}

	public boolean generate(TemplateManager p_191086_1_, int p_191086_2_, FortressTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_,
		Random p_191086_6_)
	{
	    Rotation rotation = p_191086_3_.getPlacementSettings().getRotation();
	    int i = p_191086_6_.nextInt(4) + 1;
	    FortressTemplate template = MaelstromFortress.add(p_191086_5_,
		    MaelstromFortress.addPiece(p_191086_1_, p_191086_3_, new BlockPos(0, 0, -4), "bridge_piece", rotation, true));
	    template.setComponentType(-1);
	    int j = 0;

	    for (int k = 0; k < i; ++k)
	    {
		if (p_191086_6_.nextBoolean())
		{
		    template = MaelstromFortress.add(p_191086_5_, MaelstromFortress.addPiece(p_191086_1_, template, new BlockPos(0, j, -4), "bridge_piece", rotation, true));
		    j = 0;
		}
		else
		{
		    if (p_191086_6_.nextBoolean())
		    {
			template = MaelstromFortress.add(p_191086_5_,
				MaelstromFortress.addPiece(p_191086_1_, template, new BlockPos(0, j, -4), "bridge_steep_stairs", rotation, true));
		    }
		    else
		    {
			template = MaelstromFortress.add(p_191086_5_,
				MaelstromFortress.addPiece(p_191086_1_, template, new BlockPos(0, j, -8), "bridge_gentle_stairs", rotation, true));
		    }

		    j = 4;
		}
	    }

	    if (!this.shipCreated && p_191086_6_.nextInt(10 - p_191086_2_) == 0)
	    {
		MaelstromFortress.add(p_191086_5_, MaelstromFortress.addPiece(p_191086_1_, template,
			new BlockPos(-8 + p_191086_6_.nextInt(8), j, -70 + p_191086_6_.nextInt(10)), "ship", rotation, true));
		this.shipCreated = true;
	    }
	    else if (!MaelstromFortress.recursiveChildren(p_191086_1_, MaelstromFortress.HOUSE_TOWER_GENERATOR, p_191086_2_ + 1, template, new BlockPos(-3, j + 1, -11),
		    p_191086_5_, p_191086_6_))
	    {
		return false;
	    }

	    template = MaelstromFortress.add(p_191086_5_,
		    MaelstromFortress.addPiece(p_191086_1_, template, new BlockPos(4, j, 0), "bridge_end", rotation.add(Rotation.CLOCKWISE_180), true));
	    template.setComponentType(-1);
	    return true;
	}
    };
    private static final List<Tuple<Rotation, BlockPos>> FAT_TOWER_BRIDGES = Lists.newArrayList(new Tuple(Rotation.NONE, new BlockPos(4, -1, 0)),
	    new Tuple(Rotation.CLOCKWISE_90, new BlockPos(12, -1, 4)), new Tuple(Rotation.COUNTERCLOCKWISE_90, new BlockPos(0, -1, 8)),
	    new Tuple(Rotation.CLOCKWISE_180, new BlockPos(8, -1, 12)));
    private static final IGenerator FAT_TOWER_GENERATOR = new IGenerator()
    {
	public void init()
	{
	}

	public boolean generate(TemplateManager p_191086_1_, int p_191086_2_, FortressTemplate p_191086_3_, BlockPos p_191086_4_, List<StructureComponent> p_191086_5_,
		Random p_191086_6_)
	{
	    Rotation rotation = p_191086_3_.getPlacementSettings().getRotation();
	    FortressTemplate structureendcitypieces$citytemplate = MaelstromFortress.add(p_191086_5_,
		    MaelstromFortress.addPiece(p_191086_1_, p_191086_3_, new BlockPos(-3, 4, -3), "fat_tower_base", rotation, true));
	    structureendcitypieces$citytemplate = MaelstromFortress.add(p_191086_5_,
		    MaelstromFortress.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 4, 0), "fat_tower_middle", rotation, true));

	    for (int i = 0; i < 2 && p_191086_6_.nextInt(3) != 0; ++i)
	    {
		structureendcitypieces$citytemplate = MaelstromFortress.add(p_191086_5_,
			MaelstromFortress.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(0, 8, 0), "fat_tower_middle", rotation, true));

		for (Tuple<Rotation, BlockPos> tuple : MaelstromFortress.FAT_TOWER_BRIDGES)
		{
		    if (p_191086_6_.nextBoolean())
		    {
			FortressTemplate structureendcitypieces$citytemplate1 = MaelstromFortress.add(p_191086_5_, MaelstromFortress.addPiece(p_191086_1_,
				structureendcitypieces$citytemplate, tuple.getSecond(), "bridge_end", rotation.add(tuple.getFirst()), true));
			MaelstromFortress.recursiveChildren(p_191086_1_, MaelstromFortress.TOWER_BRIDGE_GENERATOR, p_191086_2_ + 1, structureendcitypieces$citytemplate1,
				(BlockPos) null, p_191086_5_, p_191086_6_);
		    }
		}
	    }

	    MaelstromFortress.add(p_191086_5_,
		    MaelstromFortress.addPiece(p_191086_1_, structureendcitypieces$citytemplate, new BlockPos(-2, 8, -2), "fat_tower_top", rotation, true));
	    return true;
	}
    };

    public static void registerPieces()
    {
	MapGenStructureIO.registerStructureComponent(FortressTemplate.class, "ECP");
    }

    private static FortressTemplate addPiece(TemplateManager manager, FortressTemplate template, BlockPos pos, String type, Rotation rotation, boolean overwrite)
    {
	FortressTemplate newTemplate = new FortressTemplate(manager, type, template.getTemplatePosition(), rotation, overwrite);
	BlockPos blockpos = template.getTemplate().calculateConnectedPos(template.getPlacementSettings(), pos, newTemplate.getPlacementSettings(), BlockPos.ORIGIN);
	newTemplate.offset(blockpos.getX(), blockpos.getY(), blockpos.getZ());
	return newTemplate;
    }

    public static void startHouseTower(TemplateManager manager, BlockPos pos, Rotation rotation, List<StructureComponent> components, Random rand)
    {
	FAT_TOWER_GENERATOR.init();
	HOUSE_TOWER_GENERATOR.init();
	TOWER_BRIDGE_GENERATOR.init();
	TOWER_GENERATOR.init();
	FortressTemplate template = add(components, new FortressTemplate(manager, "boss_tower", pos, rotation, true));
	add(components, new FortressTemplate(manager, "open_tower", pos.add(new BlockPos(-8, 0, 8)), rotation, true));
	add(components, new FortressTemplate(manager, "watch_tower", pos.add(new BlockPos(8, 0, -8)), rotation, true));
	add(components, new FortressTemplate(manager, "dungeon", pos.add(new BlockPos(-8, 0, -24)), rotation, true));
	add(components, new FortressTemplate(manager, "jail", pos.add(new BlockPos(-24, 0, -8)), rotation, true));
//	recursiveChildren(manager, TOWER_GENERATOR, 1, template, (BlockPos) null, components, rand);
    }

    private static FortressTemplate add(List<StructureComponent> structures, FortressTemplate template)
    {
	structures.add(template);
	return template;
    }

    private static boolean recursiveChildren(TemplateManager manager, IGenerator piece, int structureAmount, FortressTemplate template, BlockPos pos,
	    List<StructureComponent> structures, Random random)
    {
	if (structureAmount > 8)
	{
	    return false;
	}
	else
	{
	    List<StructureComponent> list = Lists.<StructureComponent>newArrayList();

	    if (piece.generate(manager, structureAmount, template, pos, list, random))
	    {
		boolean flag = false;
		int i = random.nextInt();

		for (StructureComponent structurecomponent : list)
		{
		    ((FortressTemplate) structurecomponent).setComponentType(i);
		    StructureComponent structurecomponent1 = StructureComponent.findIntersecting(structures, structurecomponent.getBoundingBox());

		    if (structurecomponent1 != null && structurecomponent1.getComponentType() != template.getComponentType())
		    {
			flag = true;
			break;
		    }
		}

		if (!flag)
		{
		    structures.addAll(list);
		    return true;
		}
	    }

	    return false;
	}
    }
}