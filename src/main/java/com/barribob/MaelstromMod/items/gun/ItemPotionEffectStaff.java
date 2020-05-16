package com.barribob.MaelstromMod.items.gun;

import java.util.List;
import java.util.function.Supplier;

import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * A staff designed to activate potion effects when used
 */
public class ItemPotionEffectStaff extends ItemStaff {
    Supplier<PotionEffect[]> effects;
    String desc_loc;

    public ItemPotionEffectStaff(String name, float level, int experienceUse, int cooldown, Supplier<PotionEffect[]> effects, String desc_loc) {
	super(name, experienceUse, cooldown, ModItems.STAFF_USE_TIME, level, ModCreativeTabs.ITEMS);
	this.effects = effects;
	this.desc_loc = desc_loc;
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
	world.playSound(null, player.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0f, 1.0f + ModRandom.getFloat(0.2f));
	for (PotionEffect effect : effects.get()) {
	    player.addPotionEffect(effect);
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc(desc_loc));
    }
}
