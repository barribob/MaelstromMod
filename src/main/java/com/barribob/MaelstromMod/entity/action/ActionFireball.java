package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileFireball;
import com.barribob.MaelstromMod.entity.projectile.ProjectileShadeAttack;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class ActionFireball extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 0.4F / (actor.world.rand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 2.0f;
	float velocity = 0.5f;

	ProjectileFireball projectile = new ProjectileFireball(actor.world, actor, actor.getAttack(), null);
	double d0 = target.posY + (double) target.getEyeHeight() - 2;
	double xDir = target.posX - actor.posX;
	double yDir = d0 - projectile.posY;
	double zDir = target.posZ - actor.posZ;
	float f = MathHelper.sqrt(xDir * xDir + zDir * zDir) * 0.2F;
	projectile.shoot(xDir, yDir, zDir, velocity, inaccuracy);
	actor.playSound(SoundEvents.BLOCK_ANVIL_BREAK, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
	projectile.setTravelRange(25);
	actor.world.spawnEntity(projectile);
    }
}
