package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileQuake;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * A short range quake attack
 *
 */
public class ItemQuakeStaff extends ItemGun
{
    public ItemQuakeStaff(String name, int cooldown, int maxDamage, float level, CreativeTabs tab)
    {
	super(name, cooldown, 6, maxDamage, null, level, tab);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	float inaccuracy = 0.0f;
	float speed = 0.5f;
	float pitch = 0; // Projectiles aim straight ahead always

	// Shoots projectiles in a small arc
	for (int i = 0; i < 5; i++)
	{
	    ProjectileQuake projectile = new ProjectileQuake(world, player, this.getEnchantedDamage(stack), stack);
	    projectile.setPosition(player.posX, player.posY, player.posZ);
	    projectile.shoot(player, pitch, player.rotationYaw - 20 + (i * 10), 0.0F, speed, inaccuracy);
	    projectile.setTravelRange(8f);
	    world.spawnEntity(projectile);
	}
    }

    @Override
    protected void spawnShootParticles(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    }
    
    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
        super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("quake_staff"));
    }
}
