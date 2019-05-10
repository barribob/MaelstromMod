package com.barribob.MaelstromMod.entity.entities.npc;

import java.util.List;

import com.barribob.MaelstromMod.entity.entities.EntityTrader;
import com.barribob.MaelstromMod.init.ModProfessions;

import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class NexusArmorer extends EntityTrader
{
    public NexusArmorer(World worldIn)
    {
	super(worldIn);
	this.isImmovable = true;
	this.setSize(0.8f, 1.5f);
    }
    
    @Override
    protected void initEntityAI()
    {
	this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	return false;
    }

    @Override
    protected List<ITradeList> getTrades()
    {
	return ModProfessions.NEXUS_ARMORER.getTrades(0);
    }

    @Override
    protected String getVillagerName()
    {
	return "Armorer";
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(20);
    }
}
