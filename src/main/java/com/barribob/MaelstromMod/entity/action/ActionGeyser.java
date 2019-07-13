package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityGeyser;
import com.barribob.MaelstromMod.entity.projectile.EntityGoldenRune;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;

public class ActionGeyser extends Action
{
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target)
    {
	float zeroish = 0.001f;
	EntityGeyser projectile = new EntityGeyser(actor.world, actor, actor.getAttack());
	projectile.setPosition(target.posX, target.posY, target.posZ);
	projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
	projectile.setTravelRange(25);
	actor.world.spawnEntity(projectile);
    }
}
