package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileRepeater extends ProjectileBullet {
    public ProjectileRepeater(World worldIn, EntityLivingBase throwerIn, float baseDamage, ItemStack stack) {
        super(worldIn, throwerIn, baseDamage, stack);
    }

    public ProjectileRepeater(World worldIn) {
        super(worldIn);
    }

    public ProjectileRepeater(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    protected void spawnParticles() {
        if (this.getElement() == Element.NONE) {
            world.spawnParticle(EnumParticleTypes.REDSTONE, this.posX, this.posY, this.posZ, 0, 0, 0);
        } else {
            ParticleManager.spawnDust(world, getPositionVector(), this.getElement().particleColor, Vec3d.ZERO, ModRandom.range(10, 15));
        }
    }
}