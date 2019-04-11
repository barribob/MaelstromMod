package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectilePumpkin;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemPumpkin extends ItemGun
{
    public ItemPumpkin(String name, int cooldown, float useTime, Item ammo, float level, CreativeTabs tab)
    {
	super(name, cooldown, 0.5f, useTime, ammo, level, tab);
    }

    /**
     * Shoot a single bullet
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 0.0f;
	float velocity = 6.0f;

	ProjectilePumpkin projectile = new ProjectilePumpkin(world, player, this.getEnchantedDamage(stack), stack);
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(1000f);

	world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "An accurate energy sniper rifle.");
	tooltip.add(TextFormatting.GRAY + "Deals more damage the farther it travels.");
    }
    
    protected void getDamageTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + "Deals " + TextFormatting.BLUE + Math.round(this.getEnchantedDamage(stack) * 100) * 0.01f + TextFormatting.GRAY + " damage per meter.");
    }
}
