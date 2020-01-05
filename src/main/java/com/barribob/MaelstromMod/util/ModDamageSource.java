package com.barribob.MaelstromMod.util;

import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;

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

    public static DamageSource causeMaelstromMeleeDamage(EntityLivingBase mob)
    {
	return new EntityDamageSource(MOB_MAELSTROM, mob);
    }

    public static DamageSource causeElementalMeleeDamage(EntityLivingBase mob, Element element)
    {
	return new EntityElementalDamageSource(MOB_MAELSTROM, mob, element);
    }

    public static DamageSource causeElementalPlayerDamage(EntityPlayer player, Element element)
    {
	return new EntityElementalDamageSource(PLAYER_MAELSTROM, player, element);
    }

    public static DamageSource causeMaelstromThrownDamage(Entity source, @Nullable Entity indirectEntityIn)
    {
	return causeMaelstromThrownDamage(source, indirectEntityIn, Element.NONE);
    }

    public static DamageSource causeMaelstromThrownDamage(Entity source, @Nullable Entity indirectEntityIn, Element element)
    {
	return (new EntityElementalDamageSourceIndirect(THROWN_MAELSTROM, source, indirectEntityIn, element)).setProjectile();
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

    public static DamageSource causeElementalExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn, Element element)
    {
	return entityLivingBaseIn != null ? (new EntityElementalDamageSource(EXPLOSION_MAELSTROM_ENTITY, entityLivingBaseIn, element)).setExplosion()
		: (new DamageSource(EXPLOSION_MAELSTROM)).setExplosion();
    }
}