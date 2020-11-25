package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * The projectile for the maelstrom cannon item
 */
public class ProjectileMaelstromCannon extends ProjectileGun {
    private static final int PARTICLE_AMOUNT = 1;
    private static final int IMPACT_PARTICLE_AMOUNT = 20;
    private static final int EXPOSION_AREA_FACTOR = 2;

    public ProjectileMaelstromCannon(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectileMaelstromCannon(World worldIn) {
        super(worldIn);
    }

    public ProjectileMaelstromCannon(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    /**
     * Called every update to spawn particles
     *
     * @param world
     */
    @Override
    protected void spawnParticles() {
        for (int i = 0; i < this.PARTICLE_AMOUNT; i++) {
            ParticleManager.spawnMaelstromSmoke(world, rand, new Vec3d(this.posX, this.posY, this.posZ), true);
        }
    }

    @Override
    protected void spawnImpactParticles() {
        for (int i = 0; i < this.IMPACT_PARTICLE_AMOUNT; i++) {
            Vec3d vec1 = ModRandom.randVec().scale(EXPOSION_AREA_FACTOR * 0.25).add(getPositionVector());
            ParticleManager.spawnMaelstromExplosion(world, rand, vec1);
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
        float knockbackFactor = 1 + this.getKnockback() * 0.4f;
        int fireFactor = this.isBurning() ? 5 : 0;
        ModUtils.handleAreaImpact(EXPOSION_AREA_FACTOR, (e) -> this.getGunDamage(e), this.shootingEntity, this.getPositionVector(),
                ModDamageSource.causeElementalExplosionDamage(shootingEntity, getElement()), knockbackFactor, fireFactor);
        this.playSound(SoundEvents.ENTITY_GENERIC_EXPLODE, 1.0F, 1.0F / (rand.nextFloat() * 0.4F + 0.8F));

        super.onHit(result);
    }
}
