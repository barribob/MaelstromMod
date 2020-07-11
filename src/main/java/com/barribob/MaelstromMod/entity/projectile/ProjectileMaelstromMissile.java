package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileMaelstromMissile extends Projectile {
    public ProjectileMaelstromMissile(World worldIn, EntityLivingBase throwerIn, float damage) {
        super(worldIn, throwerIn, damage);
        this.setNoGravity(true);
    }

    public ProjectileMaelstromMissile(World worldIn) {
        super(worldIn);
    }

    public ProjectileMaelstromMissile(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        ParticleManager.spawnDarkFlames(world, this.rand, this.getPositionVector());
    }

    @Override
    protected void onHit(RayTraceResult result) {
        if (result.entityHit != null && !(result.entityHit instanceof EntityMaelstromMob) && this.shootingEntity != null) {
            if (this.shootingEntity instanceof EntityLivingBase) {
                result.entityHit.attackEntityFrom(ModDamageSource.causeElementalThrownDamage(this, shootingEntity, getElement()), this.getDamage());
            }
        }
        this.playSound(SoundEvents.BLOCK_FIRE_EXTINGUISH, 1.0f + ModRandom.getFloat(0.2f), 1.0f + ModRandom.getFloat(0.2f));
        super.onHit(result);
    }
}
