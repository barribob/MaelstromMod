package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.init.ModEnchantments;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageMana;
import com.barribob.MaelstromMod.util.ModUtils;
import com.typesafe.config.Config;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

@Mod.EventBusSubscriber()
public class ItemToManaSystem {

    @SubscribeEvent
    public static void onAttemptToUseItem(PlayerInteractEvent.RightClickItem event) {

        if(event.getSide() == Side.CLIENT) return;

        ItemStack itemStack = event.getItemStack();
        Config config = getManaConfig(itemStack);

        if(config != null) {
            int manaCost = config.getInt("mana_cost");
            NBTTagCompound compound = itemStack.getTagCompound();
            EntityPlayer player = event.getEntityPlayer();
            IMana manaCapability = player.getCapability(ManaProvider.MANA, null);
            float mana = manaCapability.getMana();

            if (manaCapability.isLocked() && manaCost != 0 && !player.capabilities.isCreativeMode) {
                cancelUse(event, player, true);
                return;
            }

            if (compound == null || !compound.hasKey("cooldown")) {
                cancelUse(event, player, false);
                return;
            }

            int cooldown = compound.getInteger("cooldown");
            if(cooldown > 0) {
                cancelUse(event, player, false);
                return;
            }

            if(mana < manaCost && !player.capabilities.isCreativeMode) {
                cancelUse(event, player, false);
                return;
            }

            if (!player.capabilities.isCreativeMode && manaCost != 0) {
                manaCapability.consume(manaCost);
                Main.network.sendTo(new MessageMana(manaCapability.getMana()), (EntityPlayerMP) player);
            }
            compound.setInteger("cooldown", (int) getEnchantedCooldown(itemStack));
            itemStack.setTagCompound(compound);
        }
    }

    private static void cancelUse(PlayerInteractEvent.RightClickItem event, EntityPlayer player, boolean sendMessage) {
        if(sendMessage) {
            player.sendMessage(new TextComponentTranslation("mana_locked"));
        }
        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.FAIL);
    }

    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntity();

            for(ItemStack itemStack : player.inventory.mainInventory) {
                updateCooldowns(itemStack);
            }
            for(ItemStack itemStack : player.inventory.offHandInventory) {
                updateCooldowns(itemStack);
            }
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        Config config = getManaConfig(event.getItemStack());
        if (config != null) {
            List<String> tooltips = event.getToolTip();
            ResourceLocation registryName = event.getItemStack().getItem().getRegistryName();
            Optional<String> nbtTooltips = tooltips.stream().filter(tooltip ->
                    registryName != null && tooltip.contains(registryName.toString())).findFirst();

            String cooldownTooltip = ModUtils.getCooldownTooltip(ItemToManaSystem.getEnchantedCooldown(event.getItemStack()));
            int manaCost = config.getInt("mana_cost");
            String manaTooltip = TextFormatting.GRAY + ModUtils.translateDesc("mana_cost") + ": " + TextFormatting.DARK_PURPLE + manaCost;

            if (nbtTooltips.isPresent()) {
                int index = tooltips.indexOf(nbtTooltips.get());
                addTooltips(tooltips, cooldownTooltip, manaCost, manaTooltip, index);
            } else {
                addTooltips(tooltips, cooldownTooltip, manaCost, manaTooltip, tooltips.size());
            }
        }
    }

    private static void addTooltips(List<String> tooltips, String cooldownTooltip, int manaCost, String manaTooltip, int index) {
        if(manaCost != 0) {
            tooltips.add(index, manaTooltip);
        }
        tooltips.add(index, cooldownTooltip);
    }

    private static void updateCooldowns(ItemStack stack) {
        Config config = getManaConfig(stack);
        if (config != null) {
            NBTTagCompound compound = stack.getTagCompound();
            if(compound == null) {
                compound = new NBTTagCompound();
            }

            if (compound.hasKey("cooldown")) {
                int cooldown = compound.getInteger("cooldown") - 1;
                compound.setInteger("cooldown", Math.max(cooldown, 0));
            } else {
                int initialCooldown = (int) getEnchantedCooldown(stack);
                compound.setInteger("cooldown", initialCooldown);
            }

            stack.setTagCompound(compound);
        }
    }

    @Nullable
    public static Config getManaConfig(ItemStack itemStack) {
        ResourceLocation registryName = itemStack.getItem().getRegistryName();
        if(registryName != null) {
            String registryPath = registryName.toString().replace(':', '.');
            if (Main.manaConfig.hasPath(registryPath)) {
                return Main.manaConfig.getConfig(registryPath);
            }
        }

        return null;
    }

    public static float getEnchantedCooldown(ItemStack stack) {
        Config config = getManaConfig(stack);
        if (config == null) return 0;
        int reload = EnchantmentHelper.getEnchantmentLevel(ModEnchantments.reload, stack);
        return config.getInt("cooldown_in_ticks") * (1 - reload * 0.1f);
    }

    public static float getCooldownForDisplay(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("cooldown")) {
            float enchantedCooldown = getEnchantedCooldown(stack);
            if(enchantedCooldown == 0) return 0;
            return stack.getTagCompound().getInteger("cooldown") / enchantedCooldown;
        }

        return 0;
    }
}
