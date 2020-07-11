package com.barribob.MaelstromMod.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;

import javax.annotation.Nullable;

/**
 * Holds the custom damage sources that is the maelstrom damage type
 */
public class ModDamageSource {
    private static final String MAELSTROM = Reference.MOD_ID + ":" + "maelstrom";
    private static final String MOB_MAELSTROM = Reference.MOD_ID + ":" + "mobMaelstrom";
    private static final String PLAYER_MAELSTROM = Reference.MOD_ID + ":" + "playerMaelstrom";
    private static final String ARROW_MAELSTROM = Reference.MOD_ID + ":" + "arrowMaelstrom";
    private static final String THROWN_MAELSTROM = Reference.MOD_ID + ":" + "thrownMaelstrom";
    private static final String EXPLOSION_MAELSTROM = Reference.MOD_ID + ":" + "explosionMaelstrom";
    private static final String EXPLOSION_MAELSTROM_ENTITY = Reference.MOD_ID + ":" + "explosionMaelstrom.player";
    private static final String MAGIC_MAELSTROM = Reference.MOD_ID + ":" + "magicMaelstrom";

    public static final DamageSource MAELSTROM_DAMAGE = (new DamageSource(MAELSTROM));

    /**
     * Return whether a certain damage type is maelstrom damage
     *
     * @param source
     * @return
     */
    public static boolean isMaelstromDamage(DamageSource source) {
        return source.damageType == MOB_MAELSTROM || source.damageType == PLAYER_MAELSTROM || source.damageType == ARROW_MAELSTROM || source.damageType == THROWN_MAELSTROM
                || source.damageType == EXPLOSION_MAELSTROM || source.damageType == EXPLOSION_MAELSTROM_ENTITY || source.damageType == MAELSTROM;
    }

    public static DamageSource causeElementalMeleeDamage(EntityLivingBase mob, Element element) {
        return new EntityElementalDamageSource(MOB_MAELSTROM, mob, element);
    }

    public static DamageSource causeElementalPlayerDamage(EntityPlayer player, Element element) {
        return new EntityElementalDamageSource(PLAYER_MAELSTROM, player, element);
    }

    public static DamageSource causeElementalThrownDamage(Entity source, @Nullable Entity indirectEntityIn, Element element) {
        return (new EntityElementalDamageSourceIndirect(THROWN_MAELSTROM, source, indirectEntityIn, element)).setProjectile();
    }

    public static DamageSource causeElementalMagicDamage(Entity source, @Nullable Entity indirectEntityIn, Element element) {
        return (new EntityElementalDamageSourceIndirect(MAGIC_MAELSTROM, source, indirectEntityIn, element)).setDamageBypassesArmor();
    }

    public static DamageSource causeElementalExplosionDamage(@Nullable EntityLivingBase entityLivingBaseIn, Element element) {
        return entityLivingBaseIn != null ? (new EntityElementalDamageSource(EXPLOSION_MAELSTROM_ENTITY, entityLivingBaseIn, element)).setExplosion()
                : (new DamageSource(EXPLOSION_MAELSTROM)).setExplosion();
    }
}