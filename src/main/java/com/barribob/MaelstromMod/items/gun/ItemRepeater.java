package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.entity.projectile.Projectile;
import com.barribob.MaelstromMod.entity.projectile.ProjectileRepeater;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemRepeater extends ItemGun {
    int maxRepeats = 5;

    public ItemRepeater(String name, float level) {
        super(name, 60, 2, level);
    }

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);

        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("repeating")) {
            NBTTagCompound compound = stack.getTagCompound();
            if (!worldIn.isRemote && compound.getBoolean("repeating") && entityIn instanceof EntityPlayer && entityIn.ticksExisted % 5 == 0) {
                this.repeat(worldIn, (EntityPlayer) entityIn, stack);
                compound.setInteger("repeats", compound.getInteger("repeats") + 1);
                if (compound.getInteger("repeats") >= this.maxRepeats) {
                    compound.setBoolean("repeating", false);
                    compound.setInteger("repeats", 0);
                }
            }
        }
    }

    private void repeat(World world, EntityPlayer player, ItemStack stack) {
        world.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.NEUTRAL, 0.5F,
                0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

        float inaccuracy = 4.0f;
        float velocity = 3.0f;

        Projectile projectile = new ProjectileRepeater(world, player, this.getEnchantedDamage(stack), stack);
        projectile.setElement(getElement());
        projectile.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, velocity, inaccuracy);
        projectile.setTravelRange(30);

        world.spawnEntity(projectile);
    }

    @Override
    protected void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack) {
        if (stack.hasTagCompound()) {
            stack.getTagCompound().setBoolean("repeating", true);
        }
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltip.add(TextFormatting.GRAY + ModUtils.translateDesc("repeater"));
    }
}
