package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerExplosion;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ToolExplosiveDagger extends ToolDagger {
    public ToolExplosiveDagger(String name, ToolMaterial material, float level) {
	super(name, material, level);
    }

    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target) {
	if (target != null) {
	    float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	    int fireFactor = EnchantmentHelper.getEnchantmentLevel(Enchantments.FIRE_ASPECT, player.getHeldItemMainhand()) * 5;
	    ModUtils.handleAreaImpact(4, (e) -> attackDamage * 0.5f, player, target.getPositionVector().add(ModUtils.yVec(-0.1f)), DamageSource.causeExplosionDamage(player), 1,
		    fireFactor);
	    player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, player.getSoundCategory(), 1.0F, 0.9F);
	    Entity particle = new ParticleSpawnerExplosion(player.world);
	    particle.copyLocationAndAnglesFrom(target);
	    player.world.spawnEntity(particle);
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("explosive_dagger"));
    }
}
