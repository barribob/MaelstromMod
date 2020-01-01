package com.barribob.MaelstromMod.player;

import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityMultiPart;
import net.minecraft.entity.MultiPartEntityPart;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.WorldServer;

/**
 * 
 * Methods taken from the player class to implement a custom sweep event
 *
 */
public class PlayerMeleeAttack
{
    private static boolean onPlayerAttackTarget(EntityPlayer player, Entity target)
    {
	ItemStack stack = player.getHeldItemMainhand();
	return stack.isEmpty() || !stack.getItem().onLeftClickEntity(stack, player, target);
    }

    /**
     * Attacks for the player the targeted entity with the currently equipped item.
     * The equipped item has hitEntity called on it. Args: targetEntity
     */
    public static void attackTargetEntityWithCurrentItem(EntityPlayer player, Entity targetEntity)
    {
	if (!onPlayerAttackTarget(player, targetEntity))
	    return;

	if (targetEntity.canBeAttackedWithItem())
	{
	    if (!targetEntity.hitByEntity(player))
	    {
		float damage = (float) player.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
		float bonusDamage;

		if (targetEntity instanceof EntityLivingBase)
		{
		    bonusDamage = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), ((EntityLivingBase) targetEntity).getCreatureAttribute());
		}
		else
		{
		    bonusDamage = EnchantmentHelper.getModifierForCreature(player.getHeldItemMainhand(), EnumCreatureAttribute.UNDEFINED);
		}

		bonusDamage += ModUtils.getSwordEnchantmentDamage(player.getHeldItemMainhand());

		float atkCooldown = player.getCooledAttackStrength(0.5F);
		damage = damage * (0.2F + atkCooldown * atkCooldown * 0.8F);
		bonusDamage = bonusDamage * atkCooldown;
		player.resetCooldown();

		if (damage > 0.0F || bonusDamage > 0.0F)
		{
		    boolean cooldownCharged = atkCooldown > 0.9F;
		    boolean sprintAtk = false;
		    int knockback = 0;
		    knockback = knockback + EnchantmentHelper.getKnockbackModifier(player);

		    if (player.isSprinting() && cooldownCharged)
		    {
			player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK,
				player.getSoundCategory(), 1.0F, 1.0F);
			++knockback;
			sprintAtk = true;
		    }

		    boolean critical = cooldownCharged && player.fallDistance > 0.0F && !player.onGround && !player.isOnLadder() && !player.isInWater()
			    && !player.isPotionActive(MobEffects.BLINDNESS) && !player.isRiding() && targetEntity instanceof EntityLivingBase;
		    critical = critical && !player.isSprinting();

		    net.minecraftforge.event.entity.player.CriticalHitEvent hitResult = net.minecraftforge.common.ForgeHooks.getCriticalHit(player, targetEntity, critical,
			    critical ? 1.5F : 1.0F);
		    critical = hitResult != null;
		    if (critical)
		    {
			damage *= hitResult.getDamageModifier();
		    }

		    damage = damage + bonusDamage;
		    boolean sweepAttack = false;
		    double d0 = player.distanceWalkedModified - player.prevDistanceWalkedModified;

		    if (cooldownCharged && !critical && !sprintAtk && player.onGround && d0 < player.getAIMoveSpeed())
		    {
			sweepAttack = true;
		    }

		    float health = 0.0F;
		    boolean hasFireAspect = false;
		    int fireAspect = EnchantmentHelper.getFireAspectModifier(player);

		    if (targetEntity instanceof EntityLivingBase)
		    {
			health = ((EntityLivingBase) targetEntity).getHealth();

			if (fireAspect > 0 && !targetEntity.isBurning())
			{
			    hasFireAspect = true;
			    targetEntity.setFire(1);
			}
		    }

		    double mx = targetEntity.motionX;
		    double my = targetEntity.motionY;
		    double mz = targetEntity.motionZ;

		    boolean attackSuccessful;
		    // If this is an elemental sword, then apply elemental damage
		    if (player.getHeldItemMainhand().getItem() instanceof IElement)
		    {
			Element element = ((IElement) player.getHeldItemMainhand().getItem()).getElement();
			attackSuccessful = targetEntity.attackEntityFrom(ModDamageSource.causeElementalPlayerDamage(player, element), damage);
		    }
		    else
		    {
			attackSuccessful = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(player), damage);
		    }

