package com.barribob.MaelstromMod.items.gun;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.event_handlers.ItemToManaSystem;
import com.barribob.MaelstromMod.items.ILeveledItem;
import com.barribob.MaelstromMod.items.ItemBase;
import com.barribob.MaelstromMod.util.Element;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.ModUtils;
import com.typesafe.config.Config;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public abstract class ItemStaff extends ItemBase implements ILeveledItem, IElement {
    private final float level;
    private Element element = Element.NONE;

    public ItemStaff(String name, float useTime, float level, CreativeTabs tab) {
        super(name, tab);
        this.maxStackSize = 1;
        this.level = level;
        Config config = ItemToManaSystem.getManaConfig(new ItemStack(this));
        if(config != null) {
            this.setMaxDamage((int) (useTime / Math.max(1, config.getInt("cooldown_in_ticks"))));
        }
    }

    /**
     * If true, this item can be enchanted with damage enchantments like guns
     */
    public boolean doesDamage() {
        return false;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemStack = playerIn.getHeldItem(handIn);
        if(!worldIn.isRemote) {
            itemStack.damageItem(1, playerIn);
            shoot(worldIn, playerIn, handIn, itemStack);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if(!ModConfig.gui.disableMaelstromArmorItemTooltips) {
            tooltip.add(ModUtils.getDisplayLevel(this.level));
        }
        if (!element.equals(Element.NONE) && !ModConfig.gui.disableElementalVisuals) {
            tooltip.add(ModUtils.getElementalTooltip(element));
        }
    }

    /**
     * Return the enchantability factor of the item, most of the time is based on
     * material.
     */
    @Override
    public int getItemEnchantability() {
        return 1;
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    @SideOnly(Side.CLIENT)
    public boolean isFull3D() {
        return true;
    }

    @Override
    public float getLevel() {
        return this.level;
    }

    @Override
    public Element getElement() {
        return element;
    }

    public ItemStaff setElement(Element element) {
        this.element = element;
        return this;
    }

    protected abstract void shoot(World world, EntityPlayer player, EnumHand handIn, ItemStack stack);
}
