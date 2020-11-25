package com.barribob.MaelstromMod.entity.entities.npc;

import com.barribob.MaelstromMod.entity.entities.EntityTrader;
import com.barribob.MaelstromMod.init.ModProfessions;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.List;

public class NexusSpecialTrader extends EntityTrader {
    private byte particles = 4;

    public NexusSpecialTrader(World worldIn) {
        super(worldIn);
        this.setImmovable(true);
        this.setSize(0.8f, 3.0f);
        this.setNoGravity(true);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 5.0F, 1.0F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        world.setEntityState(this, particles);
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == particles) {
            // Yellow particles
            ParticleManager.spawnEffect(world, ModRandom.randVec().add(ModUtils.entityPos(this).add(new Vec3d(0, 2, 0))), new Vec3d(0.9, 0.9, 0.5));
        } else {
            super.handleStatusUpdate(id);
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        return false;
    }

    @Override
    protected List<ITradeList> getTrades() {
        return ModProfessions.NEXUS_SPECIAL_TRADER.getTrades(0);
    }

    @Override
    protected String getVillagerName() {
        return "Nexus Saiyan";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }
}
