package com.barribob.MaelstromMod.items.armor;

import java.util.List;

import com.barribob.MaelstromMod.init.ModItems;
import com.google.common.collect.Multimap;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemSpeedBoots extends ModArmorBase
{
    private PotionEffect wornEffect = new PotionEffect(MobEffects.SPEED, 20, 0);
    public ItemSpeedBoots(String name, ArmorMaterial materialIn, int renderIndex, EntityEquipmentSlot equipmentSlotIn, float maelstrom_armor, String textureName)
    {
	super(name, materialIn, renderIndex, equipmentSlotIn, maelstrom_armor, textureName);
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack)
    {
	super.onArmorTick(world, player, itemStack);
	ItemStack boots = player.inventory.armorItemInSlot(0);
	if (boots != null)
	{
	    if (boots.getItem() == this)
	    {
		player.addPotionEffect(wornEffect);
	    }
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "Adds " + TextFormatting.BLUE + "Speed 1");
    }
}
