package com.barribob.MaelstromMod.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class ProjectileMeteorSpawner extends ProjectileGun {
    private static final int EXPOSION_AREA_FACTOR = 6;
    private ItemStack stack;

    public ProjectileMeteorSpawner(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
        this.stack = stack;
        this.setNoGravity(true);
    }

    public ProjectileMeteorSpawner(World worldIn) {
        super(worldIn);
        this.setNoGravity(true);
    }

    public ProjectileMeteorSpawner(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
        this.setNoGravity(true);
    }

    @Override
    protected void spawnParticles() {
    }

    @Override
    protected void onHit(RayTraceResult result) {
        if (this.shootingEntity != null && !world.isRemote) {
            ProjectileMeteor meteor = new ProjectileMeteor(world, shootingEntity, this.getDamage(), stack);
            meteor.setPosition(this.posX, this.posY + 25, this.posZ);
            meteor.shoot(this.shootingEntity, 90, 0, 0.0F, 1.0f, 0);
            meteor.setTravelRange(100f);
            world.spawnEntity(meteor);
        }
        super.onHit(result);
    }
}
