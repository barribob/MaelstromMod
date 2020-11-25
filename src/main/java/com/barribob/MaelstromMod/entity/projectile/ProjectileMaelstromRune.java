package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileMaelstromRune extends EntityLargeGoldenRune {
    public ProjectileMaelstromRune(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
    }

    public ProjectileMaelstromRune(World worldIn) {
        super(worldIn);
    }

    public ProjectileMaelstromRune(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnImpactParticles() {
        ModUtils.performNTimes(10, (i) -> {
            ModUtils.circleCallback(blastRadius, 30,
                    (offset) -> ParticleManager.spawnWisp(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, i * 0.5, offset.y)), ModColors.PURPLE, Vec3d.ZERO));
            ModUtils.circleCallback(blastRadius - 1, 30,
                    (offset) -> ParticleManager.spawnWisp(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, i * 0.5, offset.y)), ModColors.PURPLE, Vec3d.ZERO));
        });
    }

    @Override
    protected void spawnParticles() {
        if (this.ticksExisted % 5 == 0) {
            ModUtils.circleCallback(this.blastRadius, 45,
                    (offset) -> ParticleManager.spawnSwirl(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, 0.5f, offset.y)), ModColors.PURPLE, ModUtils.getEntityVelocity(this), 5));
            ModUtils.circleCallback(this.blastRadius - 1, 30,
                    (offset) -> ParticleManager.spawnSwirl(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, 0.6f, offset.y)), ModColors.PURPLE, ModUtils.getEntityVelocity(this), 5));
            ModUtils.circleCallback(this.blastRadius - 2, 30,
                    (offset) -> ParticleManager.spawnSwirl(world, ModUtils.entityPos(this).add(new Vec3d(offset.x, 0.7f, offset.y)), ModColors.PURPLE, ModUtils.getEntityVelocity(this), 5));
        }
    }

    @Override
    protected void blastEffect(EntityLivingBase e) {
        e.addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 40, 0));
    }
}
