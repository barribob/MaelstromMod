package com.barribob.MaelstromMod.entity.action;

import java.util.function.Supplier;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.MathHelper;

public class ActionThrust extends Action
{
    public static final float PROJECTILE_INACCURACY = 0;
    private float velocity;
    public final Supplier<Projectile> supplier;
    
    public ActionThrust(Supplier<Projectile> p, float velocity)
    {
	supplier = p;
	this.velocity = velocity;
    }

    public ActionThrust(Supplier<Projectile> p)
    {
	this(p, 0.5f);
    }
    
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	Projectile projectile = supplier.get();
	double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
	double xDir = target.posX - actor.posX;
	double yDir = d0 - projectile.posY;
	double zDir = target.posZ - actor.posZ;
	float f = MathHelper.sqrt(xDir * xDir + zDir * zDir) * 0.2F;
	yDir = Math.min(yDir + f, 0); // Keep the entity from aiming upward
	projectile.shoot(xDir, yDir, zDir, velocity, PROJECTILE_INACCURACY);
	actor.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (actor.getRNG().nextFloat() * 0.4F + 0.8F));
	actor.world.spawnEntity(projectile);
    }
}
