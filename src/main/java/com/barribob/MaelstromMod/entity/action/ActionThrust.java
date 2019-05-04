package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class ActionThrust extends Action
{
    public static final float PROJECTILE_INACCURACY = 0;
    public static final float PROJECTILE_VELOCITY = 0.5f;
    
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	ProjectileShadeAttack projectile = new ProjectileShadeAttack(actor.world, actor, actor.getAttack());
	double d0 = target.posY + (double) target.getEyeHeight() - 1.100000023841858D;
	double xDir = target.posX - actor.posX;
	double yDir = d0 - projectile.posY;
	double zDir = target.posZ - actor.posZ;
	float f = MathHelper.sqrt(xDir * xDir + zDir * zDir) * 0.2F;
	yDir = Math.min(yDir + f, 0); // Keep the entity from aiming upward
	projectile.shoot(xDir, yDir, zDir, PROJECTILE_VELOCITY, PROJECTILE_INACCURACY);
	actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
	actor.world.spawnEntity(projectile);
    }
}
