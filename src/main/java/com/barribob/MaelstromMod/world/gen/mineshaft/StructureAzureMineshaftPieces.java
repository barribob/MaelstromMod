package com.barribob.MaelstromMod.world.gen.mineshaft;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.init.ModBlocks;
import com.google.common.collect.Lists;

import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockRail;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenMineshaft;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureMineshaftPieces;
import net.minecraft.world.gen.structure.StructureVillagePieces;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * 
 * Generates the mineshaft with village huts
 *
 */
public class StructureAzureMineshaftPieces
{
    abstract static class Piece extends StructureComponent
    {
	public Piece()
	{
	}

	public Piece(int p_i47138_1_)
	{
	    super(p_i47138_1_);
	}

	protected IBlockState getPlanksBlock()
	{
	    return ModBlocks.AZURE_PLANKS.getDefaultState();
	}

	protected IBlockState getFenceBlock()
	{
	    return ModBlocks.AZURE_FENCE.getDefaultState();
	}

	protected boolean isSupportingBox(World p_189918_1_, StructureBoundingBox p_189918_2_, int p_189918_3_,
		int p_189918_4_, int p_189918_5_, int p_189918_6_)
	{
	    for (int i = p_189918_3_; i <= p_189918_4_; ++i)
	    {
		if (this.getBlockStateFromPos(p_189918_1_, i, p_189918_5_ + 1, p_189918_6_, p_189918_2_)
			.getMaterial() == Material.AIR)
		{
		    return false;
		}
	    }

	    return true;
	}
	
	/**
	 * Fill the given area with the selected blocks
	 */
	protected void fillWithBlocks(World worldIn, StructureBoundingBox boundingboxIn, int xMin, int yMin, int zMin,
		int xMax, int yMax, int zMax, IBlockState boundaryBlockState, IBlockState insideBlockState, boolean existingOnly)
	{
	    for (int i = yMin; i <= yMax; ++i)
	    {
		for (int j = xMin; j <= xMax; ++j)
		{
		    for (int k = zMin; k <= zMax; ++k)
		    {
			if (!existingOnly)
			{
			    if (i != yMin && i != yMax && j != xMin && j != xMax && k != zMin && k != zMax)
			    {
				this.setBlockState(worldIn, insideBlockState, j, i, k, boundingboxIn);
			    } else
			    {
				this.setBlockState(worldIn, boundaryBlockState, j, i, k, boundingboxIn);
			    }
			}
		    }
		}
	    }
	}
    }
    
    public static void registerStructurePieces()
    {
	MapGenStructureIO.registerStructureComponent(StructureAzureMineshaftPieces.Corridor.class, "AMSCorridor");
	MapGenStructureIO.registerStructureComponent(StructureAzureMineshaftPieces.Cross.class, "AMSCrossing");
	MapGenStructureIO.registerStructureComponent(StructureAzureMineshaftPieces.Room.class, "AMSRoom");
	MapGenStructureIO.registerStructureComponent(StructureAzureMineshaftPieces.Stairs.class, "AMSStairs");
	MapGenStructureIO.registerStructureComponent(StructureAzureMineshaftPieces.WoodHut.class, "AMSWoodHut");

    }

    private static StructureAzureMineshaftPieces.Piece createRandomShaftPiece(List<StructureComponent> structures,
	    Random rand, int x, int y, int z, @Nullable EnumFacing facing, int p_189940_6_)
    {
	int i = rand.nextInt(100);

	if (i >= 80)
	{
	    StructureBoundingBox structureboundingbox = StructureAzureMineshaftPieces.Cross.findCrossing(structures,
		    rand, x, y, z, facing);

	    if (structureboundingbox != null)
	    {
		return new StructureAzureMineshaftPieces.Cross(p_189940_6_, rand, structureboundingbox, facing);
	    }
	} else if (i >= 70)
	{
	    StructureBoundingBox structureboundingbox1 = StructureAzureMineshaftPieces.Stairs.findStairs(structures,
		    rand, x, y, z, facing);

	    if (structureboundingbox1 != null)
	    {
		return new StructureAzureMineshaftPieces.Stairs(p_189940_6_, rand, structureboundingbox1, facing);
	    }
	} else if (i >= 60)
	{
	    int heightOffset = -1;
	    return StructureAzureMineshaftPieces.WoodHut.createPiece(structures, rand, x, y + heightOffset, z, facing,
		    p_189940_6_);
	} else
	{
	    StructureBoundingBox structureboundingbox2 = StructureAzureMineshaftPieces.Corridor
		    .findCorridorSize(structures, rand, x, y, z, facing);

	    if (structureboundingbox2 != null)
	    {
		return new StructureAzureMineshaftPieces.Corridor(p_189940_6_, rand, structureboundingbox2, facing);
	    }
	}

	return null;
    }

