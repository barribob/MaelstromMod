package com.barribob.MaelstromMod.event_handlers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.gui.InGameGui;
import com.barribob.MaelstromMod.invasion.InvasionWorldSaveData;
import com.barribob.MaelstromMod.items.IExtendedReach;
import com.barribob.MaelstromMod.items.ISweepAttackOverride;
import com.barribob.MaelstromMod.mana.IMana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.packets.MessageExtendedReachAttack;
import com.barribob.MaelstromMod.packets.MessageSyncConfig;
import com.barribob.MaelstromMod.player.PlayerMeleeAttack;
import com.barribob.MaelstromMod.renderer.InputOverrides;
import com.barribob.MaelstromMod.util.GenUtils;
import com.barribob.MaelstromMod.util.IElement;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.ArmorHandler;
import com.barribob.MaelstromMod.util.teleporter.NexusToOverworldTeleporter;
import com.barribob.MaelstromMod.world.gen.WorldGenCustomStructures;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.WorldType;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
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
    public static final ResourceLocation MANA = new ResourceLocation(Reference.MOD_ID, "mana");
    // public static final ResourceLocation INVASION = new
    // ResourceLocation(Reference.MOD_ID, "invasion");

    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerLoggedInEvent event)
    {
	// Sync some of the config parameters
	if (ModConfig.server.sync_on_login)
	{
	    Main.network.sendTo(new MessageSyncConfig(ModConfig.balance.progression_scale, ModConfig.balance.weapon_damage, ModConfig.balance.armor_toughness, ModConfig.balance.elemental_factor), (EntityPlayerMP) event.player);
	}
    }

    @SubscribeEvent
    public static void onServerTick(TickEvent.WorldTickEvent event)
    {
	// Only tick in the overworld
	if (event.side == Side.SERVER && event.world.provider.getDimension() == 0 && event.phase == TickEvent.Phase.END)
	{
	    // Do not generate the structure on superflat worlds
	    if (event.world.getWorldType().equals(WorldType.FLAT))
	    {
		return;
	    }

	    InvasionWorldSaveData invasionCounter = ModUtils.getInvasionData(event.world);
	    invasionCounter.update();

	    // Issue a warning one tenth of the time left
	    if (invasionCounter.getInvasionTime() == ModConfig.world.invasionTime * 20 * 0.1f)
	    {
		event.world.playerEntities.forEach((p) -> {
		    p.sendMessage(
			    new TextComponentString("" + TextFormatting.DARK_PURPLE + new TextComponentTranslation(Reference.MOD_ID + ".invasion_1").getFormattedText()));
		});
	    }

	    if (invasionCounter.shouldDoInvasion())
	    {
		if (event.world.playerEntities.size() > 0)
		{
		    // Get the player closest to the origin
		    EntityPlayer player = event.world.playerEntities.stream().reduce(event.world.playerEntities.get(0),
			    (p1, p2) -> p1.getDistance(0, 0, 0) < p2.getDistance(0, 0, 0) ? p1 : p2);

		    List<BlockPos> positions = new ArrayList<BlockPos>();
		    List<Integer> variations = new ArrayList<Integer>();

		    // Find the flattest area
		    ModUtils.circleCallback(50, 16, (pos) -> {
			BlockPos structureSize = WorldGenCustomStructures.invasionTower.getSize(event.world);
			BlockPos structurePos = new BlockPos(player.getPositionVector().x, 0, player.getPositionVector().z); // Start with player xz position
			BlockPos mainTowerSize = new BlockPos(structureSize.getX() * 0.5f, 0, structureSize.getZ() * 0.5f);

			structurePos = structurePos.add(new BlockPos(pos.x, 0, pos.y)); // Add the circle position
			structurePos = structurePos.subtract(new BlockPos(mainTowerSize)); // Center the structure

			// The tower template edges are not very good indicators for what the height
			// should be.
			// This adjusts so that the height is based more on the center of the tower
			int y = ModUtils.getAverageGroundHeight(event.world, structurePos.getX() + (int) (mainTowerSize.getX() * 0.5f),
				structurePos.getZ() + (int) (mainTowerSize.getZ() * 0.5f), mainTowerSize.getX(), mainTowerSize.getZ(), 8);

			// There is too much terrain variation for the tower to be here
			if (y == -1 || y > NexusToOverworldTeleporter.yPortalOffset - structureSize.getY())
			{
			    return;
			}

			// Add the y height
			final BlockPos finalPos = structurePos.add(new BlockPos(0, y, 0));

			// Avoid spawning in water (mostly for oceans because they can be very deep)
			if (event.world.containsAnyLiquid(new AxisAlignedBB(finalPos, structureSize.add(finalPos))))
			{
			    return;
			}

			// Try to avoid bases with beds (spawnpoints) in them
			boolean baseNearby = event.world.playerEntities.stream().anyMatch((p) -> {
			    if (event.world.getSpawnPoint().equals(p.getBedLocation()) || p.getBedLocation() == null)
			    {
				return false;
			    }
			    return finalPos.distanceSq(p.getBedLocation()) < Math.pow(75, 2);
			});

			if (!baseNearby)
			{
			    int terrainVariation = GenUtils.getTerrainVariation(event.world, finalPos.getX(), finalPos.getZ(), finalPos.getX(),
				    structureSize.getZ());
			    positions.add(finalPos.down(3));
			    variations.add(terrainVariation);
			}
		    });

		    if (positions.size() > 0)
		    {
			event.world.playerEntities.forEach((p) -> {
			    p.sendMessage(new TextComponentString(
				    "" + TextFormatting.DARK_PURPLE + new TextComponentTranslation(Reference.MOD_ID + ".invasion_2").getFormattedText()));
			});
			invasionCounter.setInvaded(true);
			BlockPos structurePos = positions.get(variations.indexOf(Collections.min(variations)));
			WorldGenCustomStructures.invasionTower.generateStructure(event.world, structurePos, false);
		    }
		    else
		    {
			// If we don't find any good place to put the tower, put a cooldown because
			// chances are there may be a lot of bad areas, so don't spend too much
			// computing power
			// every tick. Instead wait a second and try again
			invasionCounter.setInvasionTime((int) (ModConfig.world.invasionTime * 0.09f));
		    }
		}
		else
		{
		    invasionCounter.setDimensionCooldownTime();
		}
	    }
	}
    }

    @SubscribeEvent
    public static void attachCabability(AttachCapabilitiesEvent<Entity> event)
    {
	if (event.getObject() instanceof EntityPlayer)
	{
	    event.addCapability(MANA, new ManaProvider());
	}
    }

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

		    if (result != null)
		    {
			if (result.typeOfHit == RayTraceResult.Type.ENTITY)
			{
			    Main.network.sendToServer(new MessageExtendedReachAttack(result.entityHit.getEntityId()));
			    mc.player.resetCooldown();
			}
			else if (result.typeOfHit == RayTraceResult.Type.MISS)
			{
			    mc.player.resetCooldown();
			    net.minecraftforge.common.ForgeHooks.onEmptyLeftClick(mc.player);
			    event.setCanceled(true); // Prevents shorter reach swords from hitting with the event going through
			}
			// We let the block ray trace result be handled by the default event
			mc.player.swingArm(EnumHand.MAIN_HAND);
		    }
		}
	    }
	}
    }

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
	float damage = event.getAmount();
	// Factor in elemental armor first
	if (event.getSource() instanceof IElement)
	{
	    damage *= 1 - ArmorHandler.getElementalArmor(event.getEntity(), ((IElement) event.getSource()).getElement());
	}

	// Factor in maelstrom armor second
	if (!event.getSource().isUnblockable())
	{
	    damage *= 1 - ArmorHandler.getMaelstromArmor(event.getEntity());
	}

	event.setAmount(damage);
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
	    if (ModConfig.gui.showArmorBar)
	    {
		InGameGui.renderArmorBar(mc, event, player);
	    }

	    if (ModConfig.gui.showGunCooldownBar)
	    {
		InGameGui.renderGunReload(mc, event, player);
	    }

	    IMana mana = player.getCapability(ManaProvider.MANA, null);

	    if (!mana.isLocked() && ModConfig.gui.showManaBar)
	    {
		InGameGui.renderManaBar(mc, event, player);
	    }
	}
    }
}
