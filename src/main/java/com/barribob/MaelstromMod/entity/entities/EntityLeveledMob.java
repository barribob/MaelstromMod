package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.animation.Animation;
import com.barribob.MaelstromMod.entity.animation.AnimationNone;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * A base class for mob to scale nicely with the leveling system. Also
 * streamlines some of the attribute setting, namely attack and max health
 *
 */
public abstract class EntityLeveledMob extends EntityCreature
{
    private float level;
    
    @SideOnly(Side.CLIENT)
    protected Animation currentAnimation;

    protected boolean isImmovable = false;
    private Vec3d initialPosition = null;

    public EntityLeveledMob(World worldIn)
    {
	super(worldIn);
	this.setLevel(1);
    }

    @Override
    public void onLivingUpdate()
    {
	super.onLivingUpdate();
	
	if (world.isRemote && currentAnimation != null)
	{
	    currentAnimation.update();
	}

	if (this.isImmovable && this.initialPosition != null)
	{
	    this.setPosition(initialPosition.x, initialPosition.y, initialPosition.z);
	}
    }

    // Hold the entity in the same position
    public void setPosition(double x, double y, double z)
    {
	super.setPosition(x, y, z);
	if (this.isImmovable)
	{
	    if (this.initialPosition == null)
	    {
		this.initialPosition = ModUtils.entityPos(this);
	    }
	    else
	    {
		super.setPosition(initialPosition.x, initialPosition.y, initialPosition.z);
	    }
	}
    }
    
    @SideOnly(Side.CLIENT)
    public Animation getCurrentAnimation()
    {
	return this.currentAnimation == null ? new AnimationNone() : this.currentAnimation;
    }

    public float getLevel()
    {
	return this.level;
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
    }

    /**
     * Sets the level, updates attributes, and set health to the updated max health
     */
    public void setLevel(float level)
    {
	this.level = level;

	// Default 20 base health and 0 attack
	this.setBaseMaxHealth(20);
	this.setBaseAttack(0);

	this.updateAttributes();

	// Completely heal the entity after setting the level
	this.setHealth(this.getMaxHealth());
    }

    protected abstract void updateAttributes();

    @Override
    public void writeEntityToNBT(NBTTagCompound compound)
    {
	compound.setFloat("level", level);
	compound.setBoolean("isImmovable", this.isImmovable);
	if (initialPosition != null)
	{
	    compound.setDouble("initialX", initialPosition.x);
	    compound.setDouble("initialY", initialPosition.y);
	    compound.setDouble("initialZ", initialPosition.z);
	}
	super.writeEntityToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound)
    {
	if (compound.hasKey("level"))
	{
	    this.setLevel(compound.getFloat("level"));
	}
	if (compound.hasKey("isImmovable"))
	{
	    this.isImmovable = compound.getBoolean("isImmovable");
	}
	if (compound.hasKey("initialX"))
	{
	    this.initialPosition = new Vec3d(compound.getDouble("initialX"), compound.getDouble("initialY"), compound.getDouble("initialZ"));
	}
	super.readFromNBT(compound);
    }

    /**
     * Get the progression multiplier based on the level of the entity
     */
    protected float getProgressionMultiplier()
    {
	return LevelHandler.getMultiplierFromLevel(this.getLevel());
    }

    /**
     * Sets the base attack, so that the leveling can affect it
     */
    protected void setBaseAttack(float attack)
    {
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(attack * this.getProgressionMultiplier());
    }

    /**
     * Return the shared monster attribute attack
     */
    public float getAttack()
    {
	return (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
    }

    /*
     * Set the base max health so that the leveling can affect it.
     */
    protected void setBaseMaxHealth(float health)
    {
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(health * this.getProgressionMultiplier());
    }
}
