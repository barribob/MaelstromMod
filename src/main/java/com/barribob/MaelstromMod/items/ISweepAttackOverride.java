package com.barribob.MaelstromMod.items;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import javax.annotation.Nullable;

/**
 * Allows tools that override this interface to replace the standard sword sweep attack with a custom one
 */
public interface ISweepAttackOverride {
    public void doSweepAttack(EntityPlayer player, @Nullable EntityLivingBase entity);
}
