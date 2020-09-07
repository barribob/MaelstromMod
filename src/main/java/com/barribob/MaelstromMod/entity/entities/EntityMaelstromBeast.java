package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.projectile.*;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class EntityMaelstromBeast extends EntityMaelstromMob implements IAttack {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));
    private Consumer<EntityLivingBase> attack;

    public EntityMaelstromBeast(World worldIn) {
        super(worldIn);
        this.healthScaledAttackFactor = 0.2;
        this.setSize(1.4f, 2.5f);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 1.25f, 50, 30, 0.5f, 10f));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote && this.isRaged()) {
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
        }

        if(!world.isRemote && this.isLeaping() && this.getAttackTarget() != null &&
                this.getDistanceSq(this.getAttackTarget()) < Math.pow(4, 2)){
            this.setLeaping(false);
            onStopLeaping();
        }

        if(!world.isRemote && this.isLeaping()) {
            AxisAlignedBB box = getEntityBoundingBox().grow(0.25, 0.12, 0.25).offset(0, 0.12, 0);
            ModUtils.destroyBlocksInAABB(box, world, this);
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            ParticleManager.spawnEffect(world, getPositionVector().add(ModRandom.randVec().scale(2)).add(ModUtils.yVec(this.getEyeHeight())), ModColors.RED);
        }
        super.handleStatusUpdate(id);
    }

    public boolean isRaged() {
        return this.getHealth() <= getMobConfig().getDouble("second_phase_hp");
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {

        if(target.posY - posY > 3)
        {
            spray.accept(target);
            return 50;
        }
        else if(distanceSq > Math.pow(12, 2)) {
            leap.accept(target);
            return 50;
        }

        List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(swipe, roar, quake));
        double[] weights = {
                Math.sqrt(Math.max(0, Math.pow(5, 2) - distanceSq)),
                attack != roar ? 0.5 : 0,
                1};

        attack = ModRandom.choice(attacks, rand, weights).next();
        attack.accept(target);
        return 50;
    }

    Runnable spawnQuake = () -> {
        Projectile projectile = this.isRaged() ?
                new ProjectileBoneQuake(world, this, this.getAttack()) :
                new ProjectileBeastQuake(world, this, this.getAttack());

        Vec3d projectileOffset = ModUtils.getRelativeOffset(this, new Vec3d(2, -2, 0));
        Vec3d forwardPos = ModUtils.getRelativeOffset(this, new Vec3d(3, -2, 0)).add(getPositionEyes(1));
        ModUtils.throwProjectile(this, forwardPos, projectile, 0, 0.8f, projectileOffset);
    };

    private final Consumer<EntityLivingBase> quake = target -> {
        ModBBAnimations.animation(this, "beast.quake", false);
        ModUtils.leapTowards(this, target.getPositionVector(), 0, 0.5f);
        addEvent(spawnQuake, 20);
    };

    @Override
    public void onStopLeaping() {
        ModBBAnimations.animation(this, "beast.leap", true);

        if(getAttackTarget() != null && getAttackTarget().getDistanceSq(this) < Math.pow(12, 2)) {
            ModBBAnimations.animation(this, "beast.slam", false);
            addEvent(spawnQuake, 4);
        }
        else {
            ModBBAnimations.animation(this, "beast.reset", false);
        }
    }

    private final Consumer<EntityLivingBase> swipe = target -> {
        ModBBAnimations.animation(this, "beast.swipe", false);

        addEvent(() -> {
            Vec3d offset = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(2, 0, 0)));
            Vec3d offset2 = this.getPositionVector().add(ModUtils.getRelativeOffset(this, new Vec3d(1, 0, 1)));
            DamageSource source = ModDamageSource.builder()
                    .type(ModDamageSource.MOB)
                    .directEntity(this)
                    .element(this.getElement())
                    .stoppedByArmorNotShields()
                    .disablesShields().build();

            ModUtils.handleAreaImpact(2, (e) -> this.getAttack(), this, offset, source, 1, 0, false);
            ModUtils.handleAreaImpact(2, (e) -> this.getAttack(), this, offset2, source, 1, 0, false);

            double width = getMobConfig().getDouble("swipe_width");

            ModUtils.destroyBlocksInAABB(new AxisAlignedBB(getPosition())
                    .grow(width, 1, width)
                    .offset(ModUtils.getRelativeOffset(this, new Vec3d(1, 1, 0))), world, this);

            if (EntityMaelstromBeast.this.isRaged()) {
                ModUtils.performNTimes(8, (i) -> spawnBone(world, offset.add(ModRandom.randVec().scale(3)), EntityMaelstromBeast.this));
            }
            this.playSound(SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, 1.0F, 0.4f + ModRandom.getFloat(0.1f));
        }, 15);
    };

    private final Consumer<EntityLivingBase> roar = target -> {
        ModBBAnimations.animation(this, "beast.roar", false);

        addEvent(() -> {
            if (EntityMaelstromBeast.this.isRaged()) {
                ModUtils.spawnMob(world, getPosition(), getLevel(), getMobConfig().getConfig("spawning_algorithm"));
            } else {
                ModUtils.handleAreaImpact(20, (e) -> {
                    if (e instanceof EntityLivingBase) {
                        ((EntityLivingBase) e).addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 120, 3));
                    }
                    return 0.0f;
                }, this, this.getPositionVector(), ModDamageSource.MAELSTROM_DAMAGE);
            }
            this.playSound(SoundEvents.ENTITY_ENDERDRAGON_GROWL, 1.0F, 0.9F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        }, 12);
    };

    public final Consumer<EntityLivingBase> spray = target -> {
        ModUtils.leapTowards(this, target.getPositionVector(), 0, 1.6f);
        ModBBAnimations.animation(this, "beast.leap", false);

        addEvent(() -> {
            ModBBAnimations.animation(this, "beast.leap", true);
            ModBBAnimations.animation(this, "beast.slam", false);

            Vec3d targetPos = target.getPositionEyes(1);
            Vec3d dir = getPositionEyes(1).subtract(targetPos);
            Vec3d axis = ModUtils.rotateVector2(dir.crossProduct(ModUtils.Y_AXIS), dir, 90).normalize().scale(5);

            ModUtils.lineCallback(targetPos.add(axis), targetPos.subtract(axis), 5, (pos, i) -> {
                Projectile projectile = new ProjectileBeastFireball(world, this, this.getAttack());
                ModUtils.throwProjectile(this, pos, projectile, 4, 0.7f);
            });

        }, 30);
    };

    private final Consumer<EntityLivingBase> leap = target -> {
        ModBBAnimations.animation(this, "beast.leap", false);

        addEvent(() -> {
            ModUtils.leapTowards(this, target.getPositionVector(), 1.8f, (float) 0.75);
            setLeaping(true);
        }, 16);
    };

    public static void spawnBone(World world, Vec3d pos, EntityLeveledMob entity) {
        if (!world.isRemote) {
            ProjectileBone projectile = new ProjectileBone(world, entity, entity.getAttack() * 0.5f);
            projectile.setPosition(pos.x, pos.y + 1.5, pos.z);
            double xDir = ModRandom.getFloat(0.1f);
            double yDir = 1 + ModRandom.getFloat(0.1f);
            double zDir = ModRandom.getFloat(0.1f);
            projectile.shoot(xDir, yDir, zDir, 0.5f, 0.5f);
            world.spawnEntity(projectile);
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

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.ENTITY_POLAR_BEAR_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.BEAST;
    }

    @Override
    protected float getSoundPitch() {
        return super.getSoundPitch() * 0.8f;
    }

    @Override
    protected float getSoundVolume() {
        return 0.6F;
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    }
}
