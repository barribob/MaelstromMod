package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityOctoMissileLauncher;
import net.minecraft.entity.EntityLivingBase;

public class ActionOctoMissiles extends Action {
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        float zeroish = 0.001f;

        EntityOctoMissileLauncher projectile = new EntityOctoMissileLauncher(actor.world, actor, actor.getAttack(), target);

        projectile.setPosition(actor.posX, actor.posY, actor.posZ);
        projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
        projectile.setTravelRange(5);
        actor.world.spawnEntity(projectile);
    }
}
