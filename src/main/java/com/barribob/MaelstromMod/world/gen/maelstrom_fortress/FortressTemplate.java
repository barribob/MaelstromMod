package com.barribob.MaelstromMod.world.gen.maelstrom_fortress;

import java.util.List;
import java.util.Random;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromIllager;
import com.barribob.MaelstromMod.entity.entities.TileEntityMalestromSpawner;
import com.barribob.MaelstromMod.init.ModBlocks;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponent;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

/**
 * 
 * The template class that holds all placement positions and settings as well as the template itself
 * Used for generating the maelstrom fortress
 *
 */
public class FortressTemplate extends StructureComponentTemplate
{
    private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(true);
    private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);

    private String pieceName;
    private Rotation rotation;
    private int distance;

    /**
     * Whether this template should overwrite existing blocks. Replaces only air if
     * false.
     */
    private boolean overwrite;

    public FortressTemplate()
    {
    }

    public FortressTemplate(TemplateManager manager, String type, int distance, BlockPos pos, Rotation rotation, boolean overwriteIn)
    {
	super(0);
	this.pieceName = type;
	this.templatePosition = pos;
	this.rotation = rotation;
	this.overwrite = overwriteIn;
	this.loadTemplate(manager);
	this.distance = distance;
    }

    public int getDistance()
    {
	return this.distance;
    }

    public void setComponentType(int i)
    {
	this.componentType = i;
    }

    public PlacementSettings getPlacementSettings()
    {
	return this.placeSettings;
    }

    public BlockPos getTemplatePosition()
    {
	return this.templatePosition;
    }

    public Template getTemplate()
    {
	return this.template;
    }

    /**
     * Discover if bounding box can fit within the current bounding box object.
     * Excludes bounding boxes that touch each other and are not actually inside
     */
    public static StructureComponent findIntersectingExclusive(List<StructureComponent> listIn, StructureBoundingBox box)
    {
	for (StructureComponent structurecomponent : listIn)
	{
	    if (structurecomponent.getBoundingBox() != null && structurecomponent.getBoundingBox().intersectsWith(box.minX + 2, box.minZ + 2, box.maxX - 2, box.maxZ - 2))
	    {
		return structurecomponent;
	    }
	}

	return null;
    }

    private void loadTemplate(TemplateManager manager)
    {
	Template template = manager.getTemplate((MinecraftServer) null, new ResourceLocation(Reference.MOD_ID, "maelstrom_fortress/" + this.pieceName));
	PlacementSettings placementsettings = (this.overwrite ? OVERWRITE : INSERT).copy().setRotation(this.rotation);
	this.setup(template, this.templatePosition, placementsettings);
    }

    /**
     * (abstract) Helper method to write subclass data to NBT
     */
    protected void writeStructureToNBT(NBTTagCompound tagCompound)
    {
	super.writeStructureToNBT(tagCompound);
	tagCompound.setString("Template", this.pieceName);
	tagCompound.setString("Rot", this.rotation.name());
	tagCompound.setBoolean("OW", this.overwrite);
    }

    /**
     * (abstract) Helper method to read subclass data from NBT
     */
    protected void readStructureFromNBT(NBTTagCompound tagCompound, TemplateManager p_143011_2_)
    {
	super.readStructureFromNBT(tagCompound, p_143011_2_);
	this.pieceName = tagCompound.getString("Template");
	this.rotation = Rotation.valueOf(tagCompound.getString("Rot"));
	this.overwrite = tagCompound.getBoolean("OW");
	this.loadTemplate(p_143011_2_);
    }

    /**
     * Loads structure block data markers and handles them by their name
     */
    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
    {
	if (function.startsWith("chest"))
	{
	    BlockPos blockpos = pos.down();

	    if (sbb.isVecInside(blockpos))
	    {
		TileEntity tileentity = worldIn.getTileEntity(blockpos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableHandler.AZURE_FORTRESS, rand.nextLong());
		}
	    }
	}
	else if (function.startsWith("boss"))
	{
	    EntityMaelstromIllager entity = new EntityMaelstromIllager(worldIn);
	    entity.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
	    worldIn.spawnEntity(entity);
	}
	else if (function.startsWith("enemy"))
	{
	    worldIn.setBlockState(pos, ModBlocks.AZURE_MAELSTROM_CORE.getDefaultState(), 2);
	    TileEntity tileentity = worldIn.getTileEntity(pos);

	    if (tileentity instanceof TileEntityMalestromSpawner)
	    {
		String entityName = rand.nextInt(2) == 0 ? "shade" : "horror";
		((TileEntityMalestromSpawner) tileentity).getSpawnerBaseLogic().setEntityId(new ResourceLocation(Reference.MOD_ID + ":" + entityName));
	    }

	}
    }
    
    
}
