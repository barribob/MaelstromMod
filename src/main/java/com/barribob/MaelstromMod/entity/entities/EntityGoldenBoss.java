package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.ai.AIAerialTimedAttack;
import com.barribob.MaelstromMod.entity.ai.EntityAIWanderWithGroup;
import com.barribob.MaelstromMod.entity.ai.FlyingMoveHelper;
import com.barribob.MaelstromMod.entity.projectile.EntityLargeGoldenRune;
import com.barribob.MaelstromMod.entity.projectile.ProjectileGoldenMissile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMaelstromRune;
import com.barribob.MaelstromMod.entity.projectile.ProjectileStatueMaelstromMissile;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.init.ModBBAnimations;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.RenderUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
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
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityGoldenBoss extends EntityMaelstromMob implements IAttack {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_6));
    private static final int pillarShieldRange = 15;
    protected static final DataParameter<Integer> ATTACK_COUNT = EntityDataManager.createKey(EntityLeveledMob.class, DataSerializers.VARINT);
    private static boolean doSummonNext;
    private static boolean doTeleportNext;

    public EntityGoldenBoss(World worldIn) {
        super(worldIn);
        this.moveHelper = new FlyingMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, worldIn);
        this.setSize(1.6f, 3.6f);
        this.healthScaledAttackFactor = 0.2;
    }

    private void executeRayAttack(EntityLivingBase target) {
        Vec3d targetPos = target.getPositionEyes(1);
        Vec3d fromTargetToActor = getPositionVector().subtract(targetPos);

        Vec3d lineDirection = ModUtils.rotateVector2(
                fromTargetToActor
                        .crossProduct(ModUtils.yVec(1)),
                fromTargetToActor,
                ModRandom.range(0, 180))
                .normalize()
                .scale(6);

        Vec3d lineStart = targetPos.subtract(lineDirection);
        Vec3d lineEnd = targetPos.add(lineDirection);

        ModUtils.lineCallback(lineStart, lineEnd, 10, (pos, i) -> {
            ProjectileGoldenMissile projectile = new ProjectileGoldenMissile(world, this, this.getAttack());
            projectile.setTravelRange(30);
            projectile.setNoGravity(true);

            ModUtils.throwProjectile(this, pos, projectile, 0, 1.1f);
        });

        this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, ModRandom.getFloat(0.2f) + 1.3f);
    }

    private final Consumer<EntityLivingBase> rayAttack = target -> {
        ModBBAnimations.animation(this, "statue.fireball", false);

        addEvent(() -> executeRayAttack(target), 22);
    };

    private final Consumer<EntityLivingBase> secondPhaseRayAttack = target -> {
        ModBBAnimations.animation(this, "statue.fireball", false);

        addEvent(() -> {
            Vec3d targetPos = target.getPositionVector();
            Vec3d fromTargetToActor = getPositionVector().subtract(targetPos);

            Vec3d lineDirection = ModUtils.rotateVector2(
                    fromTargetToActor
                            .crossProduct(ModUtils.yVec(1)),
                    fromTargetToActor,
                    ModRandom.range(0, 180))
                    .normalize()
                    .scale(6);

            Vec3d lineStart = targetPos.subtract(lineDirection);
            Vec3d lineEnd = targetPos.add(lineDirection);

            ModUtils.lineCallback(lineStart, lineEnd, 10, (pos, i) -> {
                ProjectileStatueMaelstromMissile projectile = new ProjectileStatueMaelstromMissile(world, this, this.getAttack());
                projectile.setTravelRange(30);
                projectile.setNoGravity(true);

                ModUtils.throwProjectile(this, pos, projectile, 0, 0.9f);
            });

            executeRayAttack(target);
        }, 22);
    };

    private final Consumer<EntityLivingBase> runeAttack = target -> {
        ModBBAnimations.animation(this, "statue.runes", false);

        addEvent(() -> {
            float zeroish = 0.001f;
            EntityLargeGoldenRune projectile = new EntityLargeGoldenRune(this.world, this, this.getAttack() * 2);
            ModUtils.setEntityPosition(projectile,
                    target.getPositionVector()
                            .add(new Vec3d(ModRandom.getFloat(2), 0.1, ModRandom.getFloat(2))));
            projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
            projectile.setTravelRange(25);
            this.world.spawnEntity(projectile);
        }, 12);
    };

    private final Consumer<EntityLivingBase> secondPhaseRuneAttack = target -> {
        ModBBAnimations.animation(this, "statue.runes", false);

        addEvent(() -> {
            float zeroish = 0.001f;
            Vec3d randomDirection = ModRandom.randVec()
                    .crossProduct(ModUtils.yVec(1))
                    .normalize()
                    .scale(0.13 + ModRandom.getFloat(0.05f));
            ProjectileMaelstromRune projectile = new ProjectileMaelstromRune(this.world, this, this.getAttack() * 2);
            ModUtils.setEntityPosition(projectile,
                    target.getPositionVector()
                            .add(ModUtils.yVec(0.1))
                            .add(ModUtils.rotateVector(randomDirection.scale(-2), ModUtils.yVec(1), ModRandom.range(-15, 15))));
            projectile.shoot(zeroish, zeroish, zeroish, zeroish, zeroish);
            projectile.setVelocity(randomDirection.x, 0, randomDirection.z);
            projectile.setTravelRange(25);
            this.world.spawnEntity(projectile);
        }, 12);
    };

    private final Consumer<EntityLivingBase> spawnPillarAttack = target -> {
        ModBBAnimations.animation(this, "statue.summon", false);

        this.addEvent(() -> {
            for(int i = 0; i < getMobConfig().getInt("summoning_algorithm.mobs_per_spawn"); i++) {
                BlockPos spawnCenter = ModUtils.findGroundBelow(world, getPosition());
                EntityLeveledMob mob = ModUtils.spawnMob(world, spawnCenter, this.getLevel(), getMobConfig().getConfig("summoning_algorithm"));
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

        Function<Vec3d, Runnable> missile = (offset) -> () -> {
            if(isSecondPhase() && rand.nextFloat() < 0.5) {
                ProjectileStatueMaelstromMissile projectile = new ProjectileStatueMaelstromMissile(world, this, this.getAttack());
                projectile.setTravelRange(25);

                ModUtils.throwProjectile(this, target.getPositionEyes(1),
                        projectile,
                        18.0f,
                        1.4f,
                        offset);
            }

            ProjectileGoldenMissile projectile = new ProjectileGoldenMissile(world, this, this.getAttack());
            projectile.setTravelRange(25);

            ModUtils.throwProjectile(this, target.getPositionEyes(1),
                    projectile,
                    6.0f,
                    1.6f,
                    offset);

            this.playSound(SoundEvents.ENTITY_BLAZE_SHOOT, 1.0F, ModRandom.getFloat(0.2f) + 1.3f);
        };

        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, 1, 1))), 20);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, 1, -1))), 20);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, 0.5, 1.7))), 25);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, 0.5, -1.7))), 25);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, 0, 2))), 30);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, 0, -2))), 30);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, -0.5, 2.5))), 35);
        addEvent(missile.apply(ModUtils.getRelativeOffset(this, new Vec3d(0, -0.5, -2.5))), 35);
    };

    private final Consumer<EntityLivingBase> teleportAttack = target -> {
        for(int i = 0; i < 50; i++) {
            Vec3d pos = ModRandom.randVec().normalize().scale(12)
                    .add(target.getPositionVector());

            boolean canSee = world.rayTraceBlocks(target.getPositionEyes(1), pos, false, true, false) == null;
            Vec3d prevPos = getPositionVector();
            if(canSee && ModUtils.attemptTeleport(pos, this)){
                ModUtils.lineCallback(prevPos, pos, 50, (particlePos, j) ->
                        Main.network.sendToAllTracking(new MessageModParticles(EnumModParticles.EFFECT, particlePos, Vec3d.ZERO, ModColors.YELLOW), this));
                world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE);
                break;
            }
        }
    };

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        ModUtils.removeTaskOfType(this.tasks, EntityAIWanderWithGroup.class);
        float attackDistance = (float) this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getAttributeValue();
        this.tasks.addTask(4, new AIAerialTimedAttack<>(this, 1.0f, 40, attackDistance, 20, 0.4f, 30));
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
    public boolean isEntityInvulnerable(DamageSource source) {
        long pillars = goldenPillars().size();
        return super.isEntityInvulnerable(source) || pillars > 0;
    }

    public List<EntityGoldenPillar> goldenPillars() {
        return ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox()
                .grow(pillarShieldRange)).stream()
                .filter(e -> e instanceof EntityGoldenPillar)
                .map(e -> (EntityGoldenPillar)e)
                .collect(Collectors.toList());
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {

        if(rand.nextInt(8) == 0) {
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

        List<Consumer<EntityLivingBase>> attacks = new ArrayList<>(Arrays.asList(
                volleyAttack,
                isSecondPhase() ? secondPhaseRayAttack : rayAttack,
                isSecondPhase() ? secondPhaseRuneAttack : runeAttack));
        double[] weights = {1, 1, 1};

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
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GOLDEN_BOSS;
    }

    @Override
    protected float getManaExp() {
        return 0;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        ModUtils.aerialTravel(this, strafe, vertical, forward);
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
