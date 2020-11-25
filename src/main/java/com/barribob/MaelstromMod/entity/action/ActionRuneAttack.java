package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.util.IEntityAdjustment;
import net.minecraft.entity.EntityLivingBase;

import java.util.function.Supplier;

public class ActionRuneAttack implements IAction {
    Supplier<Projectile> projectileSupplier;
    IEntityAdjustment adjustment;
    private static final float zeroish = 0.001f;

    public ActionRuneAttack(Supplier<Projectile> projectileSupplier, IEntityAdjustment adjustment) {
        this.projectileSupplier = projectileSupplier;
        this.adjustment = adjustment;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        Projectile projectile = projectileSupplier.get();
        projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
        adjustment.adjust(projectile);
        projectile.setTravelRange(50);
        actor.world.spawnEntity(projectile);
    }
}
