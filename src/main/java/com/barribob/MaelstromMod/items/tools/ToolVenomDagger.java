package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.items.ISweepAttackParticles;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ToolVenomDagger extends ToolDagger implements ISweepAttackParticles {
    public ToolVenomDagger(String name, ToolMaterial material, float level) {
	super(name, material, level);
    }

    // Add a poison effect on a full attack
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target) {
	if (target != null) {
	    target.addPotionEffect(new PotionEffect(MobEffects.POISON, 100, 1));
	    Entity particle = new ParticleSpawnerSwordSwing(player.world);
	    particle.copyLocationAndAnglesFrom(target);
	    player.world.spawnEntity(particle);
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("venom_dagger"));
    }

    @Override
    public Vec3d getColor() {
	return new Vec3d(0.2, 0.5, 0.2);
    }

    @Override
    public float getSize() {
	return 0.5f;
    }
}
