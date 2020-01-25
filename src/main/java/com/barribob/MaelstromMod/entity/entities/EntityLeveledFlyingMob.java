package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IAnimatedMob;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Base class that serves as the flying version of EntityLeveledMob
 */
public abstract class EntityLeveledFlyingMob extends EntityFlying implements IMob, IRangedAttackMob, IAnimatedMob, IElement
{
    @SideOnly(Side.CLIENT)
    protected Animation currentAnimation;
    private float level;

    protected static final DataParameter<Integer> ELEMENT = EntityDataManager.<Integer>createKey(EntityLeveledMob.class, DataSerializers.VARINT);

    public EntityLeveledFlyingMob(World worldIn)
    {
	super(worldIn);
    }

    public float getLevel()
    {
	return level;
    }

    public void setLevel(float level)
    {
	this.level = level;
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();

	if (world.isRemote && currentAnimation != null)
	{
	    currentAnimation.update();
	}
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();

	if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
	{
	    this.setDead();
	}

	/**
	 * Periodically check if the animations need to be reinitialized
	 */
	if (this.ticksExisted % 20 == 0)
	{
	    world.setEntityState(this, animationByte);
	}
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
	world.setEntityState(this, animationByte);
	if (compound.hasKey("element"))
	{
	    this.setElement(Element.getElementFromId(compound.getInteger("element")));
	}

	super.readFromNBT(compound);
    }

    public float getAttack()
    {
	return ModUtils.getMobDamage(this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue(), 0.0, this.getMaxHealth(), this.getHealth(),
		this.level, this.getElement());
    }

    @Override
    public Animation getCurrentAnimation()
    {
	return this.currentAnimation == null ? new AnimationNone() : this.currentAnimation;
    }

    @Override
    protected float applyArmorCalculations(DamageSource source, float damage)
    {
	return super.applyArmorCalculations(source, ModUtils.getArmoredDamage(source, damage, getLevel(), getElement()));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == animationByte && currentAnimation == null)
	{
	    initAnimation();
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @SideOnly(Side.CLIENT)
    protected void initAnimation()
    {
    }

    @Override
    protected void entityInit()
    {
	super.entityInit();
	this.dataManager.register(ELEMENT, Integer.valueOf(Element.NONE.id));
    }

    @Override
    public Element getElement()
    {
	return this.dataManager == null ? Element.getElementFromId(Element.NONE.id) : Element.getElementFromId(this.dataManager.get(ELEMENT));
    }

    public void setElement(Element element)
    {
	this.dataManager.set(ELEMENT, element.id);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setInteger("element", getElement().id);
	super.writeEntityToNBT(compound);
    }
}
