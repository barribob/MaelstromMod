package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.action.ActionFireballBurst;
import com.barribob.MaelstromMod.entity.action.ActionGoldenRunes;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGoldenPillar extends EntityMaelstromMob
{
    public static final String ID = "golden_pillar";
    private Action fireballBurst = new ActionFireballBurst();
    private Action goldenRune = new ActionGoldenRunes();
    private Action currentAction = fireballBurst;

    public EntityGoldenPillar(World worldIn)
    {
	super(worldIn);
	this.setSize(1.4f, 3.2f);
	this.setNoGravity(true);
	this.setLevel(2.5f);
	this.setImmovable(true);
    }

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(4, new EntityAIRangedAttack<EntityGoldenPillar>(this, 0f, 40, 39, 30.0f, 0f));
    }

    @Override
    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
	this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40);
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	world.setEntityState(this, ModUtils.PARTICLE_BYTE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == ModUtils.PARTICLE_BYTE)
	{
	    Vec3d particleColor = this.currentAction == fireballBurst ? new Vec3d(0.8, 0.4, 0.4) : ModColors.YELLOW;

	    // Spawn particles as the eyes
	    ModUtils.performNTimes(3, (i) -> {
		Vec3d look = this.getVectorForRotation(0, this.renderYawOffset + (i * 120)).scale(0.5f);
		Vec3d pos = this.getPositionVector().add(new Vec3d(0, this.getEyeHeight(), 0));
		ParticleManager.spawnEffect(world, pos.add(look), particleColor);
	    });
	    if (this.isSwingingArms())
	    {
		ParticleManager.spawnFirework(world, this.getPositionVector().add(new Vec3d(ModRandom.getFloat(0.25f), 1, ModRandom.getFloat(0.25f))), particleColor,
			new Vec3d(0, 0.15, 0));
	    }
	}
	super.handleStatusUpdate(id);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor)
    {
	currentAction.performAction(this, target);
    }

    @Override
    public void setSwingingArms(boolean swingingArms)
    {
	super.setSwingingArms(swingingArms);
	if (this.getAttackTarget() == null)
	{
	    return;
	}

	double distance = this.getDistanceSq(this.getAttackTarget());
	if (distance < Math.pow(8, 2))
	{
	    currentAction = fireballBurst;
	}
	else
	{
	    currentAction = goldenRune;
	}
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
	return SoundEvents.BLOCK_METAL_PLACE;
    }

    @Override
    protected SoundEvent getDeathSound()
    {
	return SoundEvents.BLOCK_METAL_BREAK;
    }

    @Override
    protected ResourceLocation getLootTable()
    {
	return LootTableHandler.GOLDEN_MAELSTROM;
    }
}
