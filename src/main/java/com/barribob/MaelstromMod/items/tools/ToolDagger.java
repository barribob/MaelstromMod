package com.barribob.MaelstromMod.items.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * The dagger tool is a high damage, but short reached weapon
 *
 */
public class ToolDagger extends ToolSword implements IExtendedReach, ISweepAttackOverride
{
    private static final UUID REACH_MODIFIER = UUID.fromString("a6323e02-d8e9-44c6-b941-f5d7155bb406");
    private final float attackDamage;

    public ToolDagger(String name, ToolMaterial material)
    {
	super(name, material);
	this.attackDamage = 3.0F + material.getAttackDamage() * 2;
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
	    multimap.put("extended_reach", new AttributeModifier(REACH_MODIFIER, "Extended Reach Modifier", -1.0D, 0));
	    multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", (double) this.attackDamage, 0));
	    multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.1D, 0));
	}
	return multimap;
    }

    @Override
    public float getReach()
    {
	return 2.0f;
    }

    // Remove the sweep attack for the dagger
    @Override
    public void doSweepAttack(EntityPlayer player, EntityLivingBase target)
    {	
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	tooltip.add(TextFormatting.GRAY + "Has no sweep attack");
    }
}
