package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromCannon;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * A simple medium range weapon
 *
 */
public class ItemMaelstromCannon extends ItemGun
{
    public ItemMaelstromCannon(String name, int cooldown, int maxDamage, CreativeTabs tab)
    {
	super(name, cooldown, maxDamage, null, tab);
    }

    /**
     * Shoot a single projectile
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
		0.6F / (itemRand.nextFloat() * 0.4F + 0.8F));

	int power = EnchantmentHelper.getEnchantmentLevel(Enchantments.POWER, stack);
	int knockback = EnchantmentHelper.getEnchantmentLevel(Enchantments.PUNCH, stack);
	float velocity = 1.0F;
	float inaccuracy = 3.0f;
	float degreesUp = 20;

	ProjectileMaelstromCannon projectile = new ProjectileMaelstromCannon(world, player, 4.0f, stack);
	projectile.shoot(player, player.rotationPitch - degreesUp, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(25f);
	world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + "Required Ammo: None");
	tooltip.add(TextFormatting.GRAY + "Close range explosive attack");
    }
}