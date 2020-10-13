package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.action.*;
import com.barribob.MaelstromMod.entity.ai.AIAerialTimedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAIWanderWithGroup;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.projectile.*;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.entity.adjustment.MovingRuneAdjustment;
import com.barribob.MaelstromMod.entity.adjustment.RandomRuneAdjustment;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
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
import java.util.stream.Collectors;

public class EntityGoldenBoss extends EntityMaelstromMob implements IAttack {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_6));
    protected static final DataParameter<Integer> ATTACK_COUNT = EntityDataManager.createKey(EntityLeveledMob.class, DataSerializers.VARINT);
    private static boolean doSummonNext;
    private static boolean doTeleportNext;

    public EntityGoldenBoss(World worldIn) {
        super(worldIn);
        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
        this.setSize(1.6f, 3.6f);
        this.healthScaledAttackFactor = 0.2;
        if(!world.isRemote) {
            initNirvanaAI();
        }
    }

    protected AxisAlignedBB getTargetableArea(double targetDistance) {
        return this.getEntityBoundingBox().grow(targetDistance);
    }

    Supplier<Projectile> goldenMissile = () -> new ProjectileGoldenMissile(world, this, this.getAttack());
    Supplier<Projectile> maelstromMissile = () -> new ProjectileStatueMaelstromMissile(world, this, this.getAttack());
    Supplier<Projectile> goldenRune = () -> new EntityLargeGoldenRune(this.world, this, this.getAttack() * 2);
    Supplier<Projectile> maelstromRune = () -> new ProjectileMaelstromRune(this.world, this, this.getAttack() * 2);
    Supplier<Projectile> maelstromOrGoldenMissile = () -> rand.nextInt(2) == 0 ? goldenMissile.get() : maelstromMissile.get();
    IAction goldenRayAction = new ActionRayAttack(goldenMissile, 1.1f);
    IAction maelstromRayAction = new ActionRayAttack(maelstromMissile, 1.1f);

    private final Consumer<EntityLivingBase> rayAttack = target -> {
        ModBBAnimations.animation(this, "statue.fireball", false);

        addEvent(() -> {
            goldenRayAction.performAction(this, target);
            this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, ModRandom.getFloat(0.2f) + 1.3f);
        }, 22);
    };

    private final Consumer<EntityLivingBase> secondPhaseRayAttack = target -> {
        ModBBAnimations.animation(this, "statue.fireball", false);

        addEvent(() -> {
            goldenRayAction.performAction(this, target);
            maelstromRayAction.performAction(this, target);
            this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, ModRandom.getFloat(0.2f) + 1.3f);
        }, 22);
    };

    private final Consumer<EntityLivingBase> runeAttack = target -> {
        ModBBAnimations.animation(this, "statue.runes", false);
        addEvent(() -> new ActionRuneAttack(goldenRune, new RandomRuneAdjustment(target)).performAction(this, target), 12);
    };

    private final Consumer<EntityLivingBase> secondPhaseRuneAttack = target -> {
        ModBBAnimations.animation(this, "statue.runes", false);
        addEvent(() -> new ActionRuneAttack(maelstromRune, new MovingRuneAdjustment(target)).performAction(this, target), 12);
    };

    private final Consumer<EntityLivingBase> spawnPillarAttack = target -> {
        ModBBAnimations.animation(this, "statue.summon", false);

        this.addEvent(() -> {
            for(int i = 0; i < getMobConfig().getInt("summoning_algorithm.mobs_per_spawn"); i++) {
                boolean findGround = !getMobConfig().getBoolean("pillar_can_be_summoned_in_air");
                BlockPos spawnCenter = findGround ? ModUtils.findGroundBelow(world, getPosition()) : getPosition();
                EntityLeveledMob mob = ModUtils.spawnMob(world, spawnCenter, this.getLevel(),
                        getMobConfig().getConfig("summoning_algorithm"),
                        findGround);
                if (mob != null) {
                    mob.setAttackTarget(target);
                    ModUtils.lineCallback(this.getPositionEyes(1), mob.getPositionVector(), 20, (pos, j) ->
                            Main.network.sendToAllTracking(new MessageModParticles(EnumModParticles.EFFECT, pos, Vec3d.ZERO, mob.getElement().particleColor), this));
                }
            }
        }, 15);
    };

    private final Consumer<EntityLivingBase> volleyAttack = target -> {
        ModBBAnimations.animation(this, "statue.volley", false);
        Supplier<Projectile> projectileSupplier = isSecondPhase() ? maelstromOrGoldenMissile : goldenMissile;
        new ActionVolley(projectileSupplier, 1.6f).performAction(this, target);
    };

    private final Consumer<EntityLivingBase> teleportAttack = target -> new ActionAerialTeleport(ModColors.YELLOW).performAction(this, target);

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    public void onDeath(DamageSource cause) {
        // Spawn the second half of the boss
        EntityMaelstromStatueOfNirvana boss = new EntityMaelstromStatueOfNirvana(world);
        boss.copyLocationAndAnglesFrom(this);
        boss.setRotationYawHead(this.rotationYawHead);
        if (!world.isRemote) {
            boss.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(this)), null);
            boss.setLevel(getLevel());
            boss.setElement(getElement());
            world.spawnEntity(boss);

            EntityLivingBase attackTarget = this.getAttackTarget();
            if(attackTarget != null) {
                boss.setAttackTarget(attackTarget);
                playSound(SoundEvents.ENTITY_ILLAGER_PREPARE_MIRROR, 2.5f, 1.0f + ModRandom.getFloat(0.2f));
                new ActionRingAttack(boss.maelstromFlame).performAction(boss, attackTarget);
            }
        }
        this.setPosition(0, 0, 0);
        super.onDeath(cause);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        ModUtils.removeTaskOfType(this.tasks, EntityAIWanderWithGroup.class);
    }

    private void initNirvanaAI() {
        float attackDistance = (float) this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        this.tasks.addTask(4, new AIAerialTimedAttack<>(this, 40, attackDistance, 20, 30));
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
    public boolean isEntityInvulnerable(@Nonnull DamageSource source) {
        long pillars = goldenPillars().size();
        return super.isEntityInvulnerable(source) || pillars > 0;
    }

    public List<EntityGoldenPillar> goldenPillars() {
        return ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox()
                .grow(getMobConfig().getDouble("pillar_protection_range"))).stream()
                .filter(e -> e instanceof EntityGoldenPillar)
                .filter(e ->  world.rayTraceBlocks(e.getPositionEyes(1), getPositionEyes(1), false, true, false) == null)
                .map(e -> (EntityGoldenPillar)e)
                .collect(Collectors.toList());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if(rand.nextInt(8) == 0 && amount > 1) {
            doTeleportNext = true;
        }

        double firstSummonHp = getMobConfig().getDouble("first_summon_hp");
        double secondSummonHp = getMobConfig().getDouble("second_summon_hp");
        double thirdSummonHp = getMobConfig().getDouble("third_summon_hp");
        double fourthSummonHp = getMobConfig().getDouble("fourth_summon_hp");

        float prevHealth = this.getHealth();
        boolean flag = super.attackEntityFrom(source, amount);

        if ((prevHealth > firstSummonHp && this.getHealth() <= firstSummonHp) ||
                (prevHealth > secondSummonHp && this.getHealth() <= secondSummonHp) ||
                (prevHealth > thirdSummonHp && this.getHealth() <= thirdSummonHp) ||
                (prevHealth > fourthSummonHp && this.getHealth() <= fourthSummonHp)) {
            doSummonNext = true;
        }

        return flag;
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {
        if(doSummonNext) {
            doSummonNext = false;
            spawnPillarAttack.accept(target);
            addEvent(() -> setAttackCount(0), 25);

            return 40;
        }

        return doNormalAttack(target);
    }

    public int doNormalAttack(EntityLivingBase target) {
        if(getAttackCount() == 0) {
            setAttackCount(ModRandom.range(4, 7));
        }

        boolean canSee = world.rayTraceBlocks(target.getPositionEyes(1), getPositionEyes(1), false, true, false) == null;
        List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(
                volleyAttack,
                isSecondPhase() ? secondPhaseRayAttack : rayAttack,
                isSecondPhase() ? secondPhaseRuneAttack : runeAttack,
                teleportAttack));
        double[] weights = {1, 1, 1, canSee ? 0 : 2};

        Consumer<EntityLivingBase> nextAttack = ModRandom.choice(attacks, rand, weights).next();

        if(doTeleportNext) {
            nextAttack = teleportAttack;
            doTeleportNext = false;
        }

        nextAttack.accept(target);

        int cooldown = getAttackCount() == 1 ? 120 : 40;

        addEvent(() -> setAttackCount(getAttackCount() - 1), 25);

        return cooldown;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            Vec3d particleColor = this.isSecondPhase() && rand.nextFloat() < 0.5 ? ModColors.PURPLE : ModColors.YELLOW;
            ParticleManager.spawnEffect(world, ModRandom.randVec()
                    .add(this.getPositionVector()),
                    particleColor);
        } else if(id == ModUtils.SECOND_PARTICLE_BYTE) {
            ModUtils.performNTimes(3, i -> ModUtils.circleCallback(i * 0.5f + 1, 30, pos -> {
                ParticleManager.spawnSwirl(world, getPositionVector().add(pos), ModColors.YELLOW, Vec3d.ZERO, ModRandom.range(10, 15));
                ParticleManager.spawnSwirl(world,
                        getPositionVector().add(ModUtils.rotateVector2(pos, ModUtils.yVec(1), 90)),
                        ModColors.YELLOW, Vec3d.ZERO, ModRandom.range(10, 15));
            }));
        }

        super.handleStatusUpdate(id);
    }

    @Override
    public void doRender(RenderManager renderManager, double x, double y, double z, float entityYaw, float partialTicks) {
        for (EntityGoldenPillar e : goldenPillars()) {
            RenderUtils.drawLazer(renderManager, this.getPositionVector(), e.getPositionVector(), new Vec3d(x, y - 1, z), ModColors.YELLOW, this, partialTicks);
        }
    }

    public boolean isSecondPhase() {
        return this.getHealth() < getMobConfig().getDouble("second_phase_hp");
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

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACK_COUNT, 0);
    }

    public int getAttackCount() {
        return dataManager.get(ATTACK_COUNT);
    }

    protected void setAttackCount(int i) {
        dataManager.set(ATTACK_COUNT, i);
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
