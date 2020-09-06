package com.barribob.MaelstromMod.entity.projectile;

import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromBeast;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;

public class ProjectileBoneQuake extends ProjectileBeastQuake {

    public ProjectileBoneQuake(World worldIn, EntityLivingBase throwerIn, float baseDamage) {
        super(worldIn, throwerIn, baseDamage);
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
}
