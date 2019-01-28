package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.config.ModConfig;
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
    private int maelstromDestroyer;

    public ProjectileGun(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage);

	int power = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_power, stack);
	int knockback = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.impact, stack);
	int maelstromDestroyer = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.maelstrom_destroyer, stack);
	if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_flame, stack) > 0)
	{
	    this.setFire(100);
	}

	float maxPower = ModConfig.progression_scale / ModEnchantments.gun_power.getMaxLevel();
	this.setDamage((float) (baseDamage * (1 + power * maxPower)));
	this.knockbackStrength = knockback;
	this.maelstromDestroyer = maelstromDestroyer;
    }
    
    protected int getKnockback()
    {
	return this.knockbackStrength;
    }
    
    protected float getGunDamage(Entity entity)
    {
	if(entity instanceof EntityMaelstromMob)
	{
	    float maxPower = ModConfig.progression_scale / ModEnchantments.maelstrom_destroyer.getMaxLevel();
	    return super.getDamage() * (1 + this.maelstromDestroyer * maxPower);
	}
	
	return super.getDamage();
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
