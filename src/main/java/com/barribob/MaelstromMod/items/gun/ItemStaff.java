package com.barribob.MaelstromMod.items.gun;

import java.text.DecimalFormat;
import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageMana;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The base class for the mod's magical weapons. keeps track of cooldown and
 * calls shoot() when the staff successfully shoots
 *
 */
public abstract class ItemStaff extends ItemBase implements ILeveledItem, Reloadable
{
    private final int maxCooldown;
    private float level;
    private int manaCost;
    protected DecimalFormat df = new DecimalFormat("0.00");
    private Consumer<List<String>> information = (info) -> {
    };

    public ItemStaff(String name, int experienceUse, int cooldown, float useTime, float level, CreativeTabs tab)
    {
	super(name, tab);
	this.maxStackSize = 1;
	this.maxCooldown = cooldown;
	this.level = level;
	this.manaCost = experienceUse;
	this.setMaxDamage((int) (useTime / cooldown));
    }

    /**
     * If true, this item can be enchanted with damage enchantments like guns
     */
    public boolean doesDamage()
    {
	return false;
    }

    private float getEnchantedCooldown(ItemStack stack)
    {
	int reload = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reload, stack);
	return this.maxCooldown * (1 - reload * 0.1f);
    }

    /**
     * Returns a float between 0 and 1 to represent the cooldown of the staff
     */
    @Override
    public float getCooldownForDisplay(ItemStack stack)
    {
	if (stack.hasTagCompound() && stack.getTagCompound().hasKey("cooldown"))
	{
	    return stack.getTagCompound().getInteger("cooldown") / this.getEnchantedCooldown(stack);
	}

	return 0;
    }

    /**
     * Updates the staff's cooldown
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
		compound.setInteger("cooldown", (int) this.getEnchantedCooldown(stack));
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
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
	ItemStack itemstack = playerIn.getHeldItem(handIn);

	if (!worldIn.isRemote && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("cooldown"))
	{
	    NBTTagCompound compound = itemstack.getTagCompound();
	    IMana mana = playerIn.getCapability(ManaProvider.MANA, null);

	    if (mana.isLocked() && !playerIn.capabilities.isCreativeMode)
	    {
		playerIn.sendMessage(new TextComponentTranslation("mana_locked"));
		return new ActionResult(EnumActionResult.FAIL, itemstack);
	    }

	    if (compound.getInteger("cooldown") <= 0 && (playerIn.capabilities.isCreativeMode || mana.getMana() >= this.manaCost))
	    {
		if (!playerIn.capabilities.isCreativeMode)
		{
		    mana.consume(this.manaCost);
		    itemstack.damageItem(1, playerIn);
		    Main.network.sendTo(new MessageMana(mana.getMana()), (EntityPlayerMP) playerIn);
		}
		shoot(worldIn, playerIn, handIn, itemstack);
		compound.setInteger("cooldown", (int) this.getEnchantedCooldown(itemstack));
		itemstack.setTagCompound(compound);
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	    }
	}
	return new ActionResult(EnumActionResult.FAIL, itemstack);
    }

    public ItemStaff setInformation(Consumer<List<String>> information)
    {
	this.information = information;
	return this;
    }

    // List the required ammo for the gun
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(ModUtils.getDisplayLevel(this.level));
	tooltip.add(ModUtils.getCooldownTooltip(this.getEnchantedCooldown(stack)));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("mana_cost") + ": " + TextFormatting.DARK_PURPLE + this.manaCost);
	information.accept(tooltip);
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on
     * material.
     */
    @Override
    public int getItemEnchantability()
    {
	return 1;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
	return true;
    }

    @Override
    public float getLevel()
    {
	return this.level;
    }

    protected abstract void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack);
}
