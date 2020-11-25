package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class ActionRayAttack implements IAction {
    Supplier<Projectile> projectileSupplier;
    float velocity;

    public ActionRayAttack(Supplier<Projectile> projectileSupplier, float velocity) {
        this.projectileSupplier = projectileSupplier;
        this.velocity = velocity;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        Vec3d targetPos = target.getPositionEyes(1);
        Vec3d fromTargetToActor = actor.getPositionVector().subtract(targetPos);

        Vec3d lineDirection = ModUtils.rotateVector2(
                fromTargetToActor.crossProduct(ModUtils.Y_AXIS),
                fromTargetToActor,
                ModRandom.range(0, 180))
                .normalize()
                .scale(6);

        Vec3d lineStart = targetPos.subtract(lineDirection);
        Vec3d lineEnd = targetPos.add(lineDirection);

        ModUtils.lineCallback(lineStart, lineEnd, 10, (pos, i) -> {
            Projectile projectile = projectileSupplier.get();
            projectile.setTravelRange(30);
            projectile.setNoGravity(true);

            ModUtils.throwProjectile(actor, pos, projectile, 0, velocity);
        });
    }
}
