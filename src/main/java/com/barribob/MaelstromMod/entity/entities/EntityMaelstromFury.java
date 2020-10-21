package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.ai.AIFuryDive;
import com.barribob.MaelstromMod.entity.ai.AIPassiveCircle;
import com.barribob.MaelstromMod.entity.ai.AIRandomFly;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.util.IAcceleration;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * 2 ai's - a passive circling flying ai and the dive attack ai
 * The dive attack ai gets priority and is triggered when it goes out of the player view and gets a straight shot
 * The flying ai is a simple avoidance ai that revolves around the player - it takes over as soon as the dive finishes
 * and the entity goes back to revolving around until the dive attack cooldown and other conditions are met again.
 * We can probably reuse the healer acceleration to an extent.
 */

public class EntityMaelstromFury extends EntityMaelstromMob implements IAcceleration {
    Vec3d acceleration = Vec3d.ZERO;
    public EntityMaelstromFury(World worldIn) {
        super(worldIn);
        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
        if(!worldIn.isRemote) {
            ModBBAnimations.animation(this, "fury.fly", false);
        }
        this.setSize(1.2f, 1.2f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        Vec3d prevAcceleration = acceleration;
        acceleration = ModUtils.getEntityVelocity(this).scale(0.1).add(this.acceleration.scale(0.9));

        if (!world.isRemote) {
            if(prevAcceleration.y > 0 && acceleration.y <= 0) {
                ModBBAnimations.animation(this, "fury.fly", true);
            }
            else if (prevAcceleration.y <= 0 && acceleration.y > 0) {
                ModBBAnimations.animation(this, "fury.fly", false);
            }
        }
    }

    public Vec3d getAcceleration() {
        return acceleration;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(4, new AIRandomFly(this));
        this.tasks.addTask(3, new AIPassiveCircle<>(this, 30));
        this.tasks.addTask(2, new AIFuryDive(40, 5 * 20, this, this::onDiveStart, this::onDiveEnd, this::whileDiving));
        super.initEntityAI();
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        ModUtils.aerialTravel(this, strafe, vertical, forward);
    }

    private void onDiveStart() {
        ModBBAnimations.animation(this, "fury.dive", false);
    }

    private void whileDiving() {
        Vec3d entityVelocity = ModUtils.getEntityVelocity(this);
        Vec3d spearPos = ModUtils.getAxisOffset(entityVelocity.normalize(), ModUtils.X_AXIS.scale(1.7)).add(getPositionVector());
        DamageSource damageSource = ModDamageSource.builder()
                .type(ModDamageSource.MOB)
                .disablesShields()
                .directEntity(this)
                .element(getElement())
                .build();
        float velocity = (float) entityVelocity.lengthVector();
        ModUtils.handleAreaImpact(0.7f, e -> getAttack() * velocity * 2, this, spearPos, damageSource, 0.5f, 0);
        Main.network.sendToAllTracking(new MessageModParticles(EnumModParticles.EFFECT, spearPos, entityVelocity, ModColors.PURPLE), this);
    }

    private void onDiveEnd() {
        ModBBAnimations.animation(this, "fury.dive", true);
        ModBBAnimations.animation(this, "fury.undive", false);
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, @Nonnull IBlockState state, @Nonnull BlockPos pos) {
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(@Nonnull EntityLivingBase target, float distanceFactor) {
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.getEntityBoundingBox().grow(targetDistance);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsHandler.ENTITY_SHADE_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsHandler.ENTITY_SHADE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsHandler.ENTITY_SHADE_HURT;
    }
}
