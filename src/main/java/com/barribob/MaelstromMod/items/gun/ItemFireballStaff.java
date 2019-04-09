package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileFireball;
import com.barribob.MaelstromMod.init.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemFireballStaff extends ItemGun
{    
    public ItemFireballStaff(String name, int cooldown, int useTime, float level, CreativeTabs tab)
    {
	super(name, cooldown, useTime, ModItems.IRON_PELLET, level, tab);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 1.0F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 2.0f;
	float velocity = 1.3f;
	float damage = 7;

	ProjectileFireball projectile = new ProjectileFireball(world, player, damage * this.getMultiplier(), stack);
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(25);

	world.spawnEntity(projectile);
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "Short Range fireball");
    }
    
    @Override
    protected void spawnShootParticles(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    }
}
