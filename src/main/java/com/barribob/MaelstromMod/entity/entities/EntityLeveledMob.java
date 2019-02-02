package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.util.handlers.LevelHandler;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * 
 * A base class for mob to scale nicely with the leveling system. Also
 * streamlines some of the attribute setting, namely attack and max health
 *
 */
public abstract class EntityLeveledMob extends EntityCreature
{
    private float level;

    public EntityLeveledMob(World worldIn)
    {
	super(worldIn);
	this.setLevel(1);
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
	super.writeEntityToNBT(compound);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound)
    {
	if (compound.hasKey("level"))
	{
	    this.setLevel(compound.getFloat("level"));
	}
	super.readEntityFromNBT(compound);
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
    protected float getAttack()
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
