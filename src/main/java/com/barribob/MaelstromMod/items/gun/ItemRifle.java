package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.entity.projectile.Projectile;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemRifle extends ItemGun
{    
    public ItemRifle(String name, float level)
    {
	super(name, 60, 9, level);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 0.0f;
	float velocity = 6.0f;

	Projectile projectile = factory.get(world, player, stack, this.getEnchantedDamage(stack));
	projectile.setElement(getElement());
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(100);

	world.spawnEntity(projectile);
    }
}
