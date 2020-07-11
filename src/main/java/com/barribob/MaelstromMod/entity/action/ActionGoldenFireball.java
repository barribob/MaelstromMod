package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenFireball;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;

public class ActionGoldenFireball extends Action {
    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        actor.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, 0.4F / (actor.world.rand.nextFloat() * 0.4F + 0.8F));

        float inaccuracy = 2.0f;
        float velocity = 0.5f;

        ProjectileGoldenFireball projectile = new ProjectileGoldenFireball(actor.world, actor, actor.getAttack(), null);
        double d0 = target.posY + (double) target.getEyeHeight() - 2;
        double xDir = target.posX - actor.posX;
        double yDir = d0 - projectile.posY;
        double zDir = target.posZ - actor.posZ;
        projectile.shoot(xDir, yDir, zDir, velocity, inaccuracy);
        projectile.setTravelRange(25);
        actor.world.spawnEntity(projectile);
    }
}
