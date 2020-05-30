package com.barribob.MaelstromMod.entity.util;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.packets.MessageDirectionForRender;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityTuningForkLazer extends Entity implements DirectionalRender {
    private Vec3d renderLazerPos;
    public static final int TICK_LIFE = 20;

    public EntityTuningForkLazer(World worldIn) {
	super(worldIn);
    }

    public EntityTuningForkLazer(World worldIn, Vec3d renderLazerPos) {
	super(worldIn);
	this.renderLazerPos = renderLazerPos;
    }

    @Override
    public void onUpdate() {
	if (this.ticksExisted > 1 && !this.world.isRemote) {
	    Main.network.sendToAllTracking(new MessageDirectionForRender(this, renderLazerPos), this);
	    world.setEntityState(this, ModUtils.PARTICLE_BYTE);
	}
	if (this.ticksExisted > TICK_LIFE) {
	    this.setDead();
	}
	super.onUpdate();
    }

    @Override
    public void handleStatusUpdate(byte id) {
	if (id == ModUtils.PARTICLE_BYTE && this.getRenderDirection() != null) {
	    ModUtils.lineCallback(this.getPositionVector(), this.getRenderDirection(), 10, (pos, i) -> {
		ParticleManager.spawnSwirl2(world, pos, ModColors.RED, Vec3d.ZERO);
	    });
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public float getEyeHeight() {
	return 0;
    }

    @Override
    protected void entityInit() {
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
    }

    @Override
    public void setRenderDirection(Vec3d dir) {
	this.renderLazerPos = dir;
    }

    public Vec3d getRenderDirection() {
	return this.renderLazerPos;
    }
}
