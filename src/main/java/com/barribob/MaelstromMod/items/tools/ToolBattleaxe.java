package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.barribob.MaelstromMod.items.ISweepAttackOverride;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * A slower melee weapons with a large sweep attack
 *
 */
public class ToolBattleaxe extends ToolSword implements ISweepAttackOverride
{
    public ToolBattleaxe(String name, ToolMaterial material, float level)
    {
	super(name, material, level);
    }

    /**
     * Increased sweep attack
     */
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = Math.min(0.15F + EnchantmentHelper.getSweepingDamageRatio(player), 1) * attackDamage;
	float maxDistanceSq = 10.0f;
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
    public float getAttackDamage()
    {
	return super.getAttackDamage() * 1.25f;
    }

    @Override
    protected double getAttackSpeed()
    {
	return -3.1D;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "Has large sweep attack");
    }
}
