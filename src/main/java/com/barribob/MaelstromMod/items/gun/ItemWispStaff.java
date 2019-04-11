package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.ProjectileWillOTheWisp;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

/**
 * 
 * A short range staff that burns enemies
 *
 */
public class ItemWispStaff extends ItemGun
{
    public ItemWispStaff(String name, int cooldown, int maxDamage, float level, CreativeTabs tab)
    {
	super(name, cooldown, 5, maxDamage, null, level, tab);
    }

    /**
     * Shoot a bunch of projectiles
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 0.0f;
	float speed = 1f;

	ProjectileWillOTheWisp projectile = new ProjectileWillOTheWisp(world, player, this.getEnchantedDamage(stack), stack);
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, speed, inaccuracy);
	projectile.setTravelRange(9f);

	world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + "Short range piercing flame attack");
    }

    @Override
    protected void spawnShootParticles(World worldIn, EntityPlayer playerIn, EnumHand handIn)
    {
    }
}
