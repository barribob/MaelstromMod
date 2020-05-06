package com.barribob.MaelstromMod.items.gun;

import java.util.List;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRuneWisp;
import com.barribob.MaelstromMod.init.ModCreativeTabs;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModUtils;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemRuneStaff extends ItemStaff
{
    public ItemRuneStaff(String name, float level)
    {
	super(name, 5, 40, ModItems.STAFF_USE_TIME, level, ModCreativeTabs.ITEMS);
    }

    public float getBaseDamage()
    {
	return 6 * ModConfig.balance.weapon_damage;
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack)
    {
	world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_BLAZE_AMBIENT, SoundCategory.NEUTRAL, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

	float inaccuracy = 0.0f;
	float speed = 1f;

	ProjectileRuneWisp projectile = new ProjectileRuneWisp(world, player, ModUtils.getEnchantedDamage(stack, getLevel(), getBaseDamage()), stack);
	projectile.setElement(getElement());
	projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, speed, inaccuracy);
	projectile.setTravelRange(9f);

	world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn)
    {
	super.addInformation(stack, worldIn, tooltip, flagIn);
	tooltip.add(ModUtils.getDamageTooltip(ModUtils.getEnchantedDamage(stack, this.getLevel(), getBaseDamage())));
	tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("rune_staff"));
    }

    @Override
    public boolean doesDamage()
    {
	return true;
    }
}
