package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityGoldenRune;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class ActionGoldenRunes extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	float zeroish = 0.001f;

	EntityGoldenRune projectile = new EntityGoldenRune(actor.world, actor, actor.getAttack());

	Vec3d offset = Vec3d.ZERO;
	// Randomly try to predict where the target will be and offset to there
	if (ModRandom.range(0, 2) == 0)
	{
	    offset = new Vec3d(target.motionX, target.motionY, target.motionZ).scale(10);
	    if (target instanceof EntityPlayer)
	    {
		offset = new Vec3d(target.getLookVec().x, 0, target.getLookVec().z).normalize().scale(target.getAIMoveSpeed() * 50);
	    }
	}

	projectile.setPosition(target.posX + offset.x, target.posY, target.posZ + offset.z);
	projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
	projectile.setTravelRange(25);
	projectile.setElement(Element.GOLDEN);
	actor.world.spawnEntity(projectile);
    }
}
