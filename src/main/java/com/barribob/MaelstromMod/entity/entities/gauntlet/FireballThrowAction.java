package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.util.IPitch;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class FireballThrowAction<T extends EntityLeveledMob & IPitch> implements IGauntletAction {

    private final Supplier<Vec3d> target;
    private final Supplier<Projectile> projectileSupplier;
    private final T entity;

    public FireballThrowAction(Supplier<Vec3d> target, Supplier<Projectile> projectileSupplier, T entity) {
        this.target = target;
        this.projectileSupplier = projectileSupplier;
        this.entity = entity;
    }

    @Override
    public void doAction() {
        ModBBAnimations.animation(entity, "gauntlet.fireball", false);
        Projectile proj = projectileSupplier.get();
        proj.setTravelRange(30);

        entity.addEvent(() -> entity.world.spawnEntity(proj), 10);

        // Hold the fireball in place
        for (int i = 10; i < 27; i++) {
            entity.addEvent(() -> {
                Vec3d fireballPos = entity.getPositionEyes(1).add(ModUtils.getAxisOffset(ModUtils.getLookVec(entity.getPitch(), entity.renderYawOffset), new Vec3d(1, 0, 0)));
                ModUtils.setEntityPosition(proj, fireballPos);
            }, i);
        }

        // Throw the fireball
        entity.addEvent(() -> {
            Vec3d vel = target.get().subtract(ModUtils.yVec(1)).subtract(proj.getPositionVector());
            proj.shoot(vel.x, vel.y, vel.z, 0.8f, 0.3f);
            ModUtils.addEntityVelocity(entity, vel.normalize().scale(-0.8));
        }, 27);
    }
}
