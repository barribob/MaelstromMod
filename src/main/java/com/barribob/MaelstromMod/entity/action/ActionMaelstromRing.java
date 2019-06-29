package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHerobrineQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromQuake;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;

public class ActionMaelstromRing extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	ParticleManager.spawnParticlesInCircle(1, 24, (dir) -> {
	    float inaccuracy = 0.0f;
	    float speed = 0.5f;

	    ProjectileMaelstromQuake projectile = new ProjectileMaelstromQuake(actor.world, actor, actor.getAttack());
	    projectile.setPosition(actor.posX, actor.posY, actor.posZ);
	    projectile.shoot(dir.x, 0, dir.y, speed, inaccuracy);
	    projectile.setTravelRange(30f);
	    actor.world.spawnEntity(projectile);
	});
    }
}
