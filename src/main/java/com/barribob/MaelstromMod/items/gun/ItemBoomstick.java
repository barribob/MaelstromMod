package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.util.ModRandom;
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

/**
 * 
 * The shotgun like weapon
 *
 */
public class ItemBoomstick extends ItemGun
{
    protected float pelletCount = 15;

    public ItemBoomstick(String name, int cooldown, int maxDamage, float level, CreativeTabs tab)
    {
	super(name, cooldown, 1, maxDamage, level, tab);
    }

    /**
     * Shoot a bunch of projectiles
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	for (int i = 0; i < pelletCount; i++)
	{
	    float inaccuracy = 0.0f;
	    float speed = 3f;
	    float pitch = player.rotationPitch + ModRandom.getFloat(15);
	    float yaw = player.rotationYaw + ModRandom.getFloat(15);

	    Projectile projectile = factory.get(world, player, stack, this.getEnchantedDamage(stack));
	    projectile.shoot(player, pitch, yaw, 0.0F, speed, inaccuracy);
	    projectile.setTravelRange(25f);

	    world.spawnEntity(projectile);
	}
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("boomstick"));
    }
}
