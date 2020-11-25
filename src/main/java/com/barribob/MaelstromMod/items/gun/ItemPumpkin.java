package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.entity.projectile.ProjectilePumpkin;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemPumpkin extends ItemGun {
    public ItemPumpkin(String name, int cooldown, Item ammo, float level) {
        super(name, cooldown, 0.5f, level);
    }

    /**
     * Shoot a single bullet
     */
    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.NEUTRAL, 0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        float inaccuracy = 0.0f;
        float velocity = 7.0f;

        ProjectilePumpkin projectile = new ProjectilePumpkin(world, player, this.getEnchantedDamage(stack), stack);
        projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
        projectile.setTravelRange(1000f);

        world.spawnEntity(projectile);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("pumpkin"));
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("pumpkin_damage"));
    }

    @Override
    protected void getDamageTooltip(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(ModUtils.translateDesc("damage_per_meter_tooltip", TextFormatting.BLUE + ModUtils.DF_0.format(this.getEnchantedDamage(stack)) + TextFormatting.GRAY));
    }
}
