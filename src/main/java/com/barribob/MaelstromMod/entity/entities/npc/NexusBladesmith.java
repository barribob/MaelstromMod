package com.barribob.MaelstromMod.entity.entities.npc;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityTrader;
import com.barribob.MaelstromMod.init.ModProfessions;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class NexusBladesmith extends EntityTrader
{
    public NexusBladesmith(World worldIn)
    {
	super(worldIn);
	this.setImmovable(true);
	this.setNoGravity(true);
    }

    @Override
    protected void initEntityAI()
    {
	this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 5.0F, 1.0F));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	return false;
    }

    @Override
    protected List<ITradeList> getTrades()
    {
	return ModProfessions.NEXUS_BLADESMITH.getTrades(0);
    }

    @Override
    protected String getVillagerName()
    {
	return "Bladesmith";
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }
}
