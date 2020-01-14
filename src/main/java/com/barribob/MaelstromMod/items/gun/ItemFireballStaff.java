package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.items.gun.bullet.BulletFactory;
import com.barribob.MaelstromMod.items.gun.bullet.Fireball;
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

public class ItemFireballStaff extends ItemStaff
{
    private float baseDamage;
    private BulletFactory factory = new Fireball();

    public ItemFireballStaff(String name, int useTime, float level, CreativeTabs tab)
    {
	super(name, 6, 40, useTime, level, tab);
	this.baseDamage = 10 * ModConfig.balance.weapon_damage;
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.NEUTRAL, 1.0F,
		0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 2.0f;
	float velocity = 1.3f;

	Projectile projectile = factory.get(world, player, stack, ModUtils.getEnchantedDamage(stack, this.getLevel(), baseDamage));
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
	projectile.setTravelRange(25);

	world.spawnEntity(projectile);
    }
    
    public ItemFireballStaff setFactory(BulletFactory factory)
    {
	this.factory = factory;
	return this;
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(ModUtils.getDamageTooltip(ModUtils.getEnchantedDamage(stack, this.getLevel(), this.baseDamage)));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("fireball_staff"));
    }

    @Override
    public boolean doesDamage()
    {
	return true;
    }
}
