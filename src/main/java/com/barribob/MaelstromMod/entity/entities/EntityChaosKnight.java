package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.EntityCrimsonPortalSpawn;
import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.projectile.ProjectileChaosFireball;
import com.barribob.MaelstromMod.entity.util.DirectionalRender;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.packets.MessageDirectionForRender;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import com.barribob.MaelstromMod.world.gen.nexus.WorldGenNexusTeleporter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityChaosKnight extends EntityMaelstromMob implements IAttack, DirectionalRender {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    private Vec3d chargeDir;
    private static final float dashRadius = 2;
    private Consumer<EntityLivingBase> prevAttack;

    private final Consumer<EntityLivingBase> sideSwipe = (target) -> {
        ModBBAnimations.animation(this, "chaos_knight.single_slash", false);
        addEvent(() -> {
            float distance = getDistance(target);
            if (distance > 2) {
                ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.45 * Math.sqrt(distance)), 0.5f);
            }
        }, 5);

        addEvent(() -> {
            Vec3d offset = getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(0.5, 1, -1)));
            ModUtils.handleAreaImpact(2, (e) -> getAttack(), this, offset, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.5f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
            Vec3d away = this.getPositionVector().subtract(target.getPositionVector()).normalize();
            ModUtils.leapTowards(this, away, 0.4f, 0.4f);
        }, 18);

        addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 35);
    };

    private final Consumer<EntityLivingBase> leapSlam = (target) -> {
        ModBBAnimations.animation(this, "chaos_knight.leap_slam", false);
        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.45f * Math.sqrt(getDistance(target))), 0.9f);
            fallDistance = -3;
            setLeaping(true);
        }, 20);
        addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 60);
    };

    private final Consumer<EntityLivingBase> dash = (target) -> {
        ModBBAnimations.animation(this, "chaos_knight.dash", false);
        Vec3d dir = getAttackTarget().getPositionVector().subtract(getPositionVector()).normalize();
        Vec3d teleportPos = getAttackTarget().getPositionVector();
        int maxDistance = 10;
        for (int i = 0; i < maxDistance; i++) {
            Vec3d proposedPos = teleportPos.add(dir);
            IBlockState state = world.getBlockState(new BlockPos(proposedPos).down());
            IBlockState aboveState = world.getBlockState(new BlockPos(proposedPos));
            if ((state.canEntitySpawn(this) || aboveState.getBlock() == Blocks.REDSTONE_WIRE || aboveState.getBlock() == Blocks.SKULL) && state.isSideSolid(world, new BlockPos(proposedPos).down(), EnumFacing.UP)) {
                teleportPos = proposedPos;
            }
        }

        this.chargeDir = teleportPos;

        // Send the aimed position to the client side
        Main.network.sendToAllTracking(new MessageDirectionForRender(this, this.chargeDir), this);

        addEvent(() -> {
            world.createExplosion(this, posX, posY, posZ, 2, false);
            ModUtils.lineCallback(getPositionVector(), chargeDir, (int) Math.sqrt(chargeDir.subtract(getPositionVector()).lengthSquared()), (vec, i) -> {
                ModUtils.handleAreaImpact(dashRadius, (e) -> getAttack() * 1.5f, this, vec, ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.3f, 5);
                world.playSound(vec.x, vec.y, vec.z, SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.HOSTILE, 1.0f, 1.0f + ModRandom.getFloat(0.1f), false);
            });
            this.setPositionAndUpdate(chargeDir.x, chargeDir.y, chargeDir.z);
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
            playSound(SoundEvents.ENTITY_LIGHTNING_THUNDER, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
        }, 20);

        addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 40);
    };

    private final Consumer<EntityLivingBase> spinSlash = (target) -> {
        ModBBAnimations.animation(this, "chaos_knight.triple_slash", false);
        Runnable leap = () -> {
            ModUtils.leapTowards(this, target.getPositionVector(), (float) (0.35f * Math.sqrt(getDistance(target))), 0.5f);
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0);
        };
        Runnable meleeAttack = () -> {
            ModUtils.handleAreaImpact(2.7f, (e) -> getAttack(), this, getPositionVector().add(ModUtils.yVec(1)), ModDamageSource.causeElementalMeleeDamage(this, getElement()), 0.5f, 0, false);
            playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
            this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
        };

        addEvent(leap, 10);
        addEvent(meleeAttack, 15);
        addEvent(leap, 23);
        addEvent(meleeAttack, 29);
        addEvent(leap, 34);
        addEvent(meleeAttack, 41);
        addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 60);
    };

    private final Consumer<EntityLivingBase> summonMeteors = (target) -> {
        addEvent(() -> {
            this.motionY += 0.5;
        }, 3);
        ModBBAnimations.animation(this, "chaos_knight.summon", false);
        for (int tick = 20; tick < 140; tick += 5) {
            addEvent(() -> {
                ProjectileChaosFireball meteor = new ProjectileChaosFireball(world, this, this.getAttack(), null);
                Vec3d pos = new Vec3d(ModRandom.getFloat(10), ModRandom.getFloat(1), ModRandom.getFloat(10)).add(this.getPositionVector()).add(ModUtils.yVec(13));
                Vec3d targetPos = new Vec3d(ModRandom.getFloat(5), 0, ModRandom.getFloat(5)).add(target.getPositionVector());
                Vec3d vel = targetPos.subtract(pos).normalize().scale(0.4);
                meteor.setPosition(pos.x, pos.y, pos.z);
                meteor.shoot(this, 90, 0, 0.0F, 0.0f, 0);
                ModUtils.setEntityVelocity(meteor, vel);
                meteor.setTravelRange(20f);
                world.spawnEntity(meteor);
            }, tick);
        }
        addEvent(() -> EntityChaosKnight.super.setSwingingArms(false), 30);
        addEvent(() -> {
            world.setEntityState(this, ModUtils.THIRD_PARTICLE_BYTE);
            this.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0f, 1.0f * ModRandom.getFloat(0.2f));
        }, 12);
    };

    public EntityChaosKnight(World worldIn) {
        super(worldIn);
        // Using this attribute to teleport the knight back if it falls off the tower it spawns on
        this.setImmovable(true);
        this.setSize(1.5f, 3.0f);
        this.healthScaledAttackFactor = 0.2;
        this.experienceValue = ModEntities.BOSS_EXPERIENCE;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        if (world.isRemote || this.ticksExisted % 5 != 0) {
            return;
        }

        boolean hasGround = false;
        for (int i = 0; i > -10; i--) {
            if (!world.isAirBlock(getPosition().add(new BlockPos(0, i, 0)))) {
                hasGround = true;
            }
        }

        if (!hasGround && this.motionY < -1) {
            this.setImmovable(true);
        } else if (this.isImmovable()) {
            this.setImmovable(false);
        }
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceFactor, boolean strafingBackwards) {
        float healthRatio = this.getHealth() / this.getMaxHealth();
        setSwingingArms(true);
        double distance = Math.sqrt(distanceFactor);
        List<Consumer<EntityLivingBase>> attacks = new ArrayList<Consumer<EntityLivingBase>>(Arrays.asList(sideSwipe, leapSlam, dash, spinSlash, summonMeteors));
        double[] weights = {
                (1 - (distance / 10)) * (prevAttack != sideSwipe ? 1.5 : 1.0), // More likely at closer range
                healthRatio < 0.75 ? 0.2 + 0.04 * distance : 0, // Most likely as longer range, and only appears below 75% health
                healthRatio < 0.5 ? 0.2 + 0.04 * distance : 0, // Only appears below 50% health
                0.5 - (prevAttack == spinSlash ? 0.3 : 0.0), // A powerful move that shouldn't happen too often in a row.
                prevAttack == summonMeteors || healthRatio > 0.4 ? 0.0 : (1 - healthRatio) // Meteor move becomes more common with less health
        };

        prevAttack = ModRandom.choice(attacks, rand, weights).next();
        prevAttack.accept(target);
        return prevAttack == sideSwipe || prevAttack == summonMeteors ? 50 : 90 - (int) (10 * (1 - healthRatio));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(9f);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30f);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(450);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (amount > 0.0F && this.canBlockDamageSource(source)) {
            this.damageShield(amount);
            amount = 0.0F;

            if (!source.isProjectile()) {
                Entity entity = source.getImmediateSource();

                if (entity instanceof EntityLivingBase) {
                    this.blockUsingShield((EntityLivingBase) entity);
                }
            }
            this.playSound(SoundsHandler.ENTITY_CHAOS_KNIGHT_BLOCK, 1.0f, 0.9f + ModRandom.getFloat(0.2f));

            return false;
        }
        return super.attackEntityFrom(source, amount);
    }

    private boolean canBlockDamageSource(DamageSource damageSourceIn) {
        if (!damageSourceIn.isUnblockable() && !this.isSwingingArms()) {
            Vec3d vec3d = damageSourceIn.getDamageLocation();

            if (vec3d != null) {
                Vec3d vec3d1 = this.getLook(1.0F);
                Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(this.posX, this.posY, this.posZ)).normalize();
                vec3d2 = new Vec3d(vec3d2.x, 0.0D, vec3d2.z);

                if (vec3d2.dotProduct(vec3d1) < 0.0D) {
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<EntityChaosKnight>(this, 1.0f, 60, 15, 0.5f));
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            for (int r = 1; r < 3; r++) {
                ModUtils.circleCallback(r, r * 20, (pos) -> {
                    pos = new Vec3d(pos.x, 0, pos.y);
                    ParticleManager.spawnSplit(world, pos.add(this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 0, 0))).add(ModUtils.yVec(-1.5f))), ModColors.RED, pos.scale(0.1f).add(ModUtils.yVec(0.05f)));
                });
            }
        } else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            if (chargeDir != null) {
                Vec3d particleVel = chargeDir.subtract(getPositionVector()).normalize().scale(0.5);
                ModUtils.lineCallback(getPositionVector(), chargeDir, 20, (vec, i) -> {
                    ModUtils.performNTimes(10, (j) -> {
                        ParticleManager.spawnSplit(world, vec.add(ModRandom.randVec().scale(dashRadius * 2)), ModColors.RED, particleVel.add(ModRandom.randVec().scale(0.2f)));
                        ParticleManager.spawnCustomSmoke(world, vec.add(ModRandom.randVec().scale(dashRadius * 2)), ModColors.GREY, particleVel.add(ModRandom.randVec().scale(0.2f)));
                        Vec3d flamePos = vec.add(ModRandom.randVec().scale(dashRadius * 2));
                        Vec3d flameVel = particleVel.add(ModRandom.randVec().scale(0.2f));
                        world.spawnParticle(EnumParticleTypes.FLAME, flamePos.x, flamePos.y, flamePos.z, flameVel.x, flameVel.y, flameVel.z);
                    });
                });
                this.chargeDir = null; // So that the lazer doesn't render anymore
            }
        } else if (id == ModUtils.THIRD_PARTICLE_BYTE) {
            ModUtils.circleCallback(2, 50, (pos) -> {
                pos = new Vec3d(pos.x, 0, pos.y);
                ParticleManager.spawnDust(world, pos.add(this.getPositionVector()).add(ModUtils.yVec(5)), ModColors.RED, pos.normalize().scale(0.3).add(ModUtils.yVec(0.1)), ModRandom.range(20, 30));
            });
        }
        super.handleStatusUpdate(id);
    }

    @Override
    public void onStopLeaping() {
        ModUtils.handleAreaImpact(3, (e) -> this.getAttack() * 1.5f, this, this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 0, 0))), ModDamageSource.causeElementalExplosionDamage(this, getElement()));
        this.playSound(SoundEvents.EVOCATION_ILLAGER_CAST_SPELL, 1.0f, 1.0f + ModRandom.getFloat(0.1f));
        this.world.setEntityState(this, ModUtils.PARTICLE_BYTE);
    }

    // For rendering the lazer
    @SideOnly(Side.CLIENT)
    public Vec3d getLazerPosition() {
        return this.chargeDir;
    }

    @Override
    public void setRenderDirection(Vec3d lazerDir) {
        this.chargeDir = lazerDir;
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        if (!world.isRemote && this.dimension == ModDimensions.NEXUS.getId() && this.getLevel() > 0) {
            // Spawn portal entity
            Vec3d origin = this.getInitialPosition();
            EntityCrimsonPortalSpawn spawner = new EntityCrimsonPortalSpawn(world, origin.x, origin.y, origin.z);
            world.spawnEntity(spawner);

            // Spawn nexus teleporters
            BlockPos upperTeleporterPos = new BlockPos(origin).east(14).down().north();
            BlockPos lowerTeleporterPos = upperTeleporterPos.add(0, -81, -2);
            new WorldGenNexusTeleporter(new Vec3d(-1, -83, 2)).generate(world, rand, upperTeleporterPos, Rotation.NONE);
            new WorldGenNexusTeleporter(new Vec3d(-8, 81, 3)).generate(world, rand, lowerTeleporterPos, Rotation.CLOCKWISE_90);
        }
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
    public void addTrackingPlayer(EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsHandler.ENTITY_CHAOS_KNIGHT_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsHandler.ENTITY_CHAOS_KNIGHT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsHandler.ENTITY_CHAOS_KNIGHT_HURT;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void initAnimation() {
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    }
}