    private static StructureAzureMineshaftPieces.Piece generateAndAddPiece(StructureComponent component,
	    List<StructureComponent> structures, Random rand, int x, int y, int z, EnumFacing facing, int p_189938_7_)
    {
	if (p_189938_7_ > 8)
	{
	    return null;
	} else if (Math.abs(x - component.getBoundingBox().minX) <= 80
		&& Math.abs(z - component.getBoundingBox().minZ) <= 80)
	{
	    StructureAzureMineshaftPieces.Piece structuremineshaftpieces$piece = createRandomShaftPiece(structures,
		    rand, x, y, z, facing, p_189938_7_ + 1);

	    if (structuremineshaftpieces$piece != null)
	    {
		structures.add(structuremineshaftpieces$piece);
		structuremineshaftpieces$piece.buildComponent(component, structures, rand);
	    }

	    return structuremineshaftpieces$piece;
	} else
	{
	    return null;
	}
    }

    public static class Corridor extends StructureAzureMineshaftPieces.Piece
    {
	/**
	 * A count of the different sections of this mine. The space between ceiling
	 * supports.
	 */
	private int sectionCount;

	public Corridor()
	{
	}

	/**
	 * (abstract) Helper method to write subclass data to NBT
	 */
	protected void writeStructureToNBT(NBTTagCompound tagCompound)
	{
	    tagCompound.setInteger("Num", this.sectionCount);
	}

	/**
	 * (abstract) Helper method to read subclass data from NBT
	 */
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
	{
	    this.sectionCount = tagCompound.getInteger("Num");
	}

	public Corridor(int p_i47140_1_, Random p_i47140_2_, StructureBoundingBox p_i47140_3_, EnumFacing p_i47140_4_)
	{
	    super(p_i47140_1_);
	    this.setCoordBaseMode(p_i47140_4_);
	    this.boundingBox = p_i47140_3_;

	    if (this.getCoordBaseMode().getAxis() == EnumFacing.Axis.Z)
	    {
		this.sectionCount = p_i47140_3_.getZSize() / 5;
	    } else
	    {
		this.sectionCount = p_i47140_3_.getXSize() / 5;
	    }
	}

	public static StructureBoundingBox findCorridorSize(List<StructureComponent> p_175814_0_, Random rand, int x,
		int y, int z, EnumFacing facing)
	{
	    StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);
	    int i;

	    for (i = rand.nextInt(3) + 2; i > 0; --i)
	    {
		int j = i * 5;

		switch (facing)
		{
		case NORTH:
		default:
		    structureboundingbox.maxX = x + 2;
		    structureboundingbox.minZ = z - (j - 1);
		    break;
		case SOUTH:
		    structureboundingbox.maxX = x + 2;
		    structureboundingbox.maxZ = z + (j - 1);
		    break;
		case WEST:
		    structureboundingbox.minX = x - (j - 1);
		    structureboundingbox.maxZ = z + 2;
		    break;
		case EAST:
		    structureboundingbox.maxX = x + (j - 1);
		    structureboundingbox.maxZ = z + 2;
		}

		if (StructureComponent.findIntersecting(p_175814_0_, structureboundingbox) == null)
		{
		    break;
		}
	    }

