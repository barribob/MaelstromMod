package com.barribob.MaelstromMod.items.armor;

import java.util.List;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
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
	if (itemStack != null && itemStack.getItem() == this)
	{
	    wornEffect = new PotionEffect(MobEffects.SPEED, 20, 0);
	    player.addPotionEffect(wornEffect);
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);

	String potion = TextFormatting.BLUE + I18n.translateToLocal(wornEffect.getEffectName()).trim();
	if (wornEffect.getAmplifier() > 0)
	{
	    potion = potion + " " + I18n.translateToLocal("potion.potency." + wornEffect.getAmplifier()).trim();
	}
	tooltip.add(ModUtils.translateDesc("potion_add", potion));
    }
}
