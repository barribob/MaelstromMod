package com.barribob.MaelstromMod.items.gun;

import java.util.List;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.items.gun.bullet.BulletFactory;
import com.barribob.MaelstromMod.items.gun.bullet.StandardBullet;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * The base class for the mod's cooldown weapons. keeps track of cooldown and
 * calls shoot() when the gun sucessfully shoots
 *
 */
public abstract class ItemGun extends ItemBase implements ILeveledItem, Reloadable
{
    private final int maxCooldown;
    private Item ammo;
    private static final int SMOKE_PARTICLES = 4;
    private float level;
    private final float damage;
    protected BulletFactory factory;
    private Consumer<List<String>> information = (info) -> {
    };

    public ItemGun(String name, int cooldown, float damage, float useTime, Item ammo, float level, CreativeTabs tab)
    {
	super(name, tab);
	this.maxStackSize = 1;
	this.ammo = ammo;
	this.maxCooldown = cooldown;
	this.level = level;
	this.setMaxDamage((int) (useTime / cooldown));
	this.damage = damage;
	this.factory = new StandardBullet();
    }

    public ItemGun setBullet(BulletFactory factory)
    {
	this.factory = factory;
	return this;
    }

    /**
     * Returns the correct multiplier based on the level of the item stack
     */
    private float getMultiplier()
    {
	return LevelHandler.getMultiplierFromLevel(this.level);
    }

    private float getEnchantedCooldown(ItemStack stack)
    {
	int reload = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reload, stack);
	return this.maxCooldown * (1 - reload * 0.1f);
    }

    public float getEnchantedDamage(ItemStack stack)
    {
	float maxPower = ModEnchantments.gun_power.getMaxLevel();
	float power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_power, stack);
	float enchantmentBonus = 1 + ((power / maxPower) * (ModConfig.balance.progression_scale - 1));
	return this.damage * enchantmentBonus * this.getMultiplier();
    }

    /**
     * Returns a float between 0 and 1 to represent the cooldown of the gun
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
		if (entityIn instanceof EntityPlayer && ((EntityPlayer) entityIn).getHeldItem(EnumHand.MAIN_HAND).equals(stack)
			|| ((EntityPlayer) entityIn).getHeldItem(EnumHand.OFF_HAND).equals(stack))
		{
		    int cooldown = compound.getInteger("cooldown") - 1;
		    compound.setInteger("cooldown", cooldown >= 0 ? cooldown : 0);
		}
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
	ItemStack ammoStack = findAmmo(playerIn);

	if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("cooldown"))
	{
	    NBTTagCompound compound = itemstack.getTagCompound();

	    if ((playerIn.capabilities.isCreativeMode || !ammoStack.isEmpty() || this.ammo == null) && compound.getInteger("cooldown") <= 0)
	    {
		boolean dontConsumeAmmo = playerIn.capabilities.isCreativeMode || this.ammo == null
			|| EnchantmentHelper.getEnchantmentLevel(Enchantments.INFINITY, itemstack) > 0;

		if (!dontConsumeAmmo)
		{
		    ammoStack.shrink(1);

		    if (ammoStack.isEmpty())
		    {
			playerIn.inventory.deleteStack(ammoStack);
		    }
		}

		if (!playerIn.capabilities.isCreativeMode)
		{
		    itemstack.damageItem(1, playerIn);
		}

		// Only the server spawns the projectile, otherwise things get weird
		if (!worldIn.isRemote)
		{
		    shoot(worldIn, playerIn, handIn, itemstack);
		}
		else
		{
		    spawnShootParticles(worldIn, playerIn, handIn);
		}

		compound.setInteger("cooldown", (int) this.getEnchantedCooldown(itemstack));
		itemstack.setTagCompound(compound);

		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
	    }
	}
	return new ActionResult(EnumActionResult.FAIL, itemstack);
    }

    /**
     * Called from the client side whenever the gun successfully shoots
     */
    @SideOnly(Side.CLIENT)
    protected void spawnShootParticles(World worldIn, EntityPlayer playerIn, EnumHand handIn)
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

	worldIn.spawnParticle(EnumParticleTypes.FLAME, playerIn.posX + flameOffset.x, playerIn.posY + playerIn.getEyeHeight() + flameOffset.y, playerIn.posZ + flameOffset.z,
		0, 0, 0);

	for (int i = 0; i < SMOKE_PARTICLES; i++)
	{
	    float f = 0.1f;
	    worldIn.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, playerIn.posX + flameOffset.x + ModRandom.getFloat(f),
		    playerIn.posY + playerIn.getEyeHeight() + flameOffset.y + ModRandom.getFloat(f), playerIn.posZ + flameOffset.z + ModRandom.getFloat(f), 0, 0, 0);
	}
    }

    public ItemGun setInformation(Consumer<List<String>> information)
    {
	this.information = information;
	return this;
    }

    // List the required ammo for the gun
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	String ammoName = this.ammo == null ? ModUtils.translateDesc("no_ammo_required") : this.ammo.getItemStackDisplayName(new ItemStack(this.ammo));
	tooltip.add(ModUtils.getDisplayLevel(this.level));

	if (this.getEnchantedDamage(stack) > 0)
	{
	    this.getDamageTooltip(stack, worldIn, tooltip, flagIn);
	}

	tooltip.add(ModUtils.getCooldownTooltip(this.getEnchantedCooldown(stack)));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("gun_ammo") + ": " + TextFormatting.BLUE + ammoName);
	information.accept(tooltip);
    }

    protected void getDamageTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(ModUtils.getDamageTooltip(this.getEnchantedDamage(stack)));
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
