package com.barribob.MaelstromMod.items.gun.bullet;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRepeater;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RedstoneRepeater implements BulletFactory
{
    @Override
    public Projectile get(World world, EntityPlayer player, ItemStack stack, float damage)
    {
	return new ProjectileRepeater(world, player, damage, stack);
    }
}
