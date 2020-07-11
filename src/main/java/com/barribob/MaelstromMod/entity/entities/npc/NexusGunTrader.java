package com.barribob.MaelstromMod.entity.entities.npc;

import com.barribob.MaelstromMod.entity.entities.EntityTrader;
import com.barribob.MaelstromMod.init.ModProfessions;
import com.barribob.MaelstromMod.items.gun.ItemGun;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.TimedMessager;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.world.World;

import java.util.List;

public class NexusGunTrader extends EntityTrader {
    private byte smoke = 4;
    private TimedMessager messager;
    private static final String[] GUN_EXPLANATION = {"gun_1", "gun_2", "gun_3", "gun_4", "gun_5", "gun_6", "gun_7", ""};
    private static final int[] MESSAGE_TIMES = {50, 150, 250, 350, 450, 550, 650, 750};

    public NexusGunTrader(World worldIn) {
        super(worldIn);
        this.setImmovable(true);
        this.setNoGravity(true);
    }

    @Override
    public void useRecipe(MerchantRecipe recipe) {
        super.useRecipe(recipe);
        if (messager == null && recipe.getItemToSell().getItem() instanceof ItemGun && ((ItemGun) recipe.getItemToSell().getItem()).getLevel() < 2) {
            messager = new TimedMessager(GUN_EXPLANATION, MESSAGE_TIMES, (s) -> {
                messager = null;
            });
        }
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 5.0F, 1.0F));
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!world.isRemote && messager != null) {
            messager.Update(world, ModUtils.getPlayerAreaMessager(this));
        }
        if (rand.nextInt(20) == 0) {
            world.setEntityState(this, smoke);
        }
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if (id == smoke) {
            // Positions smoke particles right above the smoking pipe
            Vec3d look = this.getVectorForRotation(this.rotationPitch, this.rotationYawHead);
            Vec3d pos = ModUtils.entityPos(this).add(new Vec3d(0, this.getEyeHeight(), 0));
            Vec3d side = look.scale(0.25).rotateYaw((float) Math.PI * -0.5f);
            Vec3d offset = pos.add(look.scale(0.5f)).add(side).add(new Vec3d(0, 0.1, 0));

            world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, offset.x, offset.y, offset.z, 0, 0.0f, 0);
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
        return ModProfessions.NEXUS_GUNSMITH.getTrades(0);
    }

    @Override
    protected String getVillagerName() {
        return "Gunsmith";
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
    }
}
