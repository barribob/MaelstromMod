package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

/**
 * 
 * Represent the attibutes and logic of the shade monster
 *
 */
public class EntityShade extends EntityZombie
{
	public EntityShade(World worldIn) 
	{
		super(worldIn);
	}
	
	@Override
	protected void applyEntityAttributes() 
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(22.0D);
	}
	
	@Override
	protected SoundEvent getAmbientSound() 
	{
		return SoundsHandler.ENTITY_SHADE_AMBIENT;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundsHandler.ENTITY_SHADE_HURT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundsHandler.ENTITY_SHADE_DEATH;
	}
	
	@Override
	protected ResourceLocation getLootTable() {
		// TODO Auto-generated method stub
		return LootTableHandler.SHADE;
	}
}
