package com.barribob.MaelstromMod.items.tools;

import java.util.UUID;

import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.google.common.collect.Multimap;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;

/**
 * 
 * Holds reach properties for an extended reach tool
 *
 */
public class ToolLongsword extends ToolSword implements IExtendedReach, ISweepAttackOverride
{
    private static final UUID REACH_MODIFIER = UUID.fromString("a6323e02-d8e9-44c6-b941-f5d7155bb406");
    private float reach = 4;

    public ToolLongsword(String name, ToolMaterial material, float level)
    {
	super(name, material, level);
    }

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
	float targetEntitySize = (float) 1.0D;

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
	Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

	if (equipmentSlot == EntityEquipmentSlot.MAINHAND)
	{
	    multimap.put("extended_reach", new AttributeModifier(REACH_MODIFIER, "Extended Reach Modifier", this.reach - 3.0D, 0));
	    multimap.removeAll(SharedMonsterAttributes.ATTACK_SPEED.getName());
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", -2.8000000953674316D, 0));
	}
	return multimap;
    }
}
