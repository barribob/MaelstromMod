package com.barribob.MaelstromMod.items;

import java.util.HashMap;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.IHasModel;
import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The base class for the mod's cooldown weapons. keeps track of cooldown and
 * calls shoot() when the gun sucessfully shoots
 *
 */
public class ItemGun extends ItemBase
{
    private int maxCooldown;
    private Item ammo;
    private static final int SMOKE_PARTICLES = 4;

    public ItemGun(String name, int cooldown, int maxDamage, Item ammo, CreativeTabs tab)
    {
	super(name, tab);
	this.maxStackSize = 1;
	this.ammo = ammo;
	this.maxCooldown = cooldown;
	this.setMaxDamage(maxDamage);
    }

    /**
     * Returns a float between 0 and 1 to represent the cooldown of the gun
     */
    public float getCooldownForDisplay(ItemStack stack)
    {
	if (stack.hasTagCompound() && stack.getTagCompound().hasKey("cooldown"))
	{
	    return stack.getTagCompound().getInteger("cooldown") / (float) this.maxCooldown;
	}

	return 0;
    }

    /**
     * Taken from the bow class. Finds the appropriate ammo for the gun
     * 
     * @param player
     * @return
     */
    private ItemStack findAmmo(EntityPlayer player)
    {
	if (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ammo)
	{
	    return player.getHeldItem(EnumHand.OFF_HAND);
	}
	else if (player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ammo)
	{
	    return player.getHeldItem(EnumHand.MAIN_HAND);
	}
	else
	{
	    for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
	    {
		ItemStack itemstack = player.inventory.getStackInSlot(i);

		if (itemstack.getItem() == ammo)
		{
		    return itemstack;
		}
	    }

	    return ItemStack.EMPTY;
	}
    }

    /**
     * Updates the gun's cooldown
     */
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
	super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	// Use a compound to record the cooldown data for an item stack
	if (stack.hasTagCompound())
	{
	    NBTTagCompound compound = stack.getTagCompound();

	    // Decrement the cooldown every tick
	    if (compound.hasKey("cooldown"))
	    {
		int cooldown = compound.getInteger("cooldown") - 1;
		compound.setInteger("cooldown", cooldown >= 0 ? cooldown : 0);
	    }
	    else
	    {
		compound.setInteger("cooldown", maxCooldown);
	    }

	    stack.setTagCompound(compound);
	}
	else
	{
	    stack.setTagCompound(new NBTTagCompound());
	}
    }

    /**
     * Called when the equipped item is right clicked. Calls the shoot function
     * after handling ammo and cooldown
     */
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
	ItemStack itemstack = playerIn.getHeldItem(handIn);
	ItemStack ammoStack = findAmmo(playerIn);

	if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("cooldown"))
	{
	    NBTTagCompound compound = itemstack.getTagCompound();

	    if ((playerIn.capabilities.isCreativeMode || !ammoStack.isEmpty()) && compound.getInteger("cooldown") <= 0)
	    {
		boolean dontConsumeAmmo = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;

		if (!dontConsumeAmmo)
		{
		    ammoStack.shrink(1);

		    if (ammoStack.isEmpty())
		    {
			playerIn.inventory.deleteStack(ammoStack);
		    }

		    itemstack.damageItem(1, playerIn);
		}

		// Only the server spawns the projectile, otherwise things get weird
		if (!worldIn.isRemote)
		{
		    shoot(worldIn, playerIn, handIn, itemstack);
		}
		else
		{
		    // Add the fire and smoke effects when the gun goes off
		    Vec3d flameOffset = playerIn.getLookVec().scale(0.5f);

		    if (handIn == EnumHand.MAIN_HAND)
		    {
			flameOffset = flameOffset.rotateYaw((float) Math.PI * -0.5f);
		    }
		    else
		    {
			flameOffset = flameOffset.rotateYaw((float) Math.PI * 0.5f);
		    }

		    flameOffset = flameOffset.add(playerIn.getLookVec());

		    worldIn.spawnParticle(EnumParticleTypes.FLAME, playerIn.posX + flameOffset.x, playerIn.posY + playerIn.getEyeHeight() + flameOffset.y,
			    playerIn.posZ + flameOffset.z, 0, 0, 0);

		    for (int i = 0; i < SMOKE_PARTICLES; i++)
		    {
			worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, playerIn.posX + flameOffset.x + ModRandom.getFloat(0.2f),
				playerIn.posY + playerIn.getEyeHeight() + flameOffset.y + ModRandom.getFloat(0.2f), playerIn.posZ + flameOffset.z + ModRandom.getFloat(0.2f),
				0, 0, 0);
		    }
		}

		compound.setInteger("cooldown", this.maxCooldown);
		itemstack.setTagCompound(compound);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	    }
	}
	return new ActionResult(EnumActionResult.FAIL, itemstack);
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
	return true;
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on
     * material.
     */
    public int getItemEnchantability()
    {
	return 1;
    }

    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
    }
}
