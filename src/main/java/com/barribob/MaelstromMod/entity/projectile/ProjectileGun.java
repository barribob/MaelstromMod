package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.init.ModEnchantments;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * A helper class to record enchantments from the gun stack
 *
 */
public class ProjectileGun extends Projectile
{
    private int knockbackStrength;
    private int maelstromDestroyer;
    private int criticalHit;
    private boolean isCritical;
    private static final byte CRITICAL_BYTE = 5;

    public ProjectileGun(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack)
    {
	super(worldIn, throwerIn, baseDamage);

	if (stack != null)
	{
	    this.knockbackStrength = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.impact, stack);
	    this.maelstromDestroyer = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.maelstrom_destroyer, stack);
	    this.criticalHit = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.critical_hit, stack);
	    if (rand.nextInt(10) == 0 && this.criticalHit > 0 && !world.isRemote)
	    {
		this.isCritical = true;
		this.setDamage(this.getDamage() * this.criticalHit * 2.5f);
	    }
	    if (EnchantmentHelper.getEnchantmentLevel(ModEnchantments.gun_flame, stack) > 0)
	    {
		this.setFire(100);
	    }
	}
    }

    protected int getKnockback()
    {
	return this.knockbackStrength;
    }

    protected float getGunDamage(Entity entity)
    {
	if (entity instanceof EntityMaelstromMob)
	{
	    float maxPower = ModConfig.progression_scale / ModEnchantments.maelstrom_destroyer.getMaxLevel();
	    return super.getDamage() * (1 + this.maelstromDestroyer * maxPower);
	}

	return super.getDamage();
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	if (this.isCritical)
	{
	    world.setEntityState(this, this.CRITICAL_BYTE);
	}
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == this.CRITICAL_BYTE)
	{
	    world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0, 0, 0);
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
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
