package com.barribob.MaelstromMod.entity.util;

import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A class that exists to spawn particles. It is to circumvent the less flexible
 * enum particle spawning method.
 */
public abstract class EntityParticleSpawner extends Entity {
    private boolean spawnedParticles = false;

    public EntityParticleSpawner(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        this.setDead();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            spawnParticles();
        }
        super.handleStatusUpdate(id);
    }

    @SideOnly(Side.CLIENT)
    protected abstract void spawnParticles();

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }
}
