package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public class ActionGolemSlam extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	Vec3d offset = actor.getPositionVector().add(ModUtils.getRelativeOffset(actor, new Vec3d(2, 1, 0)));
	ModUtils.handleAreaImpact(2, (e) -> actor.getAttack(), actor, offset, ModDamageSource.causeElementalMeleeDamage(actor, actor.getElement()), 0.5f, 0, true);

	float inaccuracy = 0.0f;
	float speed = 0.5f;
	float pitch = 0; // Projectiles aim straight ahead always

	// Shoots projectiles in a small arc
	for (int i = 0; i < 5; i++)
	{
	    ProjectileQuake projectile = new ProjectileQuake(actor.world, actor, actor.getAttack(), (ItemStack) null);
	    projectile.shoot(actor, pitch, actor.rotationYaw - 20 + (i * 10), 0.0F, speed, inaccuracy);
	    projectile.setTravelRange(8f);
	    actor.world.spawnEntity(projectile);
	}
    }
}
