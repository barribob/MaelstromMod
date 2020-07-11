package com.barribob.MaelstromMod.items;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.Mana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageMana;
import com.barribob.MaelstromMod.packets.MessageManaUnlock;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemCatalyst extends ItemBase {
    public ItemCatalyst(String name, CreativeTabs tab) {
        super(name, tab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            IMana mana = playerIn.getCapability(ManaProvider.MANA, null);
            if (mana.isLocked()) {
                mana.setLocked(false);
                mana.set(Mana.MAX_MANA); // Fill the mana bar initially
                Main.network.sendTo(new MessageManaUnlock(), (EntityPlayerMP) playerIn); // Spawn celebratory particles
                Main.network.sendTo(new MessageMana(mana.getMana()), (EntityPlayerMP) playerIn); // Update the mana bar
                playerIn.playSound(SoundEvents.ENTITY_ILLAGER_CAST_SPELL, 1.0F, 0.4F / (worldIn.rand.nextFloat() * 0.4F + 0.8F));

                if (!playerIn.capabilities.isCreativeMode) {
                    itemstack.shrink(1);
                    return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
                }
            }
        }
        return new ActionResult<ItemStack>(EnumActionResult.PASS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("catalyst"));
    }
}