		    if (attackSuccessful)
		    {
			// Apply knockback
			if (knockback > 0)
			{
			    if (targetEntity instanceof EntityLivingBase)
			    {
				((EntityLivingBase) targetEntity).knockBack(player, knockback * 0.5F, MathHelper.sin(player.rotationYaw * 0.017453292F),
					(-MathHelper.cos(player.rotationYaw * 0.017453292F)));
			    }
			    else
			    {
				targetEntity.addVelocity(-MathHelper.sin(player.rotationYaw * 0.017453292F) * knockback * 0.5F, 0.1D,
					MathHelper.cos(player.rotationYaw * 0.017453292F) * knockback * 0.5F);
			    }

			    player.motionX *= 0.6D;
			    player.motionZ *= 0.6D;
			    player.setSprinting(false);
			}

			// Do the overridden sweep attack
			Item item = player.getHeldItemMainhand().getItem();
			if (item instanceof ISweepAttackOverride && sweepAttack && targetEntity instanceof EntityLivingBase)
			{
			    ((ISweepAttackOverride) item).doSweepAttack(player, (EntityLivingBase) targetEntity);
			}

			if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged)
			{
			    ((EntityPlayerMP) targetEntity).connection.sendPacket(new SPacketEntityVelocity(targetEntity));
			    targetEntity.velocityChanged = false;
			    targetEntity.motionX = mx;
			    targetEntity.motionY = my;
			    targetEntity.motionZ = mz;
			}

			if (critical)
			{
			    player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT,
				    player.getSoundCategory(), 1.0F, 1.0F);
			    player.onCriticalHit(targetEntity);
			}

			if (!critical && !sweepAttack)
			{
			    if (cooldownCharged)
			    {
				player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG,
					player.getSoundCategory(), 1.0F, 1.0F);
			    }
			    else
			    {
				player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK,
					player.getSoundCategory(), 1.0F, 1.0F);
			    }
			}

			if (bonusDamage > 0.0F)
			{
			    player.onEnchantmentCritical(targetEntity);
			}

			player.setLastAttackedEntity(targetEntity);

			if (targetEntity instanceof EntityLivingBase)
			{
			    EnchantmentHelper.applyThornEnchantments((EntityLivingBase) targetEntity, player);
			}

			EnchantmentHelper.applyArthropodEnchantments(player, targetEntity);
			ItemStack itemstack1 = player.getHeldItemMainhand();
			Entity entity = targetEntity;

			if (targetEntity instanceof MultiPartEntityPart)
			{
			    IEntityMultiPart ientitymultipart = ((MultiPartEntityPart) targetEntity).parent;

			    if (ientitymultipart instanceof EntityLivingBase)
			    {
				entity = (EntityLivingBase) ientitymultipart;
			    }
			}

			// Decrements item damage I think
			if (!itemstack1.isEmpty() && entity instanceof EntityLivingBase)
			{
			    ItemStack beforeHitCopy = itemstack1.copy();
			    itemstack1.hitEntity((EntityLivingBase) entity, player);

			    if (itemstack1.isEmpty())
			    {
				net.minecraftforge.event.ForgeEventFactory.onPlayerDestroyItem(player, beforeHitCopy, EnumHand.MAIN_HAND);
				player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.EMPTY);
			    }
			}

			if (targetEntity instanceof EntityLivingBase)
			{
			    float f5 = health - ((EntityLivingBase) targetEntity).getHealth();
			    player.addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));

			    if (fireAspect > 0)
			    {
				targetEntity.setFire(fireAspect * 4);
			    }

			    if (player.world instanceof WorldServer && f5 > 2.0F)
			    {
				int k = (int) (f5 * 0.5D);
				((WorldServer) player.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX,
					targetEntity.posY + targetEntity.height * 0.5F, targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D);
			    }
			}

			player.addExhaustion(0.1F);
		    }
		    else
		    {
			player.world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE,
				player.getSoundCategory(), 1.0F, 1.0F);

			if (hasFireAspect)
			{
			    targetEntity.extinguish();
			}
		    }
		}
	    }
	}
    }
}
