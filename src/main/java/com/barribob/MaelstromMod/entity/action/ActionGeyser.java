package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.EntityGeyser;
import net.minecraft.entity.EntityLivingBase;

public class ActionGeyser extends Action {
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        float zeroish = 0.001f;
        EntityGeyser projectile = new EntityGeyser(actor.world, actor, actor.getAttack());
        projectile.setPosition(target.posX, target.posY, target.posZ);
        projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
        projectile.setTravelRange(25);
        actor.world.spawnEntity(projectile);
    }
}
