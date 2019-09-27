package com.barribob.MaelstromMod.entity.entities;

import java.util.function.Consumer;

import com.barribob.MaelstromMod.entity.util.EntityCliffPortalSpawn;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.TimedMessager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

public class EntityHerobrineCliff extends EntityLeveledMob
{
    private static final String[] INTRO_MESSAGES = { ModUtils.translateDialog("herobrine_cliff_1"), ModUtils.translateDialog("herobrine_cliff_2"),
	    ModUtils.translateDialog("herobrine_cliff_3"), "" };
    private static final int[] INTRO_MESSAGE_TIMES = { 120, 180, 240, 300 };
    private TimedMessager messager;
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));

    private Consumer<String> spawnPortalSpawner = (s) -> {
	this.setInvisible(true);
	Entity spawner = new EntityCliffPortalSpawn(world);
	spawner.copyLocationAndAnglesFrom(this);
	spawner.setPosition(this.posX - 0.5, this.posY, this.posZ - 0.5);
	spawner.setRotationYawHead(this.rotationYawHead);
	if (!world.isRemote)
	{
	    world.spawnEntity(spawner);
	}
	this.setDead();
    };

    private Consumer<String> messageToPlayers = (message) -> {
	if (message != "")
	{
	    for (EntityPlayer player : this.bossInfo.getPlayers())
	    {
		player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + this.getDisplayName().getFormattedText() + ": " + TextFormatting.WHITE + message));
	    }
	}
    };

    public EntityHerobrineCliff(World worldIn)
    {
	super(worldIn);
	this.setRotation(180, 0);
	this.setRotationYawHead(180);
	this.setRenderYawOffset(180);
	this.messager = new TimedMessager(INTRO_MESSAGES, INTRO_MESSAGE_TIMES, this.spawnPortalSpawner);
	this.setSize(0.5f, 1.0f);
	this.isImmovable = true;
	this.setNoGravity(true);
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 20.0F));
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	this.messager.Update(world, messageToPlayers);
    }

    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	return false;
    }

    @Override
    public boolean canRenderOnFire()
    {
	return false;
    }

    @Override
    public void setCustomNameTag(String name)
    {
	super.setCustomNameTag(name);
	this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player)
    {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }
}
