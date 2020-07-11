package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

import java.util.function.Supplier;

public class ActionSpawnEnemy extends Action {
    Supplier<EntityLeveledMob> mobSupplier;

    public ActionSpawnEnemy(Supplier<EntityLeveledMob> mobSupplier) {
        this.mobSupplier = mobSupplier;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        EntityLeveledMob mob = mobSupplier.get();
        int tries = 100;
        for (int i = 0; i < tries; i++) {
            // Find a random position to spawn the enemy
            int i1 = (int) actor.posX + MathHelper.getInt(actor.world.rand, 2, 8) * ModRandom.randSign();
            int k1 = (int) actor.posZ + MathHelper.getInt(actor.world.rand, 2, 8) * ModRandom.randSign();

            int y = 2;
            while (y > -3) {
                if (!actor.world.isAirBlock(new BlockPos(i1, actor.posY + y - 1, k1))) {
                    break;
                }
                y--;
            }

            int j1 = (int) actor.posY + y;

            if (actor.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isSideSolid(actor.world, new BlockPos(i1, j1 - 1, k1), net.minecraft.util.EnumFacing.UP)) {
                mob.setPosition(i1, j1, k1);

                // Make sure that the position is a proper spawning position
                if (!actor.world.isAnyPlayerWithinRangeAt(i1, j1, k1, 3.0D)
                        && actor.world.getCollisionBoxes(mob, mob.getEntityBoundingBox()).isEmpty() && !actor.world.containsAnyLiquid(mob.getEntityBoundingBox())) {
                    // Spawn the entity
                    actor.world.spawnEntity(mob);
                    if (actor.getAttackTarget() != null) {
                        mob.setAttackTarget(actor.getAttackTarget());
                    }
                    mob.onInitialSpawn(actor.world.getDifficultyForLocation(new BlockPos(mob)), (IEntityLivingData) null);
                    mob.spawnExplosionParticle();
                    mob.setLevel(actor.getLevel());
                    return;
                }
            }
        }
    }

}
