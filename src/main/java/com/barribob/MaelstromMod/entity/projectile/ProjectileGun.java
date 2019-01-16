package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.init.ModEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * 
 * A helper class to record enchantments from the gun stack
 *
 */
public class ProjectileGun extends Projectile
{
    private int knockbackStrength;
    private float damage;

    public ProjectileGun(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn);

	int power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_power, stack);
	int knockback = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.impact, stack);
	if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_flame, stack) > 0)
	{
	    this.setFire(100);
	}

	this.damage = (float) (baseDamage * (1 + power * 0.125f));
	this.knockbackStrength = knockback;
    }
    
    protected int getKnockback()
    {
	return this.knockbackStrength;
    }
    
    protected float getDamage()
    {
	return this.damage;
    }

    public ProjectileGun(World worldIn)
    {
	super(worldIn);
    }

    public ProjectileGun(World worldIn, double x, double y, double z)
    {
	super(worldIn, x, y, z);
    }
}
