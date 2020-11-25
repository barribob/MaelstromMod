package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.packets.MessageLeap;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemLeapStaff extends ItemStaff {
    public ItemLeapStaff(String name, int maxDamage, float level, CreativeTabs tab) {
        super(name, maxDamage, level, tab);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
        if(player instanceof EntityPlayerMP) {
            world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ENDERDRAGON_FLAP, SoundCategory.NEUTRAL, 0.5F,
                    0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
            Main.network.sendTo(new MessageLeap(), (EntityPlayerMP) player);
            player.fallDistance = -1;
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("leap_staff"));
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("fall_damage_reduction"));
    }
}
