package com.barribob.MaelstromMod.event_handlers;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.gui.InGameGui;
import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.packets.MessageExtendedReachAttack;
import com.barribob.MaelstromMod.player.PlayerMeleeAttack;
import com.barribob.MaelstromMod.renderer.InputOverrides;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.handlers.ArmorHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
/**
 * 
 * Holds various important functionalities only accessible through the forge
 * event system
 *
 */
@Mod.EventBusSubscriber()
public class ModEventHandler
{
    @SubscribeEvent(receiveCanceled = true)
    public static void onAttackEntityEvent(AttackEntityEvent event)
    {
	// Overrides the melee attack of the player if the item used is the sweep attack
	// override interface
	if (event.getEntityPlayer().getHeldItemMainhand().getItem() instanceof ISweepAttackOverride)
	{
	    PlayerMeleeAttack.attackTargetEntityWithCurrentItem(event.getEntityPlayer(), event.getTarget());
	    event.setCanceled(true);
	}
	else
	{
	    event.setCanceled(false);
	}
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent(receiveCanceled = true)
    public static void onMouseEvent(MouseEvent event)
    {
	// If left clicking
	if (event.getButton() == 0 && event.isButtonstate())
	{
	    Minecraft mc = Minecraft.getMinecraft();
	    EntityPlayer player = mc.player;

	    if (player != null)
	    {
		ItemStack itemStack = player.getHeldItemMainhand();

		// If the item has extended reach, apply that, and send the attack
		// to the server to verify
		if (itemStack != null && itemStack.getItem() instanceof IExtendedReach)
		{
		    float reach = ((IExtendedReach) itemStack.getItem()).getReach();
		    RayTraceResult result = InputOverrides.getMouseOver(1.0f, mc, reach);

		    if (result != null && result.typeOfHit == RayTraceResult.Type.ENTITY)
		    {
			Main.network.sendToServer(new MessageExtendedReachAttack(result.entityHit.getEntityId()));
		    }
		    else
		    {
			net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(mc.player);
		    }

		    // Because the actual animation is canceled as well, the methods below let the
		    // client animate properly
		    mc.player.resetCooldown();
		    mc.player.swingArm(EnumHand.MAIN_HAND);
		    event.setCanceled(true);// Prevents shorter reach swords from hitting with the event going through
		    return;
		}
	    }
	}

	event.setCanceled(false);
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
	// Factor in maelstrom armor into damage source
	if (!event.getSource().isUnblockable())
	{
	    event.setAmount(event.getAmount() * (1 - ArmorHandler.getMaelstromArmor(event.getEntity())));

	    if (ModDamageSource.isMaelstromDamage(event.getSource()))
	    {
		event.setAmount(event.getAmount() * (1 - ArmorHandler.getMaelstromProtection(event.getEntity())));
	    }
	}
    }

    /**
     * Renders the maelstrom armor bar and the gun reload bar
     */
    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onGuiPostRender(RenderGameOverlayEvent.Post event)
    {
	Minecraft mc = Minecraft.getMinecraft();
	if (mc.getRenderViewEntity() instanceof EntityPlayer)
	{
	    GlStateManager.enableBlend();
	    EntityPlayer player = (EntityPlayer) mc.getRenderViewEntity();

	    // If in creative mode or something, don't draw
	    if (mc.playerController.shouldDrawHUD())
	    {
		InGameGui.renderArmorBar(mc, event, player);
	    }

	    if (ModConfig.gui.showGunCooldownBar)
	    {
		InGameGui.renderGunReload(mc, event, player);
	    }
	}
    }
}
