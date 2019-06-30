package com.barribob.MaelstromMod.items.gun.bullet;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.items.gun.ItemGun;

import ibxm.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface BulletFactory
{
    public Projectile get(World world, EntityPlayer player, ItemStack stack, ItemGun item);
}
