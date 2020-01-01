package com.barribob.MaelstromMod.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.EntityDamageSourceIndirect;

/**
 * 
 * Holds the custom damage sources that is the maelstrom damage type
 *
 */
public class ModDamageSource
{
    private static final String MAELSTROM = Reference.MOD_ID + ":" + "maelstrom";
    private static final String MOB_MAELSTROM = Reference.MOD_ID + ":" + "mobMaelstrom";
    private static final String PLAYER_MAELSTROM = Reference.MOD_ID + ":" + "playerMaelstrom";
    private static final String ARROW_MAELSTROM = Reference.MOD_ID + ":" + "arrowMaelstrom";
    private static final String THROWN_MAELSTROM = Reference.MOD_ID + ":" + "thrownMaelstrom";
    private static final String EXPLOSION_MAELSTROM = Reference.MOD_ID + ":" + "explosionMaelstrom";
    private static final String EXPLOSION_MAELSTROM_ENTITY = Reference.MOD_ID + ":" + "explosionMaelstrom.player";

    public static final DamageSource MAELSTROM_DAMAGE = (new DamageSource(MAELSTROM));

    /**
     * Return whether a certain damage type is maelstrom damage
     * 
     * @param source
     * @return
     */
    public static boolean isMaelstromDamage(DamageSource source)
    {
	return source.damageType == MOB_MAELSTROM || source.damageType == PLAYER_MAELSTROM || source.damageType == ARROW_MAELSTROM || source.damageType == THROWN_MAELSTROM
		|| source.damageType == EXPLOSION_MAELSTROM || source.damageType == EXPLOSION_MAELSTROM_ENTITY || source.damageType == MAELSTROM;
    }

    /**
     * The standard mob damage, except with armor bypassing maelstrom armor instead
     */
    public static DamageSource causeMaelstromMeleeDamage(EntityLivingBase mob)
    {
	return new EntityDamageSource(MOB_MAELSTROM, mob);
    }

    /**
     * The standard player damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeElementalPlayerDamage(EntityPlayer player, Element element)
    {
	return new EntityElementalDamageSource(PLAYER_MAELSTROM, player, element);
    }

    /**
     * The standard thrown damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeMaelstromThrownDamage(Entity source, @Nullable Entity indirectEntityIn)
    {
	return (new EntityDamageSourceIndirect(THROWN_MAELSTROM, source, indirectEntityIn)).setProjectile();
    }

    /**
     * The standard explosion damage, except with armor bypassing maelstrom armor
     * instead
     */
    public static DamageSource causeMaelstromExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn)
    {
	return entityLivingBaseIn != null ? (new EntityDamageSource(EXPLOSION_MAELSTROM_ENTITY, entityLivingBaseIn)).setExplosion()
		: (new DamageSource(EXPLOSION_MAELSTROM)).setExplosion();
    }
}