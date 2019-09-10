package com.barribob.MaelstromMod.entity.action;

import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;

import net.minecraft.entity.EntityLivingBase;

public class ActionGroundSlash extends Action
{
    public final Supplier<Projectile> supplier;

    public ActionGroundSlash(Supplier<Projectile> p)
    {
	supplier = p;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	float inaccuracy = 0.0f;
	float speed = 0.8f;
	float pitch = 0; // Projectiles aim straight ahead always
	Projectile projectile = supplier.get();
	projectile.setPosition(actor.posX, actor.posY, actor.posZ);
	projectile.shoot(actor, pitch, actor.rotationYaw, 0.0F, speed, inaccuracy);
	projectile.setTravelRange(20f);
	actor.world.spawnEntity(projectile);
    }
}
