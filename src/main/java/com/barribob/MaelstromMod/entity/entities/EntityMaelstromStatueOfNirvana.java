package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.ActionAerialTeleport;
import com.barribob.MaelstromMod.entity.action.ActionRayAttack;
import com.barribob.MaelstromMod.entity.action.ActionRingAttack;
import com.barribob.MaelstromMod.entity.action.ActionRuneAttack;
import com.barribob.MaelstromMod.entity.adjustment.MovingRuneAdjustment;
import com.barribob.MaelstromMod.entity.ai.AIAerialTimedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAIWanderWithGroup;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileHomingFlame;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromRune;
import com.barribob.MaelstromMod.entity.projectile.ProjectileStatueMaelstromMissile;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class EntityMaelstromStatueOfNirvana extends EntityMaelstromMob implements IAttack {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));
    private static boolean doTeleportNext;
    Consumer<EntityLivingBase> previousAttack;

    public EntityMaelstromStatueOfNirvana(World worldIn) {
        super(worldIn);
        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
        this.setSize(1.6f, 3.6f);
        this.healthScaledAttackFactor = 0.2;
        if(!world.isRemote) {
            initNirvanaAI();
        }
    }

    private void initNirvanaAI() {
        float attackDistance = (float) this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        this.tasks.addTask(4, new AIAerialTimedAttack<>(this, 1.0f, 80, attackDistance, 20, 0.4f, 30));
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.getEntityBoundingBox().grow(targetDistance);
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        ModUtils.removeTaskOfType(this.tasks, EntityAIWanderWithGroup.class);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(getHealth() / getMaxHealth());
        if (!world.isRemote) {
            world.setEntityState(this, ModUtils.PARTICLE_BYTE);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if (rand.nextInt(8) == 0 && amount > 1) {
            doTeleportNext = true;
        }

        return super.attackEntityFrom(source, amount);
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        boolean canSee = world.rayTraceBlocks(target.getPositionEyes(1), getPositionEyes(1), false, true, false) == null;

        List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(
                rayAttack, runeAttack, ringAttack, teleportAttack));
        int i = previousAttack == ringAttack ? 0 : 1;
        double[] weights = {1, 1, i, canSee ? 0 : 2};

        previousAttack = ModRandom.choice(attacks, rand, weights).next();
        if(doTeleportNext) {
            previousAttack = teleportAttack;
            doTeleportNext = false;
        }

        previousAttack.accept(target);

        return 50;
    }

    public Supplier<Projectile> maelstromFlame = () -> {
        ProjectileHomingFlame projectile = new ProjectileHomingFlame(world, this, this.getAttack());
        projectile.setNoGravity(true);
        projectile.setTravelRange(40);
        return projectile;
    };
    Supplier<Projectile> maelstromRune = () -> new ProjectileMaelstromRune(this.world, this, this.getAttack() * 2);
    Supplier<Projectile> maelstromMissile = () -> new ProjectileStatueMaelstromMissile(this.world, this, this.getAttack());

    private final Consumer<EntityLivingBase> teleportAttack = target -> new ActionAerialTeleport(ModColors.PURPLE).performAction(this, target);

    private final Consumer<EntityLivingBase> rayAttack = target -> {
        ModBBAnimations.animation(this, "statue.fireball", false);

        addEvent(() -> {
            new ActionRayAttack(maelstromMissile, 1.1f).performAction(this, target);
            this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, ModRandom.getFloat(0.2f) + 1.3f);
        }, 22);
    };

    private final Consumer<EntityLivingBase> runeAttack = target -> {
        ModBBAnimations.animation(this, "statue.runes", false);
        addEvent(() -> new ActionRuneAttack(maelstromRune, new MovingRuneAdjustment(target)).performAction(this, target), 12);
    };

    private final Consumer<EntityLivingBase> ringAttack = target -> {
        ModBBAnimations.animation(this, "statue.summon", false);
        addEvent(() -> new ActionRingAttack(maelstromFlame).performAction(this, target), 15);
        playSound(SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR, 2.5f, 1.0f + ModRandom.getFloat(0.2f));
    };

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            ParticleManager.spawnEffect(world, ModRandom.randVec().add(this.getPositionVector()), ModColors.PURPLE);
        } else if(id == ModUtils.SECOND_PARTICLE_BYTE) {
            ModUtils.performNTimes(3, i -> ModUtils.circleCallback(i * 0.5f + 1, 30, pos -> {
                ParticleManager.spawnSwirl(world, getPositionVector().add(pos), ModColors.PURPLE, Vec3d.ZERO, ModRandom.range(10, 15));
                ParticleManager.spawnSwirl(world,
                        getPositionVector().add(ModUtils.rotateVector2(pos, ModUtils.yVec(1), 90)),
                        ModColors.PURPLE, Vec3d.ZERO, ModRandom.range(10, 15));
            }));
        }

        super.handleStatusUpdate(id);
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
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_METAL_PLACE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_METAL_BREAK;
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GOLDEN_BOSS;
    }

    @Override
    protected float getManaExp() {
        return 0;
    }

    @Override
    public void attackEntityWithRangedAttack(@Nonnull EntityLivingBase target, float distanceFactor) {
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        ModUtils.aerialTravel(this, strafe, vertical, forward);
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
}
