package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Random;

public class AIRandomFly extends EntityAIBase {
    private final EntityLiving parentEntity;

    public AIRandomFly(EntityLiving e) {
        this.parentEntity = e;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute() {
        EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();

        if (!entitymovehelper.isUpdating()) {
            return true;
        } else {
            double d0 = entitymovehelper.getX() - this.parentEntity.posX;
            double d1 = entitymovehelper.getY() - this.parentEntity.posY;
            double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            return d3 < 1.0D || d3 > 3600.0D;
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return false;
    }

    @Override
    public void startExecuting() {
        Random random = this.parentEntity.getRNG();
        double d0 = this.parentEntity.posX + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
        double d1 = this.parentEntity.posY + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
        double d2 = this.parentEntity.posZ + (random.nextFloat() * 2.0F - 1.0F) * 16.0F;
        this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
    }

    @Override
    public void updateTask() {
        Vec3d pos = ModUtils.getEntityVelocity(parentEntity).normalize().scale(5).add(parentEntity.getPositionVector());
        ModUtils.facePosition(pos, parentEntity, 10, 10);
        parentEntity.getLookHelper().setLookPosition(pos.x, pos.y, pos.z, 3, 3);
        super.updateTask();
    }
}
