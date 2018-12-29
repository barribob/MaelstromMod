package com.barribob.MaelstromMod.world.gen.maelstrom_fortress;

import java.util.Random;

import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import net.minecraft.world.gen.structure.StructureComponentTemplate;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraft.world.gen.structure.template.TemplateManager;
import net.minecraft.world.storage.loot.LootTableList;

public class FortressTemplate extends StructureComponentTemplate
{
    private static final PlacementSettings OVERWRITE = (new PlacementSettings()).setIgnoreEntities(true);
    private static final PlacementSettings INSERT = (new PlacementSettings()).setIgnoreEntities(true).setReplacedBlock(Blocks.AIR);

    private String pieceName;
    private Rotation rotation;
    /**
     * Whether this template should overwrite existing blocks. Replaces only air if
     * false.
     */
    private boolean overwrite;

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

    public FortressTemplate()
    {
    }

    public FortressTemplate(TemplateManager p_i47214_1_, String p_i47214_2_, BlockPos p_i47214_3_, Rotation p_i47214_4_, boolean overwriteIn)
    {
	super(0);
	this.pieceName = p_i47214_2_;
	this.templatePosition = p_i47214_3_;
	this.rotation = p_i47214_4_;
	this.overwrite = overwriteIn;
	this.loadTemplate(p_i47214_1_);
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

    protected void handleDataMarker(String function, BlockPos pos, World worldIn, Random rand, StructureBoundingBox sbb)
    {
	if (function.startsWith("Chest"))
	{
	    BlockPos blockpos = pos.down();

	    if (sbb.isVecInside(blockpos))
	    {
		TileEntity tileentity = worldIn.getTileEntity(blockpos);

		if (tileentity instanceof TileEntityChest)
		{
		    ((TileEntityChest) tileentity).setLootTable(LootTableList.CHESTS_END_CITY_TREASURE, rand.nextLong());
		}
	    }
	}
	else if (function.startsWith("Sentry"))
	{
	    EntityShulker entityshulker = new EntityShulker(worldIn);
	    entityshulker.setPosition((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D);
	    entityshulker.setAttachmentPos(pos);
	    worldIn.spawnEntity(entityshulker);
	}
	else if (function.startsWith("Elytra"))
	{
	    EntityItemFrame entityitemframe = new EntityItemFrame(worldIn, pos, this.rotation.rotate(EnumFacing.SOUTH));
	    entityitemframe.setDisplayedItem(new ItemStack(Items.ELYTRA));
	    worldIn.spawnEntity(entityitemframe);
	}
    }
}
