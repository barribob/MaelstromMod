package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityGoldenRune;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class ActionMultiGoldenRunes extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	spawnRune(actor, target, Vec3d.ZERO);

	ModUtils.circleCallback(4, 6, (pos) -> {
	    if (actor.world.rand.nextInt(2) == 0)
	    {
		spawnRune(actor, target, new Vec3d(pos.x, 0, pos.y));
	    }
	});
    }

    private void spawnRune(EntityLeveledMob actor, EntityLivingBase target, Vec3d offset)
    {
	float zeroish = 0.001f;
	EntityGoldenRune projectile = new EntityGoldenRune(actor.world, actor, actor.getAttack());
	projectile.setPosition(target.posX + offset.x, target.posY + offset.y, target.posZ + offset.z);
	projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
	projectile.setTravelRange(25);
	actor.world.spawnEntity(projectile);
    }
}
