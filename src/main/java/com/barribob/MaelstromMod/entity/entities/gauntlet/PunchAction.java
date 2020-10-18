package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class PunchAction implements IGauntletAction {

    private final String animation;
    private final Supplier<Vec3d> targetSupplier;
    private final Runnable whilePunching;
    private final EntityLeveledMob entity;
    private final Entity fistHitbox;
    private boolean isPunching;

    public PunchAction (String animation, Supplier<Vec3d> targetSupplier, Runnable whilePunching, EntityLeveledMob entity, Entity fistHitbox) {
        this.animation = animation;
        this.targetSupplier = targetSupplier;
        this.whilePunching = whilePunching;
        this.entity = entity;
        this.fistHitbox = fistHitbox;
    }

    @Override
    public void doAction() {
        ModBBAnimations.animation(entity, animation, false);
        entity.addVelocity(0, 0.5, 0);
        entity.addEvent(() -> {
            Vec3d target = targetSupplier.get();
            if (target == null) return;
            isPunching = true;
            fistHitbox.width = 2.5f;
            fistHitbox.height = 4.5f;
            entity.height = 2;
            for (int i = 0; i < 10; i++) {
                entity.addEvent(() -> {
                    Vec3d dir = target.subtract(entity.getPositionVector()).normalize().scale(0.32);
                    ModUtils.addEntityVelocity(entity, dir);
                }, i);
            }
        }, 16);
        entity.addEvent(() -> isPunching = false, 40);
        entity.addEvent(() -> {
            fistHitbox.width = 0;
            fistHitbox.height = 0;
            entity.height = 4;
        }, 50);
    }

    @Override
    public void update() {
        if (this.isPunching) {
            double vel = ModUtils.getEntityVelocity(entity).lengthVector();
            AxisAlignedBB box = entity.getEntityBoundingBox().grow(0.3, 0.3, 0.3);
            ModUtils.destroyBlocksInAABB(box, entity.world, entity);

            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(entity)
                    .stoppedByArmorNotShields()
                    .element(entity.getElement()).build();

            ModUtils.handleAreaImpact(1.3f, (e) -> entity.getAttack() * (float) vel, entity,
                    entity.getPositionEyes(1), source, (float) vel, 0, false);

            whilePunching.run();
        }
    }

    @Override
    public boolean shouldExplodeUponImpact() {
        return isPunching;
    }

    @Override
    public boolean isImmuneToDamage() {
        return isPunching;
    }
}
