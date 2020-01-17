package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.entities.herobrine_state.HerobrineState;
import com.barribob.MaelstromMod.entity.entities.herobrine_state.StateCliffKey;
import com.barribob.MaelstromMod.entity.entities.herobrine_state.StateCrimsonKey;
import com.barribob.MaelstromMod.entity.entities.herobrine_state.StateEnderPearls;
import com.barribob.MaelstromMod.entity.entities.herobrine_state.StateFirstBattle;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

/*
 * Controls the herobrine fight, with dialogue and ending
 */
public class Herobrine extends EntityLeveledMob
{
    public final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));
    private static final String nbtState = "herobrine_state";
    public HerobrineState state;

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

    public Herobrine(World worldIn)
    {
	super(worldIn);
	state = new StateEnderPearls(this);
	this.setSize(0.5f, 2.0f);
	this.setImmovable(true);
	this.setNoGravity(true);
    }

    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    protected boolean processInteract(EntityPlayer player, EnumHand hand)
    {
	state.rightClick(player);
	return super.processInteract(player, hand);
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();
	state.update();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	if (source.getTrueSource() instanceof EntityPlayer)
	{
	    state.leftClick(this);
	}
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

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (compound.hasKey(nbtState))
	{
	    if (compound.getString(nbtState).equals(new StateCliffKey(this).getNbtString()))
	    {
		state = new StateCliffKey(this);
	    }
	    else if (compound.getString(nbtState).equals(new StateCrimsonKey(this).getNbtString()))
	    {
		state = new StateCrimsonKey(this);
	    }
	    else if (compound.getString(nbtState).equals(new StateFirstBattle(this).getNbtString()))
	    {
		state = new StateFirstBattle(this);
	    }
	}
	super.readEntityFromNBT(compound);
    }

    public void teleportOutside()
    {
	this.setImmovablePosition(new Vec3d(posX, posY, posZ - 5));
	if (!world.isRemote)
	{
	    world.setEntityState(this, (byte) 4);
	}
    }

    @Override
    public void handleStatusUpdate(byte id)
    {
	if (id == 4)
	{
	    teleportOutside();
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setString(nbtState, state.getNbtString());
	super.writeEntityToNBT(compound);
    }
}
