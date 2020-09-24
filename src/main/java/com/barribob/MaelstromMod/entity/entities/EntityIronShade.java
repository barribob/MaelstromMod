package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.IAction;
import com.barribob.MaelstromMod.entity.action.ActionSpinSlash;
import com.barribob.MaelstromMod.entity.ai.AIJumpAtTarget;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelIronShade;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class EntityIronShade extends EntityMaelstromMob {
    private ComboAttack attackHandler = new ComboAttack();
    private byte frontFlip = 4;
    private byte spin = 5;
    private int spinDuration = 30;
    private int maxSpinDuration = 30;
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_6));

    public EntityIronShade(World worldIn) {
        super(worldIn);
        this.healthScaledAttackFactor = 0.2;
        this.setSize(0.9f, 2.2f);
        if (!worldIn.isRemote) {
            attackHandler.setAttack(frontFlip, (IAction) (actor, target) -> {
                DamageSource source = ModDamageSource.builder()
                        .directEntity(this)
                        .element(getElement())
                        .type(ModDamageSource.MOB)
                        .disablesShields().build();

                Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec().scale(2.0f));
                this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.8F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
                ModUtils.handleAreaImpact(1.0f, (e) -> this.getAttack(), this, pos, source, 0.20f, this.getElement() == Element.CRIMSON ? 3 : 0, false);
                actor.world.setEntityState(actor, ModUtils.SECOND_PARTICLE_BYTE);
            });
            attackHandler.setAttack(spin, new ActionSpinSlash(3.0f));
        }
    }

    @Override
    public void onUpdate() {
        bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        if (!world.isRemote && spinDuration < maxSpinDuration && attackHandler.getCurrentAttack() == spin) {
            spinDuration++;
            if (this.ticksExisted % 5 == 0) {
                attackHandler.getCurrentAttackAction().performAction(this, null);
            }
        } else if (!world.isRemote && !this.isSwingingArms()) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
        super.onUpdate();
    }

    @Override
    protected void initAnimation() {
        List<List<AnimationClip<ModelIronShade>>> flipAnimations = new ArrayList<List<AnimationClip<ModelIronShade>>>();
        List<AnimationClip<ModelIronShade>> wisps = new ArrayList<AnimationClip<ModelIronShade>>();
        List<AnimationClip<ModelIronShade>> arms = new ArrayList<AnimationClip<ModelIronShade>>();
        List<AnimationClip<ModelIronShade>> body = new ArrayList<AnimationClip<ModelIronShade>>();
        List<AnimationClip<ModelIronShade>> lowerChains = new ArrayList<AnimationClip<ModelIronShade>>();
        List<AnimationClip<ModelIronShade>> upperChains = new ArrayList<AnimationClip<ModelIronShade>>();

        BiConsumer<ModelIronShade, Float> bodyFlipper = (model, f) -> {
            model.wisps.rotateAngleX = f;
            model.wisps.rotateAngleY = 0;
        };
        BiConsumer<ModelIronShade, Float> bodyBender = (model, f) -> {
            model.body.rotateAngleX = f;
        };
        BiConsumer<ModelIronShade, Float> armMover = (model, f) -> {
            model.rightArm.rotateAngleX = f;
            model.leftArm.rotateAngleX = f;
        };
        BiConsumer<ModelIronShade, Float> lowerChainMover = (model, f) -> {
            model.chainLink1.rotateAngleX = f / 8;
            model.chainLink2.rotateAngleX = f / 4;
            model.chainLink3.rotateAngleX = f / 2;
            model.chainLink4.rotateAngleX = f;
            model.chainLink1_1.rotateAngleX = f / 8;
            model.chainLink2_1.rotateAngleX = f / 4;
            model.chainLink3_1.rotateAngleX = f / 2;
            model.chainLink4_1.rotateAngleX = f;
        };
        BiConsumer<ModelIronShade, Float> ballAndChainMover = (model, f) -> {
            model.chainLink5.rotateAngleX = f;
            model.chainLink6.rotateAngleX = f;
            model.ball.rotateAngleX = f;
            model.chainLink5_1.rotateAngleX = f;
            model.chainLink6_1.rotateAngleX = f;
            model.ball_1.rotateAngleX = f;
        };

        int flipLength = 10;
        int swingDownLength = 4;
        int stopLength = 8;
        int resetLength = 8;

        wisps.add(new AnimationClip(flipLength, 0, 360, bodyFlipper));
        wisps.add(new AnimationClip(swingDownLength + stopLength + resetLength, 360, 360, bodyFlipper));

        body.add(new AnimationClip(flipLength, 0, 0, bodyBender));
        body.add(new AnimationClip(swingDownLength, 0, 50, bodyBender));
        body.add(new AnimationClip(stopLength, 50, 50, bodyBender));
        body.add(new AnimationClip(resetLength, 50, 0, bodyBender));

        arms.add(new AnimationClip(flipLength - 4, 0, -210, armMover));
        arms.add(new AnimationClip(4, -210, -210, armMover));
        arms.add(new AnimationClip(swingDownLength, -210, -90, armMover));
        arms.add(new AnimationClip(stopLength, -90, -90, armMover));
        arms.add(new AnimationClip(resetLength, -90, 0, armMover));

        lowerChains.add(new AnimationClip(flipLength, 48, -16, lowerChainMover));
        lowerChains.add(new AnimationClip(swingDownLength + stopLength, -16, -16, lowerChainMover));
        lowerChains.add(new AnimationClip(resetLength, -16, 48, lowerChainMover));

        upperChains.add(new AnimationClip(flipLength, 0, -32, ballAndChainMover));
        upperChains.add(new AnimationClip(swingDownLength, -32, -32, ballAndChainMover));
        upperChains.add(new AnimationClip(stopLength - 6, -32, -10, ballAndChainMover));
        upperChains.add(new AnimationClip(6, -10, -10, ballAndChainMover));
        upperChains.add(new AnimationClip(resetLength, -10, 0, ballAndChainMover));

        flipAnimations.add(wisps);
        flipAnimations.add(arms);
        flipAnimations.add(body);
        flipAnimations.add(lowerChains);
        flipAnimations.add(upperChains);
        attackHandler.setAttack(frontFlip, IAction.NONE, () -> new StreamAnimation(flipAnimations));

        List<List<AnimationClip<ModelIronShade>>> spinAnimations = new ArrayList<List<AnimationClip<ModelIronShade>>>();
        wisps = new ArrayList<AnimationClip<ModelIronShade>>();
        arms = new ArrayList<AnimationClip<ModelIronShade>>();
        body = new ArrayList<AnimationClip<ModelIronShade>>();
        lowerChains = new ArrayList<AnimationClip<ModelIronShade>>();
        upperChains = new ArrayList<AnimationClip<ModelIronShade>>();

        BiConsumer<ModelIronShade, Float> bodySpinner = (model, f) -> {
            model.wisps.rotateAngleX = 0;
            model.wisps.rotateAngleY = f;
        };

        int chargeLength = 5;
        int speedUpLength = 5;
        int speedUpFasterLength = 5;

        wisps.add(new AnimationClip(chargeLength, 0, -60, bodySpinner));
        wisps.add(new AnimationClip(speedUpLength, 0, 180, bodySpinner));
        wisps.add(new AnimationClip(speedUpFasterLength, 180, 540, bodySpinner));
        wisps.add(new AnimationClip(maxSpinDuration, 540, 3240, bodySpinner));
        wisps.add(new AnimationClip(speedUpFasterLength, 3240, 3240 + 540, bodySpinner));
        wisps.add(new AnimationClip(10, 3240 + 540, 3240 + 540 + 360, bodySpinner));

        body.add(new AnimationClip(15, 0, -20, bodyBender));
        body.add(new AnimationClip(maxSpinDuration, -20, -20, bodyBender));
        body.add(new AnimationClip(15, -20, 0, bodyBender));

        arms.add(new AnimationClip(15, 0, -70, armMover));
        arms.add(new AnimationClip(maxSpinDuration, -70, -70, armMover));
        arms.add(new AnimationClip(15, -70, 0, armMover));

        lowerChains.add(new AnimationClip(15, -48, 0, lowerChainMover));
        lowerChains.add(new AnimationClip(30, 0, 0, lowerChainMover));
        lowerChains.add(new AnimationClip(15, 0, -48, lowerChainMover));

        upperChains.add(new AnimationClip(60, 0, 0, ballAndChainMover));

        spinAnimations.add(wisps);
        spinAnimations.add(arms);
        spinAnimations.add(body);
        spinAnimations.add(lowerChains);
        spinAnimations.add(upperChains);
        attackHandler.setAttack(spin, new ActionSpinSlash(3.0f), () -> new StreamAnimation(spinAnimations));
        this.currentAnimation = new StreamAnimation(flipAnimations);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromMob>(this, 1.3f, 60, 10, 4.5f, 0.4f));
        this.tasks.addTask(0, new AIJumpAtTarget(this, 0.4f, 0.5f));
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        super.setSwingingArms(swingingArms);
        if (swingingArms) {
            Byte[] attack = {spin, frontFlip};
            attackHandler.setCurrentAttack(ModRandom.choice(attack));
            world.setEntityState(this, attackHandler.getCurrentAttack());

            if (attackHandler.getCurrentAttack() == frontFlip) {
                ModUtils.leapTowards(this, this.getAttackTarget().getPositionVector(), 0.2f, 0.5f);
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        // We want a special black flame for the non-elemental shade, and red flames for the crimson element.
        Vec3d flameColor = getElement() == Element.NONE ? new Vec3d(0.1f, 0, 0.1f) : getElement().sweepColor;
        if (id >= 4 && id <= 6) {
            currentAnimation = attackHandler.getAnimation(id);
            getCurrentAnimation().startAnimation();
        } else if (id == EntityHerobrineOne.slashParticleByte) {
            ModUtils.performNTimes(4, (i) -> {
                ModUtils.circleCallback(i, 15, (pos) -> {
                    ParticleManager.spawnColoredFire(world, rand, getPositionVector().add(new Vec3d(pos.x, this.getEyeHeight() - 0.3f + ModRandom.getFloat(0.2f), pos.y)), flameColor);
                });
            });
        } else if (id == ModUtils.PARTICLE_BYTE) {
            Vec3d look = this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
            Vec3d side = look.rotateYaw((float) Math.PI * -0.5f);
            Vec3d offset = getPositionVector().add(side.scale(0.5f * ModRandom.randSign())).add(ModUtils.yVec(rand.nextFloat()));
            ParticleManager.spawnColoredFire(world, rand, offset, flameColor);
            offset = getPositionVector().add(side.scale(0.5f * ModRandom.randSign())).add(look.scale(-rand.nextFloat())).add(ModUtils.yVec(0.1f));
            ParticleManager.spawnColoredFire(world, rand, offset, flameColor);
        } else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            Vec3d pos = this.getPositionVector().add(ModUtils.yVec(1)).add(this.getLookVec().scale(2.0f));
            for (int i = 0; i < 30; i++) {
                ParticleManager.spawnColoredFire(world, rand, pos.add(ModRandom.randVec().add(ModUtils.yVec(ModRandom.getFloat(1.5f)))), flameColor);
            }
        } else {
            super.handleStatusUpdate(id);
        }
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

    @Override
    protected ResourceLocation getLootTable() {
        if (getElement() == Element.CRIMSON) {
            return LootTableHandler.CRIMSON_MINIBOSS;
        }

        return LootTableHandler.IRON_SHADE;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        if (attackHandler.getCurrentAttack() == spin) {
            spinDuration = 0;
        } else {
            attackHandler.getCurrentAttackAction().performAction(this, target);
        }
    }

    @Override
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }
}
