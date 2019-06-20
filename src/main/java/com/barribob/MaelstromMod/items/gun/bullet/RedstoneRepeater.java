package com.barribob.MaelstromMod.items.gun.bullet;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRepeater;
import com.barribob.MaelstromMod.items.gun.ItemGun;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RedstoneRepeater implements BulletFactory
{
    @Override
    public Projectile get(World world, EntityPlayer player, ItemStack stack, ItemGun item)
    {
	return new ProjectileRepeater(world, player, item.getEnchantedDamage(stack), stack);
    }
}
