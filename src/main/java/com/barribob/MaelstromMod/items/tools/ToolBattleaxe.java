package com.barribob.MaelstromMod.items.tools;

import java.util.List;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
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
    private final float attackDamage;
    private final Item.ToolMaterial material;

    public ToolBattleaxe(String name, ToolMaterial material)
    {
	super(name, material);
	this.material = material;
	this.attackDamage = 3.0F + material.getAttackDamage() * 2;
    }

    /**
     * Increased sweep attack
     */
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {
	float attackDamage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	float sweepDamage = 1.0F + EnchantmentHelper.getSweepingDamageRatio(player) * attackDamage;
	float maxDistanceSq = 10.0f;
	float targetEntitySize = (float) 2.0D;

	for (EntityLivingBase entitylivingbase : player.world.getEntitiesWithinAABB(EntityLivingBase.class, target.getEntityBoundingBox().grow(targetEntitySize, 0.25D, targetEntitySize)))
	{
	    if (entitylivingbase != player && entitylivingbase != target && !player.isOnSameTeam(entitylivingbase) && player.getDistanceSq(entitylivingbase) < maxDistanceSq)
	    {
		entitylivingbase.knockBack(player, 0.4F, (double) MathHelper.sin(player.rotationYaw * 0.017453292F),
			(double) (-MathHelper.cos(player.rotationYaw * 0.017453292F)));
		entitylivingbase.attackEntityFrom(DamageSource.causePlayerDamage(player), sweepDamage);
	    }
	}

	player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, player.getSoundCategory(), 1.0F, 0.9F);
	player.spawnSweepParticles();
    }

    /**
     * Gets a map of item attribute modifiers, used by ItemSword to increase hit
     * damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot)
    {
	Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    // Change the attack damage and speed specific for the battleaxe
	    multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0));
	    multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.9D, 0));
	}

	return multimap;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + "Has large sweep attack");
    }
}
