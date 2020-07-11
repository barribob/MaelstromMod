package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.action.Action;
import com.barribob.MaelstromMod.entity.action.ActionFireball;
import com.barribob.MaelstromMod.entity.action.ActionMaelstromRing;
import com.barribob.MaelstromMod.entity.action.ActionSpawnEnemy;
import com.barribob.MaelstromMod.entity.ai.EntityAIRangedAttack;
import com.barribob.MaelstromMod.entity.animation.AnimationMegaMissile;
import com.barribob.MaelstromMod.entity.animation.AnimationOctoMissiles;
import com.barribob.MaelstromMod.entity.animation.AnimationRuneSummon;
import com.barribob.MaelstromMod.entity.util.ComboAttack;
import com.barribob.MaelstromMod.init.ModEntities;
import com.barribob.MaelstromMod.util.*;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMaelstromGoldenBoss extends EntityMaelstromMob {
    private final BossInfoServer bossInfo = (new BossInfoServer(this.getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.NOTCHED_6));
    private ComboAttack attackHandler = new ComboAttack();
    private byte spawnEnemy = 4;
    private byte blackFireball = 5;
    private byte runes = 6;
    private byte spawnPillar = 7;

    public EntityMaelstromGoldenBoss(World worldIn) {
        super(worldIn);
        this.setSize(1.6f, 3.6f);
        this.healthScaledAttackFactor = 0.2;
        this.experienceValue = ModEntities.BOSS_EXPERIENCE;
        this.setLevel(4);
        this.setElement(Element.GOLDEN);
        if (!worldIn.isRemote) {
            this.attackHandler.setAttack(spawnEnemy, new ActionSpawnEnemy(() -> {
                EntityLeveledMob enemy = rand.nextInt(3) == 0 ? new EntityShade(world) : new EntityMaelstromLancer(world);
                return enemy.setElement(rand.nextInt(3) == 0 ? Element.GOLDEN : Element.NONE).setLevel(getLevel());
            }));
            this.attackHandler.setAttack(blackFireball, new ActionFireball());
            this.attackHandler.setAttack(runes, new ActionMaelstromRing());
            this.attackHandler.setAttack(spawnPillar, new ActionSpawnEnemy(() -> new EntityGoldenPillar(world).setLevel(getLevel()).setElement(Element.GOLDEN)));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void initAnimation() {
        this.attackHandler.setAttack(spawnEnemy, Action.NONE, () -> new AnimationOctoMissiles());
        this.attackHandler.setAttack(blackFireball, new ActionFireball(), () -> new AnimationMegaMissile());
        this.attackHandler.setAttack(runes, new ActionMaelstromRing(), () -> new AnimationRuneSummon());
        this.attackHandler.setAttack(spawnPillar, Action.NONE, EntityGoldenBoss.getSpawnPillarAnimation());
        this.currentAnimation = new AnimationOctoMissiles();
    }

    @Override
    protected boolean canDespawn() {
        return false;
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAIRangedAttack<EntityMaelstromGoldenBoss>(this, 1.0f, 40, 20.0f, 0.4f));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(250);
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
        this.attackHandler.getCurrentAttackAction().performAction(this, target);
    }

    @Override
    protected ResourceLocation getLootTable() {
        return LootTableHandler.GOLDEN_BOSS;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.bossInfo.setPercent(getHealth() / getMaxHealth());
        if (!world.isRemote && this.ticksExisted % 20 == 0) {
            long pillars = ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox().grow(15)).stream().filter((e) -> e instanceof EntityGoldenPillar).count();
            this.heal((float) Math.sqrt(pillars));
        }
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {
        super.setSwingingArms(swingingArms);
        if (swingingArms && !world.isRemote) {
            Byte[] attack = {spawnEnemy, blackFireball, runes, spawnPillar};
            double[] weights = {0.2, 0.3, 0.3, (this.getHealth() < this.getMaxHealth() * 0.6 ? 0.1 : 0.0)};
            attackHandler.setCurrentAttack(ModRandom.choice(attack, rand, weights).next());
            world.setEntityState(this, attackHandler.getCurrentAttack());
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id >= 4 && id <= 7) {
            currentAnimation = attackHandler.getAnimation(id);
            getCurrentAnimation().startAnimation();
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public void doRender(RenderManager renderManager, double x, double y, double z, float entityYaw, float partialTicks) {
        for (EntityLivingBase e : ModUtils.getEntitiesInBox(this, this.getEntityBoundingBox().grow(20))) {
            if (e instanceof EntityGoldenPillar) {
                RenderUtils.drawLazer(renderManager, this.getPositionVector(), e.getPositionVector(), new Vec3d(x, y - 1, z), ModColors.YELLOW, this, partialTicks);
            }
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

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.BLOCK_METAL_PLACE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.BLOCK_METAL_BREAK;
    }
}
