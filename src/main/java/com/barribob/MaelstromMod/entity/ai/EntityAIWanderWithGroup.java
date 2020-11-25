package com.barribob.MaelstromMod.entity.ai;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

public class EntityAIWanderWithGroup extends EntityAIWander {
    public EntityAIWanderWithGroup(EntityCreature entity, double speed) {
        super(entity, speed, 20);
    }

    @Override
    @Nullable
    protected Vec3d getPosition() {
        if (this.entity.isInWater()) {
            Vec3d vec3d = RandomPositionGenerator.getLandPos(this.entity, 15, 7);
            return vec3d == null ? super.getPosition() : vec3d;
        } else {
            Vec3d groupCenter = ModUtils.findEntityGroupCenter(this.entity, 20);

            for (int i = 0; i < 10; i++) {
                Vec3d pos = RandomPositionGenerator.findRandomTargetBlockTowards(this.entity, 10, 7, groupCenter);
                if (pos != null) {
                    return pos;
                }
            }

            return RandomPositionGenerator.getLandPos(this.entity, 10, 7);
        }
    }
}