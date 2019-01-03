package com.barribob.MaelstromMod.util;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.Main;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;
import net.minecraft.world.Explosion;

/**
 * 
 * Holds the custom damage sources that is the maelstrom damage type
 *
 */
public class ModDamageSource
{
    private static final String MAELSTROM = Reference.MOD_ID + ":" + "maelstrom";
    private static final String MOB_MAELSTROM = Reference.MOD_ID + ":" + "mobMalestrom";
    private static final String PLAYER_MAELSTROM = Reference.MOD_ID + ":" + "playerMalestrom";
    private static final String ARROW_MAELSTROM = Reference.MOD_ID + ":" + "arrowMalestrom";
    private static final String THROWN_MAELSTROM = Reference.MOD_ID + ":" + "thrownMalestrom";
    private static final String EXPLOSION_MAELSTROM = Reference.MOD_ID + ":" + "explosionMaelstrom";

    public static final DamageSource MAELSTROM_DAMAGE = (new DamageSource(MAELSTROM)).setDamageBypassesArmor().setDamageIsAbsolute();

    /**
     * Return whether a certain damage type is maelstrom damage
     * 
     * @param source
     * @return
     */
    public static boolean isMaelstromDamage(DamageSource source)
    {
	return source.damageType == MOB_MAELSTROM || source.damageType == PLAYER_MAELSTROM || source.damageType == ARROW_MAELSTROM
		|| source.damageType == THROWN_MAELSTROM || source.damageType == EXPLOSION_MAELSTROM || source.damageType == MAELSTROM;
    }

    /**
     * The standard mob damage, except with armor bypassing maelstrom armor instead
     */
    public static DamageSource causeMaelstromMeleeDamage(EntityLivingBase mob)
    {
	return new EntityDamageSource(MOB_MAELSTROM, mob).setDamageBypassesArmor().setDamageIsAbsolute();
    }

    /**
     * The standard player damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeMalestromPlayerDamage(EntityPlayer player)
    {
	return new EntityDamageSource(PLAYER_MAELSTROM, player).setDamageBypassesArmor().setDamageIsAbsolute();
    }

    /**
     * The standard arrow damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeMaelstromArrowDamage(EntityArrow arrow, @Nullable Entity indirectEntityIn)
    {
	return (new EntityDamageSourceIndirect(ARROW_MAELSTROM, arrow, indirectEntityIn)).setDamageBypassesArmor().setDamageIsAbsolute()
		.setProjectile();
    }

    /**
     * The standard thrown damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeMalestromThrownDamage(Entity source, @Nullable Entity indirectEntityIn)
    {
	return (new EntityDamageSourceIndirect(THROWN_MAELSTROM, source, indirectEntityIn)).setDamageBypassesArmor().setDamageIsAbsolute()
		.setProjectile();
    }

    /**
     * The standard explosion damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeMaelstromExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn)
    {
	return entityLivingBaseIn != null ? (new EntityDamageSource(EXPLOSION_MAELSTROM + ".player", entityLivingBaseIn)).setDamageBypassesArmor()
		.setDamageIsAbsolute().setExplosion()
		: (new DamageSource(EXPLOSION_MAELSTROM)).setDamageBypassesArmor().setDamageIsAbsolute().setExplosion();
    }
}
