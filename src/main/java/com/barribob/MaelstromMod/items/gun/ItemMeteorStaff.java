package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileMeteorSpawner;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMeteorStaff extends ItemStaff
{
    private float baseDamage = 10;

    public ItemMeteorStaff(String name, int cooldown, int useTime, float level, CreativeTabs tab)
    {
	super(name, 6, cooldown, useTime, level, tab);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	float inaccuracy = 0.0f;
	float velocity = 3f;

	Projectile projectile = new ProjectileMeteorSpawner(world, player, ModUtils.getEnchantedDamage(stack, this.getLevel(), baseDamage), stack);
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(50);

	world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(ModUtils.getDamageTooltip(ModUtils.getEnchantedDamage(stack, this.getLevel(), this.baseDamage)));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("meteor_staff"));
    }

    @Override
    public boolean doesDamage()
    {
	return true;
    }
}
