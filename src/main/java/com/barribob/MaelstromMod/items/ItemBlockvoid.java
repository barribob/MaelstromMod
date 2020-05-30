package com.barribob.MaelstromMod.items;

import java.util.List;
import java.util.UUID;

import com.barribob.MaelstromMod.util.ModUtils;
import com.google.common.collect.Multimap;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * An items that places blocks at the cost of some durability. It can also mine its block of choice and that replaced durability. To help building, it also increases a player's reach.
 * 
 * @author micha
 *
 */
public class ItemBlockvoid extends ItemBase
{
    protected final Block block;
    private static final UUID REACH_MODIFIER = UUID.fromString("a6323e02-d8e9-44c6-b941-f5d7155bb406");
    private static final float REACH = 5;
    private float efficiency = 30;

    public ItemBlockvoid(String name, Block block, float efficiency)
    {
	super(name);
	this.block = block;
	this.setMaxDamage(1000);
	this.efficiency = efficiency;
	this.maxStackSize = 1;
    }

    // Taken from ItemBlock
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ)
    {
	IBlockState iblockstate = worldIn.getBlockState(pos);
	Block block = iblockstate.getBlock();

	if (!block.isReplaceable(worldIn, pos))
	{
	    pos = pos.offset(facing);
	}

	ItemStack itemstack = player.getHeldItem(hand);

	if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(this.block, pos, false, facing, (Entity) null))
	{
	    int i = this.getMetadata(itemstack.getMetadata());
	    IBlockState iblockstate1 = this.block.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, hand);

	    if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
	    {
		iblockstate1 = worldIn.getBlockState(pos);
		SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
		worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
		itemstack.damageItem(1, player);
	    }

	    return EnumActionResult.PASS;
	}
	else
	{
	    return EnumActionResult.FAIL;
	}
    }

    // Taken from ItemBlock
    public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
	if (!world.setBlockState(pos, newState, 11))
	    return false;

	IBlockState state = world.getBlockState(pos);
	if (state.getBlock() == this.block)
	{
	    this.block.onBlockPlacedBy(world, pos, state, player, stack);

	    if (player instanceof EntityPlayerMP)
		CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP) player, pos, stack);
	}

	return true;
    }

    // Increase the placement reach of the item
    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_MODIFIER, "Extended Reach Modifier", REACH - 3.0D, 0).setSaved(false));
	}
	return multimap;
    }

    // Only efficient at destroying its own block
    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
	return state.getBlock() == this.block ? efficiency : super.getDestroySpeed(stack, state);
    }

    // Breaking its own blocks heals its durability
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving)
    {
	if (!worldIn.isRemote && state.getBlock() == this.block)
	{
	    stack.damageItem(-1, entityLiving);
	    return true;
	}
	else if (entityLiving instanceof EntityPlayer && state.getBlock() == this.block && stack.getItemDamage() > 0)
	{
	    worldIn.playSound((EntityPlayer) entityLiving, pos, SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.BLOCKS, 0.15f, 0.3f);
	}

	return false;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("blockvoid", new ItemStack(this.block).getDisplayName()));
	super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
