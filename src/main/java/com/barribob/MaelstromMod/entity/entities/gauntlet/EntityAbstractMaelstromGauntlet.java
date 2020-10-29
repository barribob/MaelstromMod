package com.barribob.MaelstromMod.entity.entities.gauntlet;

import com.barribob.MaelstromMod.entity.ai.*;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.entity.entities.EntityMaelstromMob;
import com.barribob.MaelstromMod.entity.util.DirectionalRender;
import com.barribob.MaelstromMod.entity.util.IPitch;
import com.barribob.MaelstromMod.init.ModDimensions;
import com.barribob.MaelstromMod.renderer.ITarget;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import com.barribob.MaelstromMod.util.handlers.SoundsHandler;
import com.barribob.MaelstromMod.world.dimension.crimson_kingdom.WorldGenGauntletSpike;
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderDragon;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntitySenses;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ReportedException;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public abstract class EntityAbstractMaelstromGauntlet extends EntityMaelstromMob implements IEntityMultiPart, DirectionalRender, ITarget, IPitch {
    // We keep track of the look ourselves because minecraft's look is clamped
    protected static final DataParameter<Float> LOOK = EntityDataManager.createKey(EntityLeveledMob.class, DataSerializers.FLOAT);
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.RED, BossInfo.Overlay.NOTCHED_6));
    private final MultiPartEntityPart[] hitboxParts;
    private final float boxSize = 0.8f;
    private final MultiPartEntityPart eye = new MultiPartEntityPart(this, "eye", 1.2f, 1.2f);
    private final MultiPartEntityPart behindEye = new MultiPartEntityPart(this, "behindEye", 1.0f, 1.0f);
    private final MultiPartEntityPart bottomPalm = new MultiPartEntityPart(this, "bottomPalm", 1.2f, 1.2f);
    private final MultiPartEntityPart upLeftPalm = new MultiPartEntityPart(this, "upLeftPalm", boxSize, boxSize);
    private final MultiPartEntityPart upRightPalm = new MultiPartEntityPart(this, "upRightPalm", boxSize, boxSize);
    private final MultiPartEntityPart rightPalm = new MultiPartEntityPart(this, "rightPalm", boxSize, boxSize);
    private final MultiPartEntityPart leftPalm = new MultiPartEntityPart(this, "leftPalm", boxSize, boxSize);
    private final MultiPartEntityPart fingers = new MultiPartEntityPart(this, "fingers", 1.2f, 1.2f);
    protected final MultiPartEntityPart fist = new MultiPartEntityPart(this, "fist", 0, 0);
    private IGauntletAction currentAction;
    protected static final byte stopLazerByte = 39;
    private final double punchImpactSize = getMobConfig().getDouble("punch_impact_size");
    private @Nullable MovementTracker movement;
    IGauntletAction defendAction = new DefendAction(this);

    // Lazer state variables
    private Vec3d renderLazerPos;
    private Vec3d prevRenderLazerPos;

    // Used to filter damage from parts
    private boolean damageFromEye;

    // Custom entity see ai
    private final EntitySenses senses = new GauntletEntitySenses(this);

    public final Consumer<Vec3d> punchAtPos = (target) -> {
        currentAction = new PunchAction("gauntlet.punch", () -> target, () -> {}, this, fist);
        currentAction.doAction();
        for (int i = 0; i < 12; i++) {
            this.addEvent(() -> ModUtils.faceDirection(this, target, 15), i);
        }
    };

    public EntityAbstractMaelstromGauntlet(World worldIn) {
        super(worldIn);
        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
        this.hitboxParts = new MultiPartEntityPart[]{eye, behindEye, bottomPalm, upLeftPalm, upRightPalm, rightPalm, leftPalm, fingers, fist};
        this.setSize(2, 4);
        this.noClip = true;
        this.isImmuneToFire = true;
        this.healthScaledAttackFactor = 0.1;
        if(!world.isRemote) {
            this.initGauntletAI();
        }
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.26f);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        ModUtils.removeTaskOfType(this.tasks, EntityAIWanderWithGroup.class);
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.getEntityBoundingBox().grow(targetDistance);
    }

    private void initGauntletAI() {
        float attackDistance = (float) this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        this.tasks.addTask(4, new
                AIAerialTimedAttack(this, attackDistance, 20, 20,
                new GauntletAttackInitiator( 60, this::startAttack, this::defendAttack)));
        this.tasks.addTask(7, new AiFistWander(this, punchAtPos, 80, 8));
    }

    public final IGauntletAction startAttack(EntityLivingBase target) {
        float distanceSq = (float) getDistanceSq(target);
        this.currentAction = getNextAttack(target, distanceSq, currentAction);
        this.currentAction.doAction();
        return currentAction;
    }

    protected abstract IGauntletAction getNextAttack(EntityLivingBase target, float distanceSq, IGauntletAction previousAction);

    public final @Nullable IGauntletAction defendAttack(EntityLivingBase target) {
        if (seesDanger(movement, target)) {
            currentAction = defendAction;
            defendAction.doAction();
            return defendAction;
        }
        return null;
    }

    @Override
    public void setAttackTarget(@Nullable EntityLivingBase entity) {
        if(entity != null && (movement == null || movement.entity != entity)) {
            movement = new MovementTracker(entity, 5);
        } else if (entity == null) {
            movement = null;
        }
        super.setAttackTarget(entity);
    }

    @Override
    public void onEntityUpdate() {
        if(movement != null) movement.onUpdate();
        super.onEntityUpdate();
    }

    private boolean seesDanger(@Nullable MovementTracker movementTracker, EntityLivingBase target) {
        if(movementTracker == null) return false;
        Vec3d targetMovement = movementTracker.getMovementOverTicks(5);
        double velocityTowardsThis = ModUtils.direction(target.getPositionVector(), getPositionVector())
                .dotProduct(targetMovement);
        System.out.println(velocityTowardsThis);
        return velocityTowardsThis > 3;
    }

    @Override
    public final boolean attackEntityFrom(DamageSource source, float amount) {
        if (!this.damageFromEye && !source.isUnblockable()) {
            return false;
        }
        this.damageFromEye = false;
        return super.attackEntityFrom(source, amount);
    }

    @Override
    public final boolean attackEntityFromPart(@Nonnull MultiPartEntityPart part, @Nonnull DamageSource source, float damage) {
        if (part == this.eye && (this.currentAction == null || !this.currentAction.isImmuneToDamage())) {
            this.damageFromEye = true;

            // Awaken the gauntlet
            if (damage > 0 && this.isImmovable()) {
                this.setImmovable(false);
            }

            return this.attackEntityFrom(source, damage);
        }

        if (damage > 0.0F && !source.isUnblockable()) {
            if (!source.isProjectile()) {
                Entity entity = source.getImmediateSource();

                if (entity instanceof EntityLivingBase) {
                    this.blockUsingShield((EntityLivingBase) entity);
                }
            }
            this.playSound(SoundEvents.ENTITY_BLAZE_HURT, 1.0f, 0.6f + ModRandom.getFloat(0.2f));

            return false;
        }

        return false;
    }

    @Override
    public final void onLivingUpdate() {
        bossInfo.setPercent(this.getHealth() / this.getMaxHealth());

        super.onLivingUpdate();
        Vec3d[] avec3d = new Vec3d[this.hitboxParts.length];
        for (int j = 0; j < this.hitboxParts.length; ++j) {
            avec3d[j] = new Vec3d(this.hitboxParts[j].posX, this.hitboxParts[j].posY, this.hitboxParts[j].posZ);
        }

        /*
         * Set the hitbox pieces based on the entity's rotation so that even large pitch rotations don't mess up the hitboxes
         */

        setHitboxPosition(fingers, new Vec3d(0, -1.5, 0));
        setHitboxPosition(behindEye, new Vec3d(-0.5, -0.3, 0));
        setHitboxPosition(eye, new Vec3d(0.5, -0.3, 0));
        setHitboxPosition(bottomPalm, new Vec3d(-0.4, 0.7, 0));
        setHitboxPosition(rightPalm, new Vec3d(0, 0, -0.7));
        setHitboxPosition(leftPalm, new Vec3d(0, 0, 0.7));
        setHitboxPosition(upRightPalm, new Vec3d(0, -1, -0.7));
        setHitboxPosition(upLeftPalm, new Vec3d(0, -1, 0.7));

        Vec3d fistPos = this.getPositionVector().subtract(ModUtils.yVec(0.5));
        ModUtils.setEntityPosition(fist, fistPos);

        for (int l = 0; l < this.hitboxParts.length; ++l) {
            this.hitboxParts[l].prevPosX = avec3d[l].x;
            this.hitboxParts[l].prevPosY = avec3d[l].y;
            this.hitboxParts[l].prevPosZ = avec3d[l].z;
        }

        if (!world.isRemote && currentAction != null) {
            currentAction.update();
        }
    }

    private void setHitboxPosition(Entity entity, Vec3d offset) {
        Vec3d lookVec = ModUtils.getLookVec(this.getPitch(), this.renderYawOffset);
        Vec3d center = this.getPositionVector().add(ModUtils.yVec(1.3));

        Vec3d position = center.subtract(ModUtils.Y_AXIS
                .scale(this.fingers.getEntityBoundingBox().getAverageEdgeLength() * 0.5))
                .add(ModUtils.getAxisOffset(lookVec, offset));
        ModUtils.setEntityPosition(entity, position);
    }

    @Override
    public final void onUpdate() {
        Vec3d vel = ModUtils.getEntityVelocity(this);
        double speed = vel.lengthVector();

        super.onUpdate();

        boolean motionStopped = (motionX == 0 && vel.x != 0) || (motionY == 0 && vel.y != 0) || (motionZ == 0 && vel.z != 0);
        if(motionStopped && this.currentAction != null && this.currentAction.shouldExplodeUponImpact() && !world.isRemote && speed > 0.55f) {
            onBlockPhysicalImpact(speed);
        }

        if (this.isImmovable()) {
            this.setRotation(180, 0);
            this.setRotationYawHead(180);
        }
    }

    private void onBlockPhysicalImpact(double velocity) {
        Vec3d pos = getPositionEyes(1);
        DamageSource source = ModDamageSource.builder()
                .directEntity(this)
                .element(getElement())
                .stoppedByArmorNotShields()
                .type(ModDamageSource.MOB)
                .build();

        boolean flag = net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(world, this);
        world.newExplosion(this, pos.x, pos.y, pos.z, (float) (velocity * 0.75f * punchImpactSize), false, flag);
        ModUtils.handleAreaImpact((float) (velocity * punchImpactSize), e -> getAttack(), this, pos, source);
    }

    /**
     * Immovability doubles as the gauntlet not being "awakened" or active
     */
    @Override
    protected final void setImmovable(boolean immovable) {
        if (this.isImmovable() && !immovable) {
            this.initGauntletAI(); // Start gauntlet attacks and movements after becoming mobile
        } else if (immovable) {
            ModUtils.removeTaskOfType(tasks, AIAerialTimedAttack.class);
            ModUtils.removeTaskOfType(tasks, AiFistWander.class);
        }
        super.setImmovable(immovable);
    }

    @Override
    public final void doRender(RenderManager renderManager, double x, double y, double z, float entityYaw, float partialTicks) {
        if (this.renderLazerPos != null) {
            // This sort of jenky way of binding the wrong texture to the original guardian beam creates quite a nice particle beam visual
            renderManager.renderEngine.bindTexture(RenderDragon.ENDERCRYSTAL_BEAM_TEXTURES);
            // We must interpolate between positions to make the move smoothly
            Vec3d interpolatedPos = renderLazerPos.subtract(this.prevRenderLazerPos).scale(partialTicks).add(prevRenderLazerPos);
            RenderUtils.drawBeam(renderManager, this.getPositionEyes(1), interpolatedPos, new Vec3d(x, y, z), ModColors.RED, this, partialTicks);
        }
        super.doRender(renderManager, x, y, z, entityYaw, partialTicks);
    }

    @Override
    public final float getEyeHeight() {
        return 1.6f;
    }

    @Override
    public final void setPitch(Vec3d look) {
        float prevLook = this.getPitch();
        float newLook = (float) ModUtils.toPitch(look);
        float deltaLook = 5;
        float clampedLook = MathHelper.clamp(newLook, prevLook - deltaLook, prevLook + deltaLook);
        this.dataManager.set(LOOK, clampedLook);
    }

    @Override
    public final float getPitch() {
        return this.dataManager == null ? 0 : this.dataManager.get(LOOK);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == stopLazerByte) {
            this.renderLazerPos = null;
        } else if (id == ModUtils.PARTICLE_BYTE) {
            // Render particles in a sucking in motion
            for (int i = 0; i < 5; i++) {
                Vec3d lookVec = ModUtils.getLookVec(this.getPitch(), this.renderYawOffset);
                Vec3d randOffset = ModUtils.rotateVector2(lookVec, lookVec.crossProduct(ModUtils.Y_AXIS), ModRandom.range(-70, 70));
                randOffset = ModUtils.rotateVector2(randOffset, lookVec, ModRandom.range(0, 360)).scale(1.5f);
                Vec3d velocity = Vec3d.ZERO.subtract(randOffset).normalize().scale(0.15f).add(new Vec3d(this.motionX, this.motionY, this.motionZ));
                Vec3d particlePos = this.getPositionEyes(1).add(ModUtils.getAxisOffset(lookVec, new Vec3d(1, 0, 0))).add(randOffset);
                ParticleManager.spawnDust(world, particlePos, ModColors.RED, velocity, ModRandom.range(5, 7));
            }
        } else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            // Render particles in some weird circular trig fashion
            ModUtils.circleCallback(2, 16, (pos) -> {
                pos = new Vec3d(pos.x, 0, pos.y).add(this.getPositionVector());
                double y = Math.cos(pos.x + pos.z);
                ParticleManager.spawnSplit(world, pos.add(ModUtils.yVec(y)), ModColors.PURPLE, ModUtils.yVec(-y * 0.1));
            });
        }
        super.handleStatusUpdate(id);
    }

    @Override
    public void onDeath(DamageSource cause) {
        if (!world.isRemote && this.getLevel() > 0 && this.dimension == ModDimensions.CRIMSON_KINGDOM.getId()) {

            for (int i = 0; i < 15; i++) {
                world.newExplosion(this, this.posX, this.posY + i * 2, this.posZ, 2, false, false);
            }

            new WorldGenGauntletSpike().generate(world, this.getRNG(), this.getPosition().add(new BlockPos(-3, 0, -3)));
            super.onDeath(cause);
        }
    }

    @Override
    public final void travel(float strafe, float vertical, float forward) {
        ModUtils.aerialTravel(this, strafe, vertical, forward);
    }

    /**
     * Add a bit of brightness to the entity, because otherwise it looks pretty black
     */
    @Override
    public int getBrightnessForRender() {
        return Math.min(super.getBrightnessForRender() + 60, 200);
    }

    @Override
    protected void entityInit() {
        this.dataManager.register(LOOK, 0f);
        super.entityInit();
    }

    @Override
    @Nonnull
    public EntitySenses getEntitySenses() {
        return this.senses;
    }

    @Override
    @Nonnull
    public World getWorld() {
        return world;
    }

    @Override
    public Entity[] getParts() {
        return this.hitboxParts;
    }

    @Override
    protected boolean canDespawn() {
        return false;
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
    public void attackEntityWithRangedAttack(@Nullable EntityLivingBase target, float distanceFactor) {
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    /**
     * This is overriden because we do want the main hitbox to clip with blocks while still not clipping with anything else
     */
    @Override
    public final void move(@Nonnull MoverType type, double x, double y, double z) {
        this.world.profiler.startSection("move");

        if (this.isInWeb) {
            this.isInWeb = false;
            x *= 0.25D;
            y *= 0.05000000074505806D;
            z *= 0.25D;
            this.motionX = 0.0D;
            this.motionY = 0.0D;
            this.motionZ = 0.0D;
        }

        double d2 = x;
        double d3 = y;
        double d4 = z;

        List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().expand(x, y, z));
        AxisAlignedBB axisalignedbb = this.getEntityBoundingBox();

        if (y != 0.0D) {
            int k = 0;

            for (int l = list1.size(); k < l; ++k) {
                y = list1.get(k).calculateYOffset(this.getEntityBoundingBox(), y);
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));
        }

        if (x != 0.0D) {
            int j5 = 0;

            for (int l5 = list1.size(); j5 < l5; ++j5) {
                x = list1.get(j5).calculateXOffset(this.getEntityBoundingBox(), x);
            }

            if (x != 0.0D) {
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(x, 0.0D, 0.0D));
            }
        }

        if (z != 0.0D) {
            int k5 = 0;

            for (int i6 = list1.size(); k5 < i6; ++k5) {
                z = list1.get(k5).calculateZOffset(this.getEntityBoundingBox(), z);
            }

            if (z != 0.0D) {
                this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, 0.0D, z));
            }
        }

        boolean flag = this.onGround || d3 != y && d3 < 0.0D;

        if (this.stepHeight > 0.0F && flag && (d2 != x || d4 != z)) {
            double d14 = x;
            double d6 = y;
            double d7 = z;
            AxisAlignedBB axisalignedbb1 = this.getEntityBoundingBox();
            this.setEntityBoundingBox(axisalignedbb);
            y = this.stepHeight;
            List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, this.getEntityBoundingBox().expand(d2, y, d4));
            AxisAlignedBB axisalignedbb2 = this.getEntityBoundingBox();
            AxisAlignedBB axisalignedbb3 = axisalignedbb2.expand(d2, 0.0D, d4);
            double d8 = y;
            int j1 = 0;

            for (int k1 = list.size(); j1 < k1; ++j1) {
                d8 = list.get(j1).calculateYOffset(axisalignedbb3, d8);
            }

            axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
            double d18 = d2;
            int l1 = 0;

            for (int i2 = list.size(); l1 < i2; ++l1) {
                d18 = list.get(l1).calculateXOffset(axisalignedbb2, d18);
            }

            axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
            double d19 = d4;
            int j2 = 0;

            for (int k2 = list.size(); j2 < k2; ++j2) {
                d19 = list.get(j2).calculateZOffset(axisalignedbb2, d19);
            }

            axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
            AxisAlignedBB axisalignedbb4 = this.getEntityBoundingBox();
            double d20 = y;
            int l2 = 0;

            for (int i3 = list.size(); l2 < i3; ++l2) {
                d20 = list.get(l2).calculateYOffset(axisalignedbb4, d20);
            }

            axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
            double d21 = d2;
            int j3 = 0;

            for (int k3 = list.size(); j3 < k3; ++j3) {
                d21 = list.get(j3).calculateXOffset(axisalignedbb4, d21);
            }

            axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
            double d22 = d4;
            int l3 = 0;

            for (int i4 = list.size(); l3 < i4; ++l3) {
                d22 = list.get(l3).calculateZOffset(axisalignedbb4, d22);
            }

            axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
            double d23 = d18 * d18 + d19 * d19;
            double d9 = d21 * d21 + d22 * d22;

            if (d23 > d9) {
                x = d18;
                z = d19;
                y = -d8;
                this.setEntityBoundingBox(axisalignedbb2);
            } else {
                x = d21;
                z = d22;
                y = -d20;
                this.setEntityBoundingBox(axisalignedbb4);
            }

            int j4 = 0;

            for (int k4 = list.size(); j4 < k4; ++j4) {
                y = list.get(j4).calculateYOffset(this.getEntityBoundingBox(), y);
            }

            this.setEntityBoundingBox(this.getEntityBoundingBox().offset(0.0D, y, 0.0D));

            if (d14 * d14 + d7 * d7 >= x * x + z * z) {
                x = d14;
                y = d6;
                z = d7;
                this.setEntityBoundingBox(axisalignedbb1);
            }
        }

        this.world.profiler.endSection();
        this.world.profiler.startSection("rest");
        this.resetPositionToBB();
        this.collidedHorizontally = d2 != x || d4 != z;
        this.collidedVertically = d3 != y;
        this.onGround = this.collidedVertically && d3 < 0.0D;
        this.collided = this.collidedHorizontally || this.collidedVertically;
        int j6 = MathHelper.floor(this.posX);
        int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
        int k6 = MathHelper.floor(this.posZ);
        BlockPos blockpos = new BlockPos(j6, i1, k6);
        IBlockState iblockstate = this.world.getBlockState(blockpos);

        if (iblockstate.getMaterial() == Material.AIR) {
            BlockPos blockpos1 = blockpos.down();
            IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
            Block block1 = iblockstate1.getBlock();

            if (block1 instanceof BlockFence || block1 instanceof BlockWall || block1 instanceof BlockFenceGate) {
                iblockstate = iblockstate1;
                blockpos = blockpos1;
            }
        }

        this.updateFallState(y, this.onGround, iblockstate, blockpos);

        if (d2 != x) {
            this.motionX = 0.0D;
        }

        if (d4 != z) {
            this.motionZ = 0.0D;
        }

        Block block = iblockstate.getBlock();

        if (d3 != y) {
            block.onLanded(this.world, this);
        }

        try {
            this.doBlockCollisions();
        } catch (Throwable throwable) {
            CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
            CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
            this.addEntityCrashInfo(crashreportcategory);
            throw new ReportedException(crashreport);
        }

        this.world.profiler.endSection();
    }

    @Override
    public final void setRenderDirection(Vec3d dir) {
        if (this.renderLazerPos != null) {
            this.prevRenderLazerPos = this.renderLazerPos;
        } else {
            this.prevRenderLazerPos = dir;
        }
        this.renderLazerPos = dir;
    }

    @Override
    public void readEntityFromNBT(@Nonnull NBTTagCompound compound) {
        if (this.hasCustomName()) {
            this.bossInfo.setName(this.getDisplayName());
        }

        super.readEntityFromNBT(compound);
    }

    @Override
    public void setCustomNameTag(@Nonnull String name) {
        super.setCustomNameTag(name);
        this.bossInfo.setName(this.getDisplayName());
    }

    @Override
    public void addTrackingPlayer(@Nonnull EntityPlayerMP player) {
        super.addTrackingPlayer(player);
        this.bossInfo.addPlayer(player);
    }

    @Override
    public void removeTrackingPlayer(@Nonnull EntityPlayerMP player) {
        super.removeTrackingPlayer(player);
        this.bossInfo.removePlayer(player);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundsHandler.ENTITY_GAUNTLET_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundsHandler.ENTITY_GAUNTLET_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundsHandler.ENTITY_GAUNTLET_HURT;
    }

    @Override
    public Optional<Vec3d> getTarget() {
        return Optional.ofNullable(renderLazerPos);
    }
}
