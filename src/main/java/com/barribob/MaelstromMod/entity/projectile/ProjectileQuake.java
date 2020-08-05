package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

import java.util.List;

/**
 * Projectile from the quake staff
 */
public class ProjectileQuake extends ProjectileGun {
    protected static final float AREA_FACTOR = 0.5f;

    public ProjectileQuake(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
        this.setNoGravity(true);
    }

    public ProjectileQuake(World worldIn) {
        super(worldIn);
    }

    public ProjectileQuake(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        IBlockState block = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
        if (block.isFullCube()) {
            for (int i = 0; i < 5; i++) {
                ParticleManager.spawnBreak(world, this.getPositionVector().add(ModRandom.randVec().scale(1.0f).add(ModUtils.yVec(0.75f))), Item.getItemFromBlock(block.getBlock()), ModRandom.randVec().scale(0.1).add(ModUtils.yVec(0.1f)));
            }
            if (this.getElement() != Element.NONE) {
                ParticleManager.spawnEffect(world, getPositionVector().add(ModUtils.yVec(0.5f)).add(ModRandom.randVec()), this.getElement().particleColor);
            }
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        // Keeps the projectile on the surface of the ground
        int updates = 5;
        for (int i = 0; i < updates; i++) {
            if (!world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).isFullCube()) {
                this.setPosition(this.posX, this.posY - 0.25f, this.posZ);
            } else if (world.getBlockState(new BlockPos(this.posX, this.posY + 1, this.posZ)).isFullCube()) {
                this.setPosition(this.posX, this.posY + 0.25f, this.posZ);
            }
        }

        playQuakeSound();

        /*
         * Find all entities in a certain area and deal damage to them
         */
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(AREA_FACTOR).expand(0, 0.25f, 0));
        for (Entity entity : list) {
            if (entity instanceof EntityLivingBase && this.shootingEntity != null && entity != this.shootingEntity) {
                int burnTime = this.isBurning() ? 5 : 0;
                entity.setFire(burnTime);

                DamageSource source = ModDamageSource.builder()
                        .type(ModDamageSource.PROJECTILE)
                        .indirectEntity(shootingEntity)
                        .directEntity(this)
                        .element(getElement())
                        .stoppedByArmorNotShields().build();

                entity.attackEntityFrom(source, this.getGunDamage(entity));

                // Apply knockback enchantment
                if (this.getKnockback() > 0) {
                    float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                    if (f1 > 0.0F) {
                        entity.addVelocity(this.motionX * this.getKnockback() * 0.6000000238418579D / f1, 0.1D,
                                this.motionZ * this.getKnockback() * 0.6000000238418579D / f1);
                    }
                }
            }
        }

        // If the projectile hits water and looses all of its velocity, despawn
        if (!world.isRemote && Math.abs(this.motionX) + Math.abs(this.motionZ) < 0.01f) {
            this.setDead();
        }
    }

    protected void playQuakeSound() {
        // Play the block break sound
        BlockPos pos = new BlockPos(this.posX, this.posY, this.posZ);
        IBlockState state = world.getBlockState(pos);
        if (state.isFullCube()) {
            world.playSound(this.posX, this.posY, this.posZ, state.getBlock().getSoundType(state, world, pos, this).getStepSound(), SoundCategory.BLOCKS, 1.0F, 1.0F, false);
        }
    }

    @Override
    protected void onHit(RayTraceResult result) {
    }
}