	    return i > 0 ? structureboundingbox : null;
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
	{
	    int i = this.getComponentType();
	    int j = rand.nextInt(4);
	    EnumFacing enumfacing = this.getCoordBaseMode();

	    if (enumfacing != null)
	    {
		switch (enumfacing)
		{
		case NORTH:
		default:

		    if (j <= 1)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX, this.boundingBox.minY,
				this.boundingBox.minZ - 1, enumfacing, i);
		    } else if (j == 2)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX - 1, this.boundingBox.minY,
				this.boundingBox.minZ, EnumFacing.WEST, i);
		    } else
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.maxX + 1, this.boundingBox.minY,
				this.boundingBox.minZ, EnumFacing.EAST, i);
		    }

		    break;
		case SOUTH:

		    if (j <= 1)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX, this.boundingBox.minY,
				this.boundingBox.maxZ + 1, enumfacing, i);
		    } else if (j == 2)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX - 1, this.boundingBox.minY,
				this.boundingBox.maxZ - 3, EnumFacing.WEST, i);
		    } else
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.maxX + 1, this.boundingBox.minY,
				this.boundingBox.maxZ - 3, EnumFacing.EAST, i);
		    }

		    break;
		case WEST:

		    if (j <= 1)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX - 1, this.boundingBox.minY,
				this.boundingBox.minZ, enumfacing, i);
		    } else if (j == 2)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX, this.boundingBox.minY,
				this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
		    } else
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.minX, this.boundingBox.minY,
				this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
		    }

		    break;
		case EAST:

		    if (j <= 1)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.maxX + 1, this.boundingBox.minY,
				this.boundingBox.minZ, enumfacing, i);
		    } else if (j == 2)
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.maxX - 3, this.boundingBox.minY,
				this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
		    } else
		    {
			StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				this.boundingBox.maxX - 3, this.boundingBox.minY,
				this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
		    }
		}
	    }

	    if (i < 8)
	    {
		if (enumfacing != EnumFacing.NORTH && enumfacing != EnumFacing.SOUTH)
		{
		    for (int i1 = this.boundingBox.minX + 3; i1 + 3 <= this.boundingBox.maxX; i1 += 5)
		    {
			int j1 = rand.nextInt(5);

			if (j1 == 0)
			{
			    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, i1,
				    this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i + 1);
			} else if (j1 == 1)
			{
			    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, i1,
				    this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i + 1);
			}
		    }
		} else
		{
		    for (int k = this.boundingBox.minZ + 3; k + 3 <= this.boundingBox.maxZ; k += 5)
		    {
			int l = rand.nextInt(5);

			if (l == 0)
			{
			    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				    this.boundingBox.minX - 1, this.boundingBox.minY, k, EnumFacing.WEST, i + 1);
			} else if (l == 1)
			{
			    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
				    this.boundingBox.maxX + 1, this.boundingBox.minY, k, EnumFacing.EAST, i + 1);
			}
		    }
		}
	    }
	}

	/**
	 * Adds chest to the structure and sets its contents
	 */
	protected boolean generateChest(World worldIn, StructureBoundingBox structurebb, Random randomIn, int x, int y,
		int z, ResourceLocation loot)
	{
	    BlockPos blockpos = new BlockPos(this.getXWithOffset(x, z), this.getYWithOffset(y),
		    this.getZWithOffset(x, z));

	    if (structurebb.isVecInside(blockpos) && worldIn.getBlockState(blockpos).getMaterial() == Material.AIR
		    && worldIn.getBlockState(blockpos.down()).getMaterial() != Material.AIR)
	    {
		IBlockState iblockstate = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE,
			randomIn.nextBoolean() ? BlockRailBase.EnumRailDirection.NORTH_SOUTH
				: BlockRailBase.EnumRailDirection.EAST_WEST);
		this.setBlockState(worldIn, iblockstate, x, y, z, structurebb);
		EntityMinecartChest entityminecartchest = new EntityMinecartChest(worldIn,
			(double) ((float) blockpos.getX() + 0.5F), (double) ((float) blockpos.getY() + 0.5F),
			(double) ((float) blockpos.getZ() + 0.5F));
		entityminecartchest.setLootTable(loot, randomIn.nextLong());
		worldIn.spawnEntity(entityminecartchest);
		return true;
	    } else
	    {
		return false;
	    }
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob
	 * Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
	{
	    if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
	    {
		return false;
	    } else
	    {
		int i = 0;
		int j = 2;
		int k = 0;
		int l = 2;
		int sections = this.sectionCount * 5 - 1;
		IBlockState planks = this.getPlanksBlock();
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 2, 2, sections, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		
		for (int j1 = 0; j1 < this.sectionCount; ++j1)
		{
		    int k1 = 2 + j1 * 5;
		    this.placeSupport(worldIn, structureBoundingBoxIn, 0, 0, k1, 2, 2, randomIn);

		    if (randomIn.nextInt(100) == 0)
		    {
			this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 2, 0, k1 - 1,
				LootTableList.CHESTS_ABANDONED_MINESHAFT);
		    }

		    if (randomIn.nextInt(100) == 0)
		    {
			this.generateChest(worldIn, structureBoundingBoxIn, randomIn, 0, 0, k1 + 1,
				LootTableList.CHESTS_ABANDONED_MINESHAFT);
		    }
		}

		/**
		 * Set the planks below the mineshaft
		 */
		int y = -1;
		int x = 1;
		for (int z = -1; z <= sections; ++z)
		{
		    IBlockState iblockstate3 = this.getBlockStateFromPos(worldIn, x, y, z, structureBoundingBoxIn);

		    if (iblockstate3.getMaterial() == Material.AIR)
		    {
			this.setBlockState(worldIn, planks, x, y, z, structureBoundingBoxIn);
		    }
		}

		/**
		 * Spawn rails
		 */
		IBlockState rail = Blocks.RAIL.getDefaultState().withProperty(BlockRail.SHAPE, BlockRailBase.EnumRailDirection.NORTH_SOUTH);

		for (int z = -1; z <= sections; ++z)
		{
		    IBlockState iblockstate2 = this.getBlockStateFromPos(worldIn, x, y, z, structureBoundingBoxIn);

		    if (iblockstate2.getMaterial() != Material.AIR && iblockstate2.isFullBlock())
		    {
			this.setBlockState(worldIn, rail, x, y + 1, z, structureBoundingBoxIn);
		    }
		}

		return true;
	    }
	}

	private void placeSupport(World world, StructureBoundingBox box, int i1, int i2, int i3, int i4, int i5, Random rand)
	{
	    float torch_chance = 1.0f;

	    if (this.isSupportingBox(world, box, i1, i5, i4, i3))
	    {
		IBlockState planks = this.getPlanksBlock();
		IBlockState fence = this.getFenceBlock();
		IBlockState air = Blocks.AIR.getDefaultState();
		this.fillWithBlocks(world, box, i1, i2, i3, i1, i4 - 1, i3, fence, air, false);
		this.fillWithBlocks(world, box, i5, i2, i3, i5, i4 - 1, i3, fence, air, false);

		if (rand.nextInt(4) == 0)
		{
		    this.fillWithBlocks(world, box, i1, i4, i3, i1, i4, i3, planks, air, false);
		    this.fillWithBlocks(world, box, i5, i4, i3, i5, i4, i3, planks, air, false);
		} else
		{
		    this.fillWithBlocks(world, box, i1, i4, i3, i5, i4, i3, planks, air, false);
		    this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.SOUTH), i1 + 1, i4, i3 - 1, box);
		    this.setBlockState(world, Blocks.TORCH.getDefaultState().withProperty(BlockTorch.FACING, EnumFacing.NORTH), i1 + 1, i4, i3 + 1, box);

		}
	    }
	}
    }

    public static class Cross extends StructureAzureMineshaftPieces.Piece
    {
	private EnumFacing corridorDirection;
	private boolean isMultipleFloors;

	public Cross()
	{
	}

	/**
	 * (abstract) Helper method to write subclass data to NBT
	 */
	protected void writeStructureToNBT(NBTTagCompound tagCompound)
	{
	    tagCompound.setBoolean("tf", this.isMultipleFloors);
	    tagCompound.setInteger("D", this.corridorDirection.getHorizontalIndex());
	}

	/**
	 * (abstract) Helper method to read subclass data from NBT
	 */
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
	{
	    this.isMultipleFloors = tagCompound.getBoolean("tf");
	    this.corridorDirection = EnumFacing.getHorizontal(tagCompound.getInteger("D"));
	}

	public Cross(int p_i47139_1_, Random p_i47139_2_, StructureBoundingBox p_i47139_3_,
		@Nullable EnumFacing p_i47139_4_)
	{
	    super(p_i47139_1_);
	    this.corridorDirection = p_i47139_4_;
	    this.boundingBox = p_i47139_3_;
	    this.isMultipleFloors = p_i47139_3_.getYSize() > 3;
	}

	public static StructureBoundingBox findCrossing(List<StructureComponent> listIn, Random rand, int x, int y,
		int z, EnumFacing facing)
	{
	    StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y, z, x, y + 2, z);

	    if (rand.nextInt(4) == 0)
	    {
		structureboundingbox.maxY += 4;
	    }

	    switch (facing)
	    {
	    case NORTH:
	    default:
		structureboundingbox.minX = x - 1;
		structureboundingbox.maxX = x + 3;
		structureboundingbox.minZ = z - 4;
		break;
	    case SOUTH:
		structureboundingbox.minX = x - 1;
		structureboundingbox.maxX = x + 3;
		structureboundingbox.maxZ = z + 3 + 1;
		break;
	    case WEST:
		structureboundingbox.minX = x - 4;
		structureboundingbox.minZ = z - 1;
		structureboundingbox.maxZ = z + 3;
		break;
	    case EAST:
		structureboundingbox.maxX = x + 3 + 1;
		structureboundingbox.minZ = z - 1;
		structureboundingbox.maxZ = z + 3;
	    }

	    return StructureComponent.findIntersecting(listIn, structureboundingbox) != null ? null
		    : structureboundingbox;
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
	{
	    int i = this.getComponentType();

	    switch (this.corridorDirection)
	    {
	    case NORTH:
	    default:
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
		break;
	    case SOUTH:
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
		break;
	    case WEST:
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.WEST, i);
		break;
	    case EAST:
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
		StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.maxX + 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, EnumFacing.EAST, i);
	    }

	    if (this.isMultipleFloors)
	    {
		if (rand.nextBoolean())
		{
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
			    this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ - 1,
			    EnumFacing.NORTH, i);
		}

		if (rand.nextBoolean())
		{
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
			    this.boundingBox.minX - 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1,
			    EnumFacing.WEST, i);
		}

		if (rand.nextBoolean())
		{
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
			    this.boundingBox.maxX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.minZ + 1,
			    EnumFacing.EAST, i);
		}

		if (rand.nextBoolean())
		{
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
			    this.boundingBox.minX + 1, this.boundingBox.minY + 3 + 1, this.boundingBox.maxZ + 1,
			    EnumFacing.SOUTH, i);
		}
	    }
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob
	 * Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
	{
	    if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
	    {
		return false;
	    } else
	    {
		IBlockState iblockstate = this.getPlanksBlock();

		if (this.isMultipleFloors)
		{
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1,
			    this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1,
			    this.boundingBox.minY + 3 - 1, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(),
			    Blocks.AIR.getDefaultState(), false);
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY,
			    this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.minY + 3 - 1,
			    this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(),
			    false);
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1,
			    this.boundingBox.maxY - 2, this.boundingBox.minZ, this.boundingBox.maxX - 1,
			    this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(),
			    Blocks.AIR.getDefaultState(), false);
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX,
			    this.boundingBox.maxY - 2, this.boundingBox.minZ + 1, this.boundingBox.maxX,
			    this.boundingBox.maxY, this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(),
			    Blocks.AIR.getDefaultState(), false);
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1,
			    this.boundingBox.minY + 3, this.boundingBox.minZ + 1, this.boundingBox.maxX - 1,
			    this.boundingBox.minY + 3, this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(),
			    Blocks.AIR.getDefaultState(), false);
		} else
		{
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1,
			    this.boundingBox.minY, this.boundingBox.minZ, this.boundingBox.maxX - 1,
			    this.boundingBox.maxY, this.boundingBox.maxZ, Blocks.AIR.getDefaultState(),
			    Blocks.AIR.getDefaultState(), false);
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY,
			    this.boundingBox.minZ + 1, this.boundingBox.maxX, this.boundingBox.maxY,
			    this.boundingBox.maxZ - 1, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(),
			    false);
		}

		this.placeSupportPillar(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
		this.placeSupportPillar(worldIn, structureBoundingBoxIn, this.boundingBox.minX + 1,
			this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);
		this.placeSupportPillar(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1,
			this.boundingBox.minY, this.boundingBox.minZ + 1, this.boundingBox.maxY);
		this.placeSupportPillar(worldIn, structureBoundingBoxIn, this.boundingBox.maxX - 1,
			this.boundingBox.minY, this.boundingBox.maxZ - 1, this.boundingBox.maxY);

		for (int i = this.boundingBox.minX; i <= this.boundingBox.maxX; ++i)
		{
		    for (int j = this.boundingBox.minZ; j <= this.boundingBox.maxZ; ++j)
		    {
			if (this.getBlockStateFromPos(worldIn, i, this.boundingBox.minY - 1, j, structureBoundingBoxIn)
				.getMaterial() == Material.AIR
				&& this.getSkyBrightness(worldIn, i, this.boundingBox.minY - 1, j,
					structureBoundingBoxIn) < 8)
			{
			    this.setBlockState(worldIn, iblockstate, i, this.boundingBox.minY - 1, j,
				    structureBoundingBoxIn);
			}
		    }
		}

		return true;
	    }
	}

	private void placeSupportPillar(World p_189923_1_, StructureBoundingBox p_189923_2_, int p_189923_3_,
		int p_189923_4_, int p_189923_5_, int p_189923_6_)
	{
	    if (this.getBlockStateFromPos(p_189923_1_, p_189923_3_, p_189923_6_ + 1, p_189923_5_, p_189923_2_)
		    .getMaterial() != Material.AIR)
	    {
		this.fillWithBlocks(p_189923_1_, p_189923_2_, p_189923_3_, p_189923_4_, p_189923_5_, p_189923_3_,
			p_189923_6_, p_189923_5_, this.getPlanksBlock(), Blocks.AIR.getDefaultState(), false);
	    }
	}
    }

    public static class Room extends StructureAzureMineshaftPieces.Piece
    {
	/** List of other Mineshaft components linked to this room. */
	private final List<StructureBoundingBox> connectedRooms = Lists.<StructureBoundingBox>newLinkedList();

	public Room()
	{
	}

	public Room(int p_i47137_1_, Random room, int p_i47137_3_, int p_i47137_4_)
	{
	    super(p_i47137_1_);
	    this.boundingBox = new StructureBoundingBox(p_i47137_3_, 50, p_i47137_4_, p_i47137_3_ + 7 + room.nextInt(6),
		    54 + room.nextInt(6), p_i47137_4_ + 7 + room.nextInt(6));
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
	{
	    int i = this.getComponentType();
	    int j = this.boundingBox.getYSize() - 3 - 1;

	    if (j <= 0)
	    {
		j = 1;
	    }

	    int k;

	    for (k = 0; k < this.boundingBox.getXSize(); k = k + 4)
	    {
		k = k + rand.nextInt(this.boundingBox.getXSize());

		if (k + 3 > this.boundingBox.getXSize())
		{
		    break;
		}

		StructureAzureMineshaftPieces.Piece structuremineshaftpieces$piece = StructureAzureMineshaftPieces
			.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + k,
				this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ - 1,
				EnumFacing.NORTH, i);

		if (structuremineshaftpieces$piece != null)
		{
		    StructureBoundingBox structureboundingbox = structuremineshaftpieces$piece.getBoundingBox();
		    this.connectedRooms.add(new StructureBoundingBox(structureboundingbox.minX,
			    structureboundingbox.minY, this.boundingBox.minZ, structureboundingbox.maxX,
			    structureboundingbox.maxY, this.boundingBox.minZ + 1));
		}
	    }

	    for (k = 0; k < this.boundingBox.getXSize(); k = k + 4)
	    {
		k = k + rand.nextInt(this.boundingBox.getXSize());

		if (k + 3 > this.boundingBox.getXSize())
		{
		    break;
		}

		StructureAzureMineshaftPieces.Piece structuremineshaftpieces$peice1 = StructureAzureMineshaftPieces
			.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX + k,
				this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.maxZ + 1,
				EnumFacing.SOUTH, i);

		if (structuremineshaftpieces$peice1 != null)
		{
		    StructureBoundingBox structureboundingbox1 = structuremineshaftpieces$peice1.getBoundingBox();
		    this.connectedRooms.add(new StructureBoundingBox(structureboundingbox1.minX,
			    structureboundingbox1.minY, this.boundingBox.maxZ - 1, structureboundingbox1.maxX,
			    structureboundingbox1.maxY, this.boundingBox.maxZ));
		}
	    }

	    for (k = 0; k < this.boundingBox.getZSize(); k = k + 4)
	    {
		k = k + rand.nextInt(this.boundingBox.getZSize());

		if (k + 3 > this.boundingBox.getZSize())
		{
		    break;
		}

		StructureAzureMineshaftPieces.Piece structuremineshaftpieces$peice2 = StructureAzureMineshaftPieces
			.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX - 1,
				this.boundingBox.minY + rand.nextInt(j) + 1, this.boundingBox.minZ + k, EnumFacing.WEST,
				i);

		if (structuremineshaftpieces$peice2 != null)
		{
		    StructureBoundingBox structureboundingbox2 = structuremineshaftpieces$peice2.getBoundingBox();
		    this.connectedRooms.add(new StructureBoundingBox(this.boundingBox.minX, structureboundingbox2.minY,
			    structureboundingbox2.minZ, this.boundingBox.minX + 1, structureboundingbox2.maxY,
			    structureboundingbox2.maxZ));
		}
	    }

	    for (k = 0; k < this.boundingBox.getZSize(); k = k + 4)
	    {
		k = k + rand.nextInt(this.boundingBox.getZSize());

		if (k + 3 > this.boundingBox.getZSize())
		{
		    break;
		}

		StructureComponent structurecomponent = StructureAzureMineshaftPieces.generateAndAddPiece(componentIn,
			listIn, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + rand.nextInt(j) + 1,
			this.boundingBox.minZ + k, EnumFacing.EAST, i);

		if (structurecomponent != null)
		{
		    StructureBoundingBox structureboundingbox3 = structurecomponent.getBoundingBox();
		    this.connectedRooms.add(new StructureBoundingBox(this.boundingBox.maxX - 1,
			    structureboundingbox3.minY, structureboundingbox3.minZ, this.boundingBox.maxX,
			    structureboundingbox3.maxY, structureboundingbox3.maxZ));
		}
	    }
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob
	 * Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
	{
	    if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
	    {
		return false;
	    } else
	    {

		// Fills the floor with dirt?
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY,
			this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.minY, this.boundingBox.maxZ,
			ModBlocks.AZURE_PLANKS.getDefaultState(), Blocks.AIR.getDefaultState(), true);

		this.fillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX, this.boundingBox.minY + 1,
			this.boundingBox.minZ, this.boundingBox.maxX,
			Math.min(this.boundingBox.minY + 3, this.boundingBox.maxY), this.boundingBox.maxZ,
			Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);

		for (StructureBoundingBox structureboundingbox : this.connectedRooms)
		{
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, structureboundingbox.minX,
			    structureboundingbox.maxY - 2, structureboundingbox.minZ, structureboundingbox.maxX,
			    structureboundingbox.maxY, structureboundingbox.maxZ, Blocks.AIR.getDefaultState(),
			    Blocks.AIR.getDefaultState(), false);
		}

		this.randomlyRareFillWithBlocks(worldIn, structureBoundingBoxIn, this.boundingBox.minX,
			this.boundingBox.minY + 4, this.boundingBox.minZ, this.boundingBox.maxX, this.boundingBox.maxY,
			this.boundingBox.maxZ, Blocks.AIR.getDefaultState(), false);
		return true;
	    }
	}

	public void offset(int x, int y, int z)
	{
	    super.offset(x, y, z);

	    for (StructureBoundingBox structureboundingbox : this.connectedRooms)
	    {
		structureboundingbox.offset(x, y, z);
	    }
	}

	/**
	 * (abstract) Helper method to write subclass data to NBT
	 */
	protected void writeStructureToNBT(NBTTagCompound tagCompound)
	{
	    NBTTagList nbttaglist = new NBTTagList();

	    for (StructureBoundingBox structureboundingbox : this.connectedRooms)
	    {
		nbttaglist.appendTag(structureboundingbox.toNBTTagIntArray());
	    }

	    tagCompound.setTag("Entrances", nbttaglist);
	}

	/**
	 * (abstract) Helper method to read subclass data from NBT
	 */
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
	{
	    NBTTagList nbttaglist = tagCompound.getTagList("Entrances", 11);

	    for (int i = 0; i < nbttaglist.tagCount(); ++i)
	    {
		this.connectedRooms.add(new StructureBoundingBox(nbttaglist.getIntArrayAt(i)));
	    }
	}
    }

    public static class Stairs extends StructureAzureMineshaftPieces.Piece
    {
	public Stairs()
	{
	}

	public Stairs(int p_i47136_1_, Random p_i47136_2_, StructureBoundingBox p_i47136_3_, EnumFacing p_i47136_4_)
	{
	    super(p_i47136_1_);
	    this.setCoordBaseMode(p_i47136_4_);
	    this.boundingBox = p_i47136_3_;
	}

	public static StructureBoundingBox findStairs(List<StructureComponent> listIn, Random rand, int x, int y, int z,
		EnumFacing facing)
	{
	    StructureBoundingBox structureboundingbox = new StructureBoundingBox(x, y - 5, z, x, y + 2, z);

	    switch (facing)
	    {
	    case NORTH:
	    default:
		structureboundingbox.maxX = x + 2;
		structureboundingbox.minZ = z - 8;
		break;
	    case SOUTH:
		structureboundingbox.maxX = x + 2;
		structureboundingbox.maxZ = z + 8;
		break;
	    case WEST:
		structureboundingbox.minX = x - 8;
		structureboundingbox.maxZ = z + 2;
		break;
	    case EAST:
		structureboundingbox.maxX = x + 8;
		structureboundingbox.maxZ = z + 2;
	    }

	    return StructureComponent.findIntersecting(listIn, structureboundingbox) != null ? null
		    : structureboundingbox;
	}

	/**
	 * Initiates construction of the Structure Component picked, at the current
	 * Location of StructGen
	 */
	public void buildComponent(StructureComponent componentIn, List<StructureComponent> listIn, Random rand)
	{
	    int i = this.getComponentType();
	    EnumFacing enumfacing = this.getCoordBaseMode();

	    if (enumfacing != null)
	    {
		switch (enumfacing)
		{
		case NORTH:
		default:
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX,
			    this.boundingBox.minY, this.boundingBox.minZ - 1, EnumFacing.NORTH, i);
		    break;
		case SOUTH:
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand, this.boundingBox.minX,
			    this.boundingBox.minY, this.boundingBox.maxZ + 1, EnumFacing.SOUTH, i);
		    break;
		case WEST:
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
			    this.boundingBox.minX - 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.WEST,
			    i);
		    break;
		case EAST:
		    StructureAzureMineshaftPieces.generateAndAddPiece(componentIn, listIn, rand,
			    this.boundingBox.maxX + 1, this.boundingBox.minY, this.boundingBox.minZ, EnumFacing.EAST,
			    i);
		}
	    }
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob
	 * Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
	{
	    if (this.isLiquidInStructureBoundingBox(worldIn, structureBoundingBoxIn))
	    {
		return false;
	    } else
	    {
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5, 0, 2, 7, 1, Blocks.AIR.getDefaultState(),
			Blocks.AIR.getDefaultState(), false);
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 7, 2, 2, 8, Blocks.AIR.getDefaultState(),
			Blocks.AIR.getDefaultState(), false);

		for (int i = 0; i < 5; ++i)
		{
		    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 5 - i - (i < 4 ? 1 : 0), 2 + i, 2, 7 - i,
			    2 + i, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
		}

		return true;
	    }
	}

	@Override
	protected void writeStructureToNBT(NBTTagCompound tagCompound){}

	@Override
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_){}
    }

    public static class WoodHut extends StructureAzureMineshaftPieces.Piece
    {
	private boolean isTallHouse;
	private int tablePosition;

	public WoodHut()
	{
	}

	public WoodHut(int type, Random rand, StructureBoundingBox structurebb, EnumFacing facing)
	{
	    super(type);
	    this.setCoordBaseMode(facing);
	    this.boundingBox = structurebb;
	    this.isTallHouse = rand.nextBoolean();
	    this.tablePosition = rand.nextInt(3);
	}

	/**
	 * (abstract) Helper method to write subclass data to NBT
	 */
	protected void writeStructureToNBT(NBTTagCompound tagCompound)
	{
	    tagCompound.setInteger("T", this.tablePosition);
	    tagCompound.setBoolean("C", this.isTallHouse);
	}

	/**
	 * (abstract) Helper method to read subclass data from NBT
	 */
	protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
	{
	    this.tablePosition = tagCompound.getInteger("T");
	    this.isTallHouse = tagCompound.getBoolean("C");
	}

	public static StructureAzureMineshaftPieces.WoodHut createPiece(List<StructureComponent> structures,
		Random rand, int x, int y, int z, EnumFacing facing, int p_175853_7_)
	{
	    StructureBoundingBox structureboundingbox = StructureBoundingBox.getComponentToAddBoundingBox(x, y, z, 0, 0,
		    0, 4, 6, 5, facing);
	    return StructureComponent.findIntersecting(structures, structureboundingbox) == null
		    ? new StructureAzureMineshaftPieces.WoodHut(p_175853_7_, rand, structureboundingbox, facing)
		    : null;
	}

	/**
	 * second Part of Structure generating, this for example places Spiderwebs, Mob
	 * Spawners, it closes Mineshafts at the end, it adds Fences...
	 */
	public boolean addComponentParts(World worldIn, Random randomIn, StructureBoundingBox structureBoundingBoxIn)
	{
	    IBlockState stone = ModBlocks.DARK_AZURE_STONE.getDefaultState();
	    IBlockState planks = ModBlocks.AZURE_PLANKS.getDefaultState();
	    IBlockState stairs = Blocks.STONE_STAIRS.getDefaultState().withProperty(BlockStairs.FACING, EnumFacing.NORTH);
	    IBlockState log = ModBlocks.AZURE_LOG.getDefaultState();
	    IBlockState fence = ModBlocks.AZURE_FENCE.getDefaultState();
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 1, 3, 5, 4, Blocks.AIR.getDefaultState(), Blocks.AIR.getDefaultState(), false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, 0, 3, 0, 4, stone, stone, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 0, 1, 2, 0, 3, ModBlocks.DARK_AZURE_STONE.getDefaultState(), ModBlocks.DARK_AZURE_STONE.getDefaultState(), false);

	    if (this.isTallHouse)
	    {
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 4, 1, 2, 4, 3, log, log, false);
	    } else
	    {
		this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 5, 1, 2, 5, 3, log, log, false);
	    }

	    this.setBlockState(worldIn, log, 1, 4, 0, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 2, 4, 0, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 1, 4, 4, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 2, 4, 4, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 0, 4, 1, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 0, 4, 2, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 0, 4, 3, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 3, 4, 1, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 3, 4, 2, structureBoundingBoxIn);
	    this.setBlockState(worldIn, log, 3, 4, 3, structureBoundingBoxIn);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 0, 0, 3, 0, log, log, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 0, 3, 3, 0, log, log, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 4, 0, 3, 4, log, log, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 4, 3, 3, 4, log, log, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 1, 1, 0, 3, 3, planks, planks, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 3, 1, 1, 3, 3, 3, planks, planks, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 0, 2, 3, 0, planks, planks, false);
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 1, 1, 4, 2, 3, 4, planks, planks, false);
	    this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 0, 2, 2, structureBoundingBoxIn);
	    this.setBlockState(worldIn, Blocks.GLASS_PANE.getDefaultState(), 3, 2, 2, structureBoundingBoxIn);

	    if (this.tablePosition > 0)
	    {
		this.setBlockState(worldIn, fence, this.tablePosition, 1, 3, structureBoundingBoxIn);
		this.setBlockState(worldIn, Blocks.WOODEN_PRESSURE_PLATE.getDefaultState(), this.tablePosition, 2, 3,
			structureBoundingBoxIn);
	    }

	    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 1, 0, structureBoundingBoxIn);
	    this.setBlockState(worldIn, Blocks.AIR.getDefaultState(), 1, 2, 0, structureBoundingBoxIn);
	    this.generateDoor(worldIn, structureBoundingBoxIn, randomIn, 1, 1, 0, EnumFacing.NORTH, Blocks.OAK_DOOR);

	    // Fill with a porch
	    this.fillWithBlocks(worldIn, structureBoundingBoxIn, 0, 0, -1, 2, 0, -1, planks, planks, false);
	    
	    return true;
	}
    }
}