package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileBullet;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRepeater;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRepeater extends ItemGun
{    
    boolean repeating;
    int maxRepeats = 5;
    int repeats;
    
    public ItemRepeater(String name, int cooldown, int useTime, float level, CreativeTabs tab)
    {
	super(name, cooldown, 2, useTime, Items.REDSTONE, level, tab);
    }
    
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected)
    {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
        
        if(!worldIn.isRemote && this.repeating && entityIn instanceof EntityPlayer && entityIn.ticksExisted % 5 == 0)
        {
            this.repeat(worldIn, (EntityPlayer)entityIn, stack);
            this.repeats++;
            if(this.repeats >= this.maxRepeats)
            {
        	this.repeating = false;
        	this.repeats = 0;
            }
        }
    }
    
    private void repeat(World world, EntityPlayer player, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 4.0f;
	float velocity = 3.0f;

	ProjectileRepeater projectile = new ProjectileRepeater(world, player, this.getEnchantedDamage(stack), stack);
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(30);

	world.spawnEntity(projectile);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	this.repeating = true;
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "Short Range repeating gun");
    }
}
