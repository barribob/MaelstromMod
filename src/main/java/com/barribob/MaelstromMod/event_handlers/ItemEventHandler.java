package com.barribob.MaelstromMod.event_handlers;

import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.particleSpawners.ParticleSpawnerRainbow;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.init.ModPotions;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.items.tools.ToolSword;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageMana;
import com.barribob.MaelstromMod.util.ModUtils;
import com.google.common.collect.Multimap;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber()
public class ItemEventHandler
{
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onTooltipEvent(ItemTooltipEvent event)
    {
	ItemStack stack = event.getItemStack();
	if (stack.getItem() instanceof ISweepAttackOverride)
	{
	    int sharpness = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.sharpness_2, stack);
	    if (sharpness > 0 && event.getEntityPlayer() != null)
	    {
		Multimap<String, AttributeModifier> multimap = stack.getAttributeModifiers(EntityEquipmentSlot.MAINHAND);
		AttributeModifier modifier = null;
		for (Entry<String, AttributeModifier> entry : multimap.entries())
		{
		    AttributeModifier attributemodifier = entry.getValue();
		    if (attributemodifier.getID() == ToolSword.getAttackDamageModifier())
		    {
			List<String> newTooltip = event.getToolTip().stream().map((tooltip) -> {
			    String tooltipStub = I18n.translateToLocal("attribute.name." + entry.getKey());
			    if (tooltip.contains(tooltipStub))
			    {
				double d0 = attributemodifier.getAmount();
				d0 = d0 + event.getEntityPlayer().getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue();
				d0 = d0 + EnchantmentHelper.getModifierForCreature(stack, EnumCreatureAttribute.UNDEFINED);
				d0 += ModUtils.getSwordEnchantmentDamage(stack);
				String t = I18n.translateToLocalFormatted("attribute.modifier.equals." + attributemodifier.getOperation(), stack.DECIMALFORMAT.format(d0),
					I18n.translateToLocal("attribute.name." + entry.getKey()));
				return t;
			    }
			    return tooltip;
			}).collect(Collectors.toList());
			event.getToolTip().clear();
			event.getToolTip().addAll(newTooltip);
		    }
		}
	    }
	}
    }

    @SubscribeEvent
    public static void onLivingUpdateEvent(LivingEvent.LivingUpdateEvent event)
    {
	if (event.getEntity() instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) event.getEntity();
	    Item heldItem = player.getHeldItem(EnumHand.MAIN_HAND).getItem();
	    Item offhandItem = player.getHeldItem(EnumHand.OFF_HAND).getItem();

	    Item helmet = player.inventory.armorInventory.get(3).getItem();
	    Item chestplate = player.inventory.armorInventory.get(2).getItem();
	    Item leggings = player.inventory.armorInventory.get(1).getItem();
	    Item boots = player.inventory.armorInventory.get(0).getItem();

	    if ((heldItem.equals(ModItems.BAKUYA) && offhandItem.equals(ModItems.KANSHOU)) || (heldItem.equals(ModItems.KANSHOU) && offhandItem.equals(ModItems.BAKUYA)))
	    {
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 20, 1));
	    }

	    if (!player.world.isRemote && (heldItem.equals(ModItems.CROSS_OF_AQUA) || offhandItem.equals(ModItems.CROSS_OF_AQUA)))
	    {
		IMana mana = player.getCapability(ManaProvider.MANA, null);
		if (!mana.isLocked() && mana.getMana() > 0)
		{
		    if (!player.capabilities.isCreativeMode && player.ticksExisted % 40 == 0)
		    {
			mana.consume(1);
			Main.network.sendTo(new MessageMana(mana.getMana()), (EntityPlayerMP) player);
		    }
		    player.addPotionEffect(new PotionEffect(ModPotions.water_strider, 40));
		}
	    }

	    if (helmet.equals(ModItems.NYAN_HELMET) &&
		    chestplate.equals(ModItems.NYAN_CHESTPLATE) &&
		    leggings.equals(ModItems.NYAN_LEGGINGS) &&
		    boots.equals(ModItems.NYAN_BOOTS) && player.isSprinting())
	    {
		Entity particle = new ParticleSpawnerRainbow(player.world);
		Vec3d pos = player.getPositionVector().subtract(new Vec3d(player.getLookVec().x, 0, player.getLookVec().z));
		particle.setPosition(pos.x, pos.y, pos.z);
		player.world.spawnEntity(particle);
	    }

	    if (!player.world.isRemote &&
		    helmet.equals(ModItems.BLACK_GOLD_HELMET) &&
		    chestplate.equals(ModItems.BLACK_GOLD_CHESTPLATE) &&
		    leggings.equals(ModItems.BLACK_GOLD_LEGGINGS) &&
		    boots.equals(ModItems.BLACK_GOLD_BOOTS) &&
		    player.ticksExisted % 40 == 0)
	    {
		player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 60, 0));
	    }

	    if (!player.world.isRemote &&
		    helmet.equals(ModItems.MAELSTROM_HELMET) &&
		    chestplate.equals(ModItems.MAELSTROM_CHESTPLATE) &&
		    leggings.equals(ModItems.MAELSTROM_LEGGINGS) &&
		    boots.equals(ModItems.MAELSTROM_BOOTS) &&
		    player.ticksExisted % 40 == 0)
	    {
		player.addPotionEffect(new PotionEffect(MobEffects.RESISTANCE, 60, 0));
	    }

	    if (helmet.equals(ModItems.GOLTOX_HELMET) &&
		    chestplate.equals(ModItems.GOLTOX_CHESTPLATE) &&
		    leggings.equals(ModItems.GOLTOX_LEGGINGS) &&
		    boots.equals(ModItems.GOLTOX_BOOTS) &&
		    player.ticksExisted % 20 == 0)
	    {
		if (player.isPotionActive(MobEffects.POISON))
		{
		    PotionEffect effect = player.getActivePotionEffect(MobEffects.POISON);
		    player.removeActivePotionEffect(MobEffects.POISON);
		    player.addPotionEffect(new PotionEffect(MobEffects.POISON, effect.getDuration() - 20, effect.getAmplifier()));
		}
	    }

	    if (!player.world.isRemote &&
		    helmet.equals(ModItems.SWAMP_HELMET) &&
		    chestplate.equals(ModItems.SWAMP_CHESTPLATE) &&
		    leggings.equals(ModItems.SWAMP_LEGGINGS) &&
		    boots.equals(ModItems.SWAMP_BOOTS) &&
		    player.ticksExisted % 40 == 0)
	    {
		player.addPotionEffect(new PotionEffect(MobEffects.STRENGTH, 60, 0));
	    }
	}
    }
}
