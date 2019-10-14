package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemLeapStaff extends ItemGun
{
    public ItemLeapStaff(String name, int cooldown, int maxDamage, float level, CreativeTabs tab)
    {
	super(name, cooldown, 0, maxDamage, null, level, tab);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	player.fallDistance = -1;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("leap_staff"));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("fall_damage_reduction"));
    }

    @Override
    protected void spawnShootParticles(World worldIn, EntityPlayer player, EnumHand handIn)
    {
	float maxVelocityIncrease = 0.6f;
	player.addVelocity(Math.min(Math.max(player.motionX, -maxVelocityIncrease), maxVelocityIncrease), 0.8f, Math.min(Math.max(player.motionZ, -maxVelocityIncrease), maxVelocityIncrease));
	player.motionY = Math.min(1.0f, player.motionY);
    }
}
