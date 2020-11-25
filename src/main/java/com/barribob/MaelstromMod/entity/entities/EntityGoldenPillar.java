package com.barribob.MaelstromMod.entity.entities;

import com.barribob.MaelstromMod.entity.ai.EntityAITimedAttack;
import com.barribob.MaelstromMod.entity.projectile.EntityGoldenRune;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.util.IAttack;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.LootTableHandler;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityGoldenPillar extends EntityMaelstromMob implements IAttack {
    public EntityGoldenPillar(World worldIn) {
        super(worldIn);
        this.setSize(1.4f, 3.2f);
        this.setNoGravity(true);
        this.setImmovable(true);
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.tasks.addTask(4, new EntityAITimedAttack<>(this, 0f, 60, 40, 30.0f, 0f));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        world.setEntityState(this, ModUtils.PARTICLE_BYTE);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == ModUtils.PARTICLE_BYTE) {
            // Spawn particles as the eyes
            ModUtils.performNTimes(3, (i) -> {
                Vec3d look = this.getVectorForRotation(0, this.renderYawOffset + (i * 120)).scale(0.5f);
                Vec3d pos = this.getPositionVector().add(new Vec3d(0, this.getEyeHeight(), 0));
                ParticleManager.spawnEffect(world, pos.add(look), ModColors.YELLOW);
            });
        }
        else if (id == ModUtils.SECOND_PARTICLE_BYTE) {
            ParticleManager.spawnFirework(world,
                    this.getPositionVector().add(new Vec3d(ModRandom.getFloat(0.25f), 1, ModRandom.getFloat(0.25f))),
                    ModColors.YELLOW,
                    new Vec3d(0, 0.15, 0));
        }
        super.handleStatusUpdate(id);
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
        return LootTableHandler.GOLDEN_MAELSTROM;
    }

    private Runnable getRune(Vec3d target) {
        return () -> {
            Projectile projectile = new EntityGoldenRune(world, this, this.getAttack());
            projectile.setTravelRange(30);
            ModUtils.throwProjectile(this, target, projectile, 4, 0.5f);
            playSound(SoundEvents.BLOCK_METAL_BREAK, 1.0f, 1.0f + ModRandom.getFloat(0.2f));
        };
    }

    @Override
    public int startAttack(EntityLivingBase target, float distanceSq, boolean strafingBackwards) {

        /*
        Oscillate attacks between attack, and to the side
         */

        addEvent(() -> {
            Vec3d centerTarget = target.getPositionEyes(1);
            Vec3d offset = centerTarget
                    .subtract(getPositionEyes(1))
                    .crossProduct(ModUtils.yVec(1))
                    .normalize()
                    .scale(3 * ModRandom.randSign());

            getRune(centerTarget).run();
            addEvent(getRune(centerTarget.add(offset)), 10);
            addEvent(getRune(centerTarget.subtract(offset)), 20);
        }, 40);

        for(int i = 0; i < 40; i++) {
            addEvent(() -> world.setEntityState(this, ModUtils.SECOND_PARTICLE_BYTE), i);
        }

        return 60;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
    }
}
