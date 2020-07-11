package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ProjectileBoneQuake extends ProjectileQuake {
    public static final int PARTICLE_AMOUNT = 5;

    public ProjectileBoneQuake(World worldIn, EntityLivingBase throwerIn, float baseDamage) {
        super(worldIn, throwerIn, baseDamage, null);
        this.setSize(0.25f, 1);
    }

    public ProjectileBoneQuake(World worldIn) {
        super(worldIn);
    }

    public ProjectileBoneQuake(World worldIn, double x, double y, double z) {
        super(worldIn, x, y, z);
    }

    @Override
    public void onUpdate() {
        if (this.shootingEntity instanceof EntityLeveledMob) {
            EntityMaelstromBeast.spawnBone(world, this.getPositionVector(), (EntityLeveledMob) this.shootingEntity);
        }
        super.onUpdate();
    }

    @Override
    protected void spawnParticles() {
        IBlockState block = world.getBlockState(new BlockPos(this.posX, this.posY, this.posZ));
        if (block.isFullCube()) {
            for (int i = 0; i < this.PARTICLE_AMOUNT; i++) {
                ParticleManager.spawnFirework(world, getPositionVector().add(new Vec3d(ModRandom.getFloat(0.125f), 0, ModRandom.getFloat(0.125f))), ModColors.WHITE,
                        ModUtils.yVec(0.1f + ModRandom.getFloat(0.1f)));
            }
        }
    }
}
