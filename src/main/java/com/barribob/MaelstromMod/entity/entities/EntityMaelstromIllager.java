package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttackNoReset;
import com.barribob.MaelstromMod.entity.animation.AnimationClip;
import com.barribob.MaelstromMod.entity.animation.AnimationOscillateArms;
import com.barribob.MaelstromMod.entity.animation.StreamAnimation;
import com.barribob.MaelstromMod.entity.model.ModelMaelstromIllager;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHorrorAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromWisp;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public class EntityMaelstromIllager extends EntityMaelstromMob {
    private final byte summonMob = 4;
    private final byte magicMissile = 5;
    private final byte wisp = 6;
    private final byte shield = 7;
    private final byte enemy = 8;
    private final float shieldSize = 4;
    private EntityAIRangedAttack<EntityMaelstromMob> phase1AttackAI;
    private final ComboAttack attackHandler = new ComboAttack();

    private final Action spawnEnemy = new Action() {
        @Override
        public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
            int mobCount = phase2() ? getMobConfig().getInt("summoning_algorithm.second_phase_mobs_per_spawn") :
                    getMobConfig().getInt("summoning_algorithm.first_phase_mobs_per_spawn");
            for (int i = 0; i < mobCount; i++) {
                ModUtils.spawnMob(world, getPosition(), getLevel(), getMobConfig().getConfig("summoning_algorithm"));
            }
            actor.playSound(SoundsHandler.get("illager.summon_attack"), 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
        }
    };

    // Responsible for the boss bar
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));

    public EntityMaelstromIllager(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.5f);
        this.healthScaledAttackFactor = 0.2;
        if (!world.isRemote) {
            attackHandler.setAttack(magicMissile, new Action() {
                @Override
                public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
                    ModUtils.throwProjectile(actor, target, new ProjectileHorrorAttack(world, actor, getAttack()), 6.0f, 1.2f,
                            ModUtils.getRelativeOffset(actor, new Vec3d(0, 0, 1)));
                    actor.playSound(SoundsHandler.get("illager.projectile_attack"), 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                }
            });
            attackHandler.setAttack(wisp, new Action() {
                @Override
                public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
                    Projectile proj = new ProjectileMaelstromWisp(world, actor, getAttack());
                    proj.setTravelRange(15f);
                    ModUtils.throwProjectile(actor, target, proj, 1.0f, 1.0f);
                    actor.playSound(SoundsHandler.get("illager.tunnel_attack"), 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
                }
            });
            attackHandler.setAttack(shield, new Action() {
                @Override
                public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
                    DamageSource damageSource = ModDamageSource.builder()
                            .directEntity(actor)
                            .type(ModDamageSource.MAGIC)
                            .element(getElement())
                            .stoppedByArmorNotShields().build();

                    ModUtils.handleAreaImpact(shieldSize, (e) -> getAttack(), actor, getPositionVector(), damageSource);
                    actor.playSound(SoundsHandler.get("illager.shield_attack"), 1.0F, 0.4F / (world.rand.nextFloat() * 0.4F + 0.8F));
                    actor.world.setEntityState(actor, ModUtils.THIRD_PARTICLE_BYTE);
                }
            });
            attackHandler.setAttack(enemy, spawnEnemy);
        }
    }

    @Override
    protected void initAnimation() {
        List<List<AnimationClip<ModelMaelstromIllager>>> animationMissile = new ArrayList<List<AnimationClip<ModelMaelstromIllager>>>();
        List<AnimationClip<ModelMaelstromIllager>> rightArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();
        List<AnimationClip<ModelMaelstromIllager>> leftArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();

        BiConsumer<ModelMaelstromIllager, Float> leftArmMover = (model, f) -> {
            model.bipedLeftArm.rotateAngleX = f;
            model.bipedLeftArm.rotateAngleY = 0;
            model.bipedLeftArm.rotateAngleZ = f.floatValue() / -6;
        };
        BiConsumer<ModelMaelstromIllager, Float> rightArmMover = (model, f) -> {
            model.bipedRightArm.rotateAngleX = 0;
            model.bipedRightArm.rotateAngleY = 0;
            model.bipedRightArm.rotateAngleZ = 0;
        };

        leftArm.add(new AnimationClip(12, 0, -180, leftArmMover));
        leftArm.add(new AnimationClip(8, -180, -180, leftArmMover));
        leftArm.add(new AnimationClip(4, -180, 0, leftArmMover));

        rightArm.add(new AnimationClip(12, 0, -180, rightArmMover));
        rightArm.add(new AnimationClip(8, -180, -180, rightArmMover));
        rightArm.add(new AnimationClip(4, -180, 0, rightArmMover));

        animationMissile.add(rightArm);
        animationMissile.add(leftArm);

        attackHandler.setAttack(magicMissile, Action.NONE, () -> new StreamAnimation(animationMissile));

        List<List<AnimationClip<ModelMaelstromIllager>>> animationWisp = new ArrayList<List<AnimationClip<ModelMaelstromIllager>>>();
        rightArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();
        leftArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();

        leftArmMover = (model, f) -> {
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(-90);
            model.bipedLeftArm.rotateAngleY = f;
            model.bipedLeftArm.rotateAngleZ = 0;
        };
        rightArmMover = (model, f) -> {
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(-90);
            model.bipedRightArm.rotateAngleY = f;
            model.bipedRightArm.rotateAngleZ = 0;
        };

        leftArm.add(new AnimationClip(10, 0, -90, leftArmMover));
        leftArm.add(new AnimationClip(8, -90, -90, leftArmMover));
        leftArm.add(new AnimationClip(4, -90, 0, leftArmMover));

        rightArm.add(new AnimationClip(10, 0, 90, rightArmMover));
        rightArm.add(new AnimationClip(8, 90, 90, rightArmMover));
        rightArm.add(new AnimationClip(4, 90, 0, rightArmMover));

        animationWisp.add(rightArm);
        animationWisp.add(leftArm);

        attackHandler.setAttack(wisp, Action.NONE, () -> new StreamAnimation(animationWisp));

        List<List<AnimationClip<ModelMaelstromIllager>>> animationShield = new ArrayList<List<AnimationClip<ModelMaelstromIllager>>>();

        rightArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();
        leftArm = new ArrayList<AnimationClip<ModelMaelstromIllager>>();

        leftArmMover = (model, f) -> {
            model.bipedLeftArm.rotateAngleX = f;
            model.bipedLeftArm.rotateAngleY = -f * 0.45f;
            model.bipedLeftArm.rotateAngleZ = 0;
        };
        rightArmMover = (model, f) -> {
            model.bipedRightArm.rotateAngleX = f;
            model.bipedRightArm.rotateAngleY = f * 0.45f;
            model.bipedRightArm.rotateAngleZ = 0;
        };

        leftArm.add(new AnimationClip(10, 0, -120, leftArmMover));
        leftArm.add(new AnimationClip(8, -120, -120, leftArmMover));
        leftArm.add(new AnimationClip(4, -120, 0, leftArmMover));

        rightArm.add(new AnimationClip(10, 0, -120, rightArmMover));
        rightArm.add(new AnimationClip(8, -120, -120, rightArmMover));
        rightArm.add(new AnimationClip(4, -120, 0, rightArmMover));

        animationShield.add(rightArm);
        animationShield.add(leftArm);

        attackHandler.setAttack(shield, Action.NONE, () -> new StreamAnimation(animationShield));
        attackHandler.setAttack(enemy, Action.NONE, () -> new AnimationOscillateArms(60, this));

        this.currentAnimation = new AnimationOscillateArms(60, this);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        phase1AttackAI = new EntityAIRangedAttackNoReset<EntityMaelstromMob>(this, 1.25f, 360, 60, 15.0f, 0.5f);
        this.tasks.addTask(4, phase1AttackAI);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.EVOCATION_ILLAGER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_EVOCATION_ILLAGER_HURT;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.MAELSTROM_ILLAGER;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.isSwingingArms()) {
            if (!source.isProjectile()) {
                Entity entity = source.getImmediateSource();

                if (entity instanceof EntityLivingBase) {
                    this.blockUsingShield((EntityLivingBase) entity);
                }
            }
            this.playSound(SoundsHandler.ENTITY_CHAOS_KNIGHT_BLOCK, 1.0f, 0.9f + ModRandom.getFloat(0.2f));

            return false;
        }

        float prevHealth = this.getHealth();
        double firstDialogHp = getMobConfig().getDouble("first_dialog_hp");
        double secondDialogHP = getMobConfig().getDouble("second_dialog_hp");
        double secondPhaseHp = getMobConfig().getDouble("second_boss_phase_hp");
        boolean flag = super.attackEntityFrom(source, amount);

        String message = "";
        if (prevHealth > firstDialogHp && this.getHealth() <= firstDialogHp) {
            message = "illager_1";
        }

        if (prevHealth > secondDialogHP && this.getHealth() <= secondDialogHP) {
            message = "illager_2";
        }

        if (prevHealth > secondPhaseHp && this.getHealth() <= secondPhaseHp) {
            message = "illager_3";
        }

        if (message != "") {
            for (EntityPlayer player : this.bossInfo.getPlayers()) {
                player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Maelstrom Illager: " + TextFormatting.WHITE)
                        .appendSibling(new TextComponentTranslation(ModUtils.LANG_CHAT + message)));
            }
        }

        return flag;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        this.phase1AttackAI.setAttackCooldowns(
                phase2() ? 50 : 360,
                phase2() ? 20 : 60
        );

        if (phase2() && attackHandler.getCurrentAttack() != 0) {
            attackHandler.getCurrentAttackAction().performAction(this, target);
        } else {
            spawnEnemy.performAction(this, target);
        }
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!phase2() || (phase2() && attackHandler.getCurrentAttack() == enemy)) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        } else if (this.attackHandler != null && this.isSwingingArms()) {
            if (this.attackHandler.getCurrentAttack() == magicMissile) {
                world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
            }
        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        super.setSwingingArms(swingingArms);
        if (this.isSwingingArms()) {
            if (phase2()) {
                Byte[] attack = {wisp, magicMissile, enemy};
                double[] weights = {0.5, 0.5, 0.2};
                attackHandler.setCurrentAttack(ModRandom.choice(attack, this.getRNG(), weights).next());
                if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 4) {
                    attackHandler.setCurrentAttack(shield);
                }
                world.setEntityState(this, attackHandler.getCurrentAttack());
            } else {
                world.setEntityState(this, summonMob);
            }
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == summonMob) {
            this.currentAnimation = new AnimationOscillateArms(60, this);
            currentAnimation.startAnimation();
        } else if (id >= 5 && id <= 8) {
            currentAnimation = attackHandler.getAnimation(id);
            getCurrentAnimation().startAnimation();
        } else if (id == ModUtils.THIRD_PARTICLE_BYTE) {
            for (int i = 0; i < 1000; i++) {
                Vec3d unit = new Vec3d(0, 1, 0);
                unit = unit.rotatePitch((float) (Math.PI * ModRandom.getFloat(1)));
                unit = unit.rotateYaw((float) (Math.PI * ModRandom.getFloat(1)));
                unit = unit.normalize().scale(shieldSize);
                ParticleManager.spawnEffect(world, unit.add(getPositionVector()), ModColors.PURPLE);
            }
        } else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            ParticleManager.spawnMaelstromPotionParticle(world, rand, ModUtils.getRelativeOffset(this, new Vec3d(0, this.getEyeHeight(), 1)).add(getPositionVector()), true);
        } else if (id == ModUtils.PARTICLE_BYTE) {
            if (this.isSwingingArms()) {
                float f = this.renderYawOffset * 0.017453292F + MathHelper.cos(this.ticksExisted * 0.6662F) * 0.25F;
                float f1 = MathHelper.cos(f);
                float f2 = MathHelper.sin(f);
                ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX + f1 * 0.6D, this.posY + 1.8D, this.posZ + f2 * 0.6D), true);
                ParticleManager.spawnMaelstromPotionParticle(world, rand, new Vec3d(this.posX - f1 * 0.6D, this.posY + 1.8D, this.posZ - f2 * 0.6D), true);
            }
        }
        super.handleStatusUpdate(id);
    }

    private boolean phase2() {
        return this.getHealth() < getMobConfig().getInt("second_boss_phase_hp");
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }

        super.readEntityFromNBT(compound);
    }

    @Override
    public void setCustomNameTag(String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    protected void updateAITasks() {
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());
        super.updateAITasks();
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