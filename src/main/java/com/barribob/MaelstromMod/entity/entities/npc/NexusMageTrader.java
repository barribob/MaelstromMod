package com.barribob.MaelstromMod.entity.entities.npc;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityTrader;
import com.barribob.MaelstromMod.init.ModProfessions;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NexusMageTrader extends EntityTrader
{
    private byte magic = 4;

    public NexusMageTrader(World worldIn)
    {
	super(worldIn);
	this.isImmovable = true;
    }

    @Override
    protected void initEntityAI()
    {
	this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 5.0F, 1.0F));
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	world.setEntityState(this, magic);
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id == magic)
	{
	    // Magic particles centered in the mage's staff
	    Vec3d look = this.getVectorForRotation(0, this.renderYawOffset);
	    Vec3d pos = ModUtils.entityPos(this).add(new Vec3d(0, this.getEyeHeight(), 0));
	    Vec3d side = look.scale(0.55).rotateYaw((float) Math.PI * -0.5f);
	    Vec3d offset = pos.add(look.scale(0.5f)).add(side).add(new Vec3d(0, 0.75, 0));

	    ParticleManager.spawnEffect(world, offset, new Vec3d(0.3, 0.9, 0.3));
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	return false;
    }

    @Override
    protected List<ITradeList> getTrades()
    {
	return ModProfessions.NEXUS_MAGE.getTrades(0);
    }

    @Override
    protected String getVillagerName()
    {
	return "Magic Merchant";
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(20);
    }
}
