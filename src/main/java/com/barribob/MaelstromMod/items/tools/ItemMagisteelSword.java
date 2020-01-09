package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileSwordSlash;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMagisteelSword extends ToolSword implements ISweepAttackOverride
{
    public ItemMagisteelSword(String name, ToolMaterial material, float level)
    {
	super(name, material, level);
    }

    @Override
    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack)
    {
	if (!entityLiving.world.isRemote && entityLiving instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) entityLiving;
	    float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	    Item heldItem = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
	    float atkCooldown = player.getCooledAttackStrength(0.5F);

	    if (atkCooldown > 0.9F)
	    {
		Projectile proj = new ProjectileSwordSlash(player.world, player, attackDamage);
		proj.setTravelRange(4);
		proj.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5f, 0);
		player.world.spawnEntity(proj);
		player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F,
			0.9F);
		if (!player.capabilities.isCreativeMode)
		{
		    stack.damageItem(1, player);
		}
	    }
	}
	return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = Math.min(0.15F + EnchantmentHelper.getSweepingDamageRatio(player), 1) * attackDamage;
	float maxDistanceSq = 9.0f;
	float targetEntitySize = (float) 1.0D;

	for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class,
		target.getEntityBoundingBox().grow(targetEntitySize, 0.25D, targetEntitySize)))
	{
	    if (entitylivingbase != player && entitylivingbase != target && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < maxDistanceSq)
	    {
		entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F), (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), sweepDamage);
	    }
	}

	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 0.9F);
	player.spawnSweepParticles();
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("magisteel_sword"));
    }
}
