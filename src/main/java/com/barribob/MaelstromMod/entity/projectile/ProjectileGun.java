package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.init.ModEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
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
    private int maelstromDestroyer;

    public ProjectileGun(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn);

	int power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_power, stack);
	int knockback = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.impact, stack);
	int maelstromDestroyer = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.maelstrom_destroyer, stack);
	if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_flame, stack) > 0)
	{
	    this.setFire(100);
	}

	this.damage = (float) (baseDamage * (1 + power * 0.125f));
	this.knockbackStrength = knockback;
	this.maelstromDestroyer = maelstromDestroyer;
    }
    
    protected int getKnockback()
    {
	return this.knockbackStrength;
    }
    
    protected float getDamage(Entity entity)
    {
	if(entity instanceof EntityMaelstromMob)
	{
	    return this.damage * (1 + this.maelstromDestroyer * 0.2f);
	}
	
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
