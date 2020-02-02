package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectilePillarFlames;
import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * Spawn fireballs in a burst of random directions
 *
 */
public class ActionFireballBurst extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	int numProjectiles = 30;
	float velocity = 0.3f;
	for (int i = 0; i < numProjectiles; i++)
	{
	    ProjectilePillarFlames projectile = new ProjectilePillarFlames(actor.world, actor, actor.getAttack());
	    projectile.setPosition(projectile.posX, actor.posY + (actor.getEyeHeight() * 0.5f), projectile.posZ);
	    double xDir = ModRandom.getFloat(1);
	    double yDir = ModRandom.getFloat(0.5f);
	    double zDir = ModRandom.getFloat(1);
	    float f = MathHelper.sqrt(xDir * xDir + zDir * zDir) * 0.2F;
	    projectile.shoot(xDir, yDir, zDir, velocity, 0);
	    projectile.setTravelRange(10);
	    actor.world.spawnEntity(projectile);
	}

	// Spawn one aimed at the player
	ProjectilePillarFlames projectile = new ProjectilePillarFlames(actor.world, actor, actor.getAttack());
	projectile.setPosition(projectile.posX, actor.posY + (actor.getEyeHeight() * 0.5f), projectile.posZ);
	double d0 = target.posY + target.getEyeHeight() - 1;
	double xDir = target.posX - actor.posX;
	double yDir = d0 - projectile.posY;
	double zDir = target.posZ - actor.posZ;
	float f = MathHelper.sqrt(xDir * xDir + zDir * zDir) * 0.2F;
	projectile.shoot(xDir, yDir, zDir, velocity, 0);
	projectile.setTravelRange(10);
	actor.world.spawnEntity(projectile);

	actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 1.0F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
    }
}
