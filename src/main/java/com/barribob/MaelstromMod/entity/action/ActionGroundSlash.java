package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHerobrineQuake;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;

import net.minecraft.entity.EntityLivingBase;

public class ActionGroundSlash extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	float inaccuracy = 0.0f;
	float speed = 0.7f;
	float pitch = 0; // Projectiles aim straight ahead always
	ProjectileHerobrineQuake projectile = new ProjectileHerobrineQuake(actor.world, actor, actor.getAttack());
	projectile.setPosition(actor.posX, actor.posY, actor.posZ);
	projectile.shoot(actor, pitch, actor.rotationYaw, 0.0F, speed, inaccuracy);
	projectile.setTravelRange(15f);
	actor.world.spawnEntity(projectile);
    }
}
