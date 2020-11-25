package com.barribob.MaelstromMod.items;

import net.minecraft.util.math.Vec3d;

/**
 * Adds additional sweep attack particle to items with sweep attacks Works in
 * conjunction with ParticleSpawnerSwordSwing
 *
 * @see com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing
 */
public interface ISweepAttackParticles {
    public Vec3d getColor();

    public float getSize();
}
