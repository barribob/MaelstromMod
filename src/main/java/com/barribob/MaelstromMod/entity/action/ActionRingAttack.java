package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.function.Supplier;

public class ActionRingAttack implements IAction {
    Supplier<Projectile> projectileSupplier;

    public ActionRingAttack(Supplier<Projectile> projectileSupplier) {
        this.projectileSupplier = projectileSupplier;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        Vec3d direction = target.getPositionEyes(1).subtract(actor.getPositionVector());
        float f1 = MathHelper.sqrt(direction.x * direction.x + direction.z * direction.z);
        ModUtils.circleCallback(4, 12, (pos) -> {
            Vec3d rotatedPos = pos.rotatePitch((float) (MathHelper.atan2(direction.y, f1))).rotateYaw((float) (MathHelper.atan2(direction.x, direction.z)));
            Projectile projectile = projectileSupplier.get();
            ModUtils.setEntityPosition(projectile, rotatedPos.add(actor.getPositionVector()));
            actor.world.spawnEntity(projectile);
            actor.addEvent(() -> {
                ModUtils.throwProjectileNoSpawn(target.getPositionEyes(1), projectile, 0, 0.4f);
            }, 10);
        });
    }
}
