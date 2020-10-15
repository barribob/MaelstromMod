package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class ProjectileAbstractMegaFireball extends ProjectileGun {
    private boolean canTakeDamage;

    public ProjectileAbstractMegaFireball(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack, boolean canTakeDamage) {
        super(worldIn, throwerIn, baseDamage, stack);
        this.setNoGravity(true);
        this.setSize(1, 1);
        this.canTakeDamage = canTakeDamage;
    }

    public ProjectileAbstractMegaFireball(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
        this.setSize(1, 1);
    }

    public ProjectileAbstractMegaFireball(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
        this.setSize(1, 1);
    }

    @Override
    protected final void onHit(RayTraceResult result) {
        boolean isShootingEntity = result != null && result.entityHit != null && result.entityHit == this.shootingEntity;
        boolean isPartOfShootingEntity = result != null && result.entityHit != null && (result.entityHit instanceof MultiPartEntityPart && ((MultiPartEntityPart) result.entityHit).parent == this.shootingEntity);
        if (isShootingEntity || isPartOfShootingEntity || world.isRemote || this.shootingEntity == null) {
            return;
        }

        onImpact(result);
        super.onHit(result);
    }

    protected abstract void onImpact(RayTraceResult result);

    @Override
    public void onUpdate() {

        Vec3d vel = ModUtils.getEntityVelocity(this);
        super.onUpdate();
        // Maintain the velocity the entity has
        ModUtils.setEntityVelocity(this, vel);

        if (this.shootingEntity != null && getDistanceTraveled() > this.travelRange) {
            this.world.setEntityState(this, IMPACT_PARTICLE_BYTE);
            this.onHit(null);
        }
    }

    @Override
    public final boolean attackEntityFrom(@Nonnull DamageSource source, float amount) {
        if (!this.isDead && canTakeDamage) {
            this.setDead();
            this.onHit(null);
        }
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public final boolean canBeCollidedWith() {
        return true;
    }

    @Override
    public final boolean canBeAttackedWithItem() {
        return canTakeDamage;
    }

    @Override
    public int getBrightnessForRender() {
        return 200;
    }
}
