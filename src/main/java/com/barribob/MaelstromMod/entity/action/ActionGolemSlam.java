package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

public class ActionGolemSlam extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	float inaccuracy = 0.0f;
	float speed = 2f;
	float pitch = 0; // Projectiles aim straight ahead always

	// Shoots projectiles in a small arc
	for (int i = 0; i < 5; i++)
	{
	    ProjectileQuake projectile = new ProjectileQuake(actor.world, actor, actor.getAttack(), (ItemStack) null);
	    projectile.setPosition(actor.posX, actor.posY, actor.posZ);
	    projectile.shoot(actor, pitch, actor.rotationYaw - 20 + (i * 10), 0.0F, speed, inaccuracy);
	    projectile.setTravelRange(8f);
	    actor.world.spawnEntity(projectile);
	}
    }
}
