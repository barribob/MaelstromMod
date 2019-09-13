package com.barribob.MaelstromMod.items.tools;

import java.util.List;
import java.util.UUID;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.ModUtils;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * Holds reach properties for an extended reach tool
 *
 */
public class ToolDragonslayer extends ToolSword implements IExtendedReach, ISweepAttackOverride
{
    private static final UUID REACH_MODIFIER = UUID.fromString("a6323e02-d8e9-44c6-b941-f5d7155bb406");
    private float reach = 5;

    public ToolDragonslayer(String name, ToolMaterial material, float level)
    {
	super(name, material, level);
    }

    @Override
    public float getReach()
    {
	return this.reach;
    }
    
    /**
     * Increased sweep attack
     */
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = Math.min(0.15F + EnchantmentHelper.getSweepingDamageRatio(player), 1) * attackDamage;
	float maxDistanceSq = (float) Math.pow(this.reach, 2);
	float targetEntitySize = (float) 2.0D;

	for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(targetEntitySize, 0.25D, targetEntitySize)))
	{
	    if (entitylivingbase != player && entitylivingbase != target && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < maxDistanceSq)
	    {
		entitylivingbase.knockBack(player, 0.4F, MathHelper.sin(player.rotationYaw * 0.017453292F),
			(-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), sweepDamage);
	    }
	}

	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 0.9F);
	player.spawnSweepParticles();
    }

    @Override
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    multimap.put("extended_reach", new AttributeModifier(REACH_MODIFIER, "Extended Reach Modifier", this.reach - 3.0D, 0));
	}
	return multimap;
    }
    @Override
    public float getAttackDamage()
    {
	return super.getAttackDamage() * 1.25f;
    }

    @Override
    protected double getAttackSpeed()
    {
	return -3.2D;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("dragonslayer"));
    }
}
