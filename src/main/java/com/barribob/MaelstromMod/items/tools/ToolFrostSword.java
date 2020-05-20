package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerSwordSwing;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.items.ISweepAttackParticles;
import com.barribob.MaelstromMod.util.Element;
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

public class ToolFrostSword extends ToolSword implements ISweepAttackOverride, ISweepAttackParticles {
    private static final float targetEntitySize = 1.0f;
    private static final Vec3d particleColor = new Vec3d(0.4, 0.4, 0.7f);

    public ToolFrostSword(String name, ToolMaterial material, float level) {
	super(name, material, level, Element.AZURE);
    }

    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target) {
	ModUtils.doSweepAttack(player, target, getElement(), (e) -> {
	    e.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 1));
	});

	if (target != null) {
	    target.addPotionEffect(new PotionEffect(MobEffects.SLOWNESS, 60, 1));

	    Entity particle = new ParticleSpawnerSwordSwing(player.world);
	    particle.copyLocationAndAnglesFrom(target);
	    player.world.spawnEntity(particle);
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("frost_sword"));
    }

    @Override
    public Vec3d getColor() {
	return particleColor;
    }

    @Override
    public float getSize() {
	return targetEntitySize;
    }
}
