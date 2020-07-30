package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.AIFlyingSupport;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.projectile.EntityHealerOrb;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMaelstromHealer extends EntityMaelstromMob {
    Vec3d acceleration = Vec3d.ZERO;
    protected static final DataParameter<Boolean> FLYING = EntityDataManager.<Boolean>createKey(EntityMaelstromHealer.class, DataSerializers.BOOLEAN);
    private EntityAIBase flyingAI = new AIFlyingSupport(this, 1.2f, 3.5f, 10f, 60);
    private float timeSinceNoTarget = 0;

    public EntityMaelstromHealer(World worldIn) {
        super(worldIn);
        this.setSize(0.9f, 2.0f);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        Vec3d motion = new Vec3d(this.motionX, this.motionY, this.motionZ);
        this.acceleration = motion.scale(0.1).add(this.acceleration.scale(0.9));

        if (this.world.isRemote) {
            return;
        }

        // Switch to flying mode
        if (!this.isFlying() && ((!this.onGround && ModUtils.isAirBelow(world, getPosition(), 4)) || this.getAttackTarget() != null)) {
            this.setFlying(true);
            this.tasks.addTask(4, flyingAI);
            this.moveHelper = new FlyingMoveHelper(this);
            this.navigator = new PathNavigateFlying(this, world);
            ModBBAnimations.animation(this, "healer.fly", false);
        }

        // Switch to ground mode
        if (this.isFlying() && !ModUtils.isAirBelow(world, getPosition(), 4) && this.timeSinceNoTarget > 200) {
            this.setFlying(false);
            this.tasks.removeTask(flyingAI);
            this.moveHelper = new EntityMoveHelper(this);
            this.navigator = new PathNavigateGround(this, world);
            ModBBAnimations.animation(this, "healer.fly", true);
            ModBBAnimations.animation(this, "healer.fold_wing", false);
        }

        if (this.getAttackTarget() == null) {
            this.timeSinceNoTarget++;
        }

        if (rand.nextInt(20) == 0) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
    }

    @Override
    public void setAttackTarget(EntityLivingBase entity) {
        super.setAttackTarget(entity);
        if (entity != null) {
            this.timeSinceNoTarget = 0;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            if (this.getElement().equals(Element.NONE)) {
                ParticleManager.spawnMaelstromPotionParticle(world, rand, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), false);
            }

            ParticleManager.spawnEffect(world, this.getPositionVector().add(ModRandom.randVec()).add(ModUtils.yVec(1)), getElement().particleColor);
        } else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            ParticleManager.spawnSwirl2(world, this.getPositionVector(), ModColors.PURPLE, Vec3d.ZERO);
        }
        super.handleStatusUpdate(id);
    }

    public Vec3d getAcceleration() {
        return this.isFlying() ? this.acceleration : Vec3d.ZERO;
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isFlying()) {
            if (this.isInWater()) {
                this.moveRelative(strafe, vertical, forward, 0.02F);
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.800000011920929D;
                this.motionY *= 0.800000011920929D;
                this.motionZ *= 0.800000011920929D;
            } else if (this.isInLava()) {
                this.moveRelative(strafe, vertical, forward, 0.02F);
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.5D;
                this.motionY *= 0.5D;
                this.motionZ *= 0.5D;
            } else {
                float f = 0.91F;

                if (this.onGround) {
                    BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                    IBlockState underState = this.world.getBlockState(underPos);
                    f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
                }

                float f1 = 0.16277136F / (f * f * f);
                this.moveRelative(strafe, vertical, forward, this.onGround ? 0.1F * f1 : 0.02F);
                f = 0.91F;

                if (this.onGround) {
                    BlockPos underPos = new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.getEntityBoundingBox().minY) - 1, MathHelper.floor(this.posZ));
                    IBlockState underState = this.world.getBlockState(underPos);
                    f = underState.getBlock().getSlipperiness(underState, this.world, underPos, this) * 0.91F;
                }

                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= f;
                this.motionY *= f;
                this.motionZ *= f;
            }

            this.prevLimbSwingAmount = this.limbSwingAmount;
            double d1 = this.posX - this.prevPosX;
            double d0 = this.posZ - this.prevPosZ;
            float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;

            if (f2 > 1.0F) {
                f2 = 1.0F;
            }

            this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
            this.limbSwing += this.limbSwingAmount;
        } else {
            super.travel(strafe, vertical, forward);
        }
    }

    public boolean isFlying() {
        return this.dataManager == null ? false : this.dataManager.get(FLYING);
    }

    protected void setFlying(boolean immovable) {
        this.dataManager.set(FLYING, immovable);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FLYING, Boolean.valueOf(false));
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        ModBBAnimations.animation(this, "healer.summon_orb", false);
        this.addEvent(() -> {
            EntityHealerOrb orb = new EntityHealerOrb(world, this, target);
            world.spawnEntity(orb);
            world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
        }, 10);
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
        return SoundsHandler.ENTITY_SHADE_DEATH;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }
}
