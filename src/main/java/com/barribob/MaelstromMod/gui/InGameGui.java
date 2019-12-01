package com.barribob.MaelstromMod.gui;

import com.barribob.MaelstromMod.config.ModConfig;
import com.barribob.MaelstromMod.items.gun.Reloadable;
import com.barribob.MaelstromMod.mana.Mana;
import com.barribob.MaelstromMod.mana.ManaProvider;
import com.barribob.MaelstromMod.util.Reference;
import com.barribob.MaelstromMod.util.handlers.ArmorHandler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.GuiIngameForge;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Handles armor bar rendering, and gun reload rendering
 *
 */
public class InGameGui
{
    public static final ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID + ":textures/gui/armor_icons.png");

    /*
     * Sourced from the render hotbar method in GuiIngame to display a cooldown bar
     */
    @SideOnly(Side.CLIENT)
    public static void renderGunReload(Minecraft mc, RenderGameOverlayEvent.Post event, EntityPlayer player)
    {
	GameSettings gamesettings = mc.gameSettings;
	if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !player.hasReducedDebug() && !gamesettings.reducedDebugInfo)
	{
	    return;
	}

	GlStateManager.enableRescaleNormal();
	GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
		GlStateManager.DestFactor.ZERO);
	RenderHelper.enableGUIStandardItemLighting();

	int i = event.getResolution().getScaledWidth() / 2;

	// Render the 9 slots
	for (int l = 0; l < 9; ++l)
	{
	    int i1 = i - 90 + l * 20 + 2;
	    int j1 = event.getResolution().getScaledHeight() - 16 - 3;
	    renderItemAmmo(player.inventory.mainInventory.get(l), i1, j1, mc);
	}

	ItemStack itemstack = player.getHeldItemOffhand();
	EnumHandSide enumhandside = player.getPrimaryHand().opposite();

	// Render the offhand
	if (!itemstack.isEmpty())
	{
	    int j1 = event.getResolution().getScaledHeight() - 16 - 3;

	    if (enumhandside == EnumHandSide.LEFT)
	    {
		renderItemAmmo(itemstack, i - 91 - 26, j1, mc);
	    }
	    else
	    {
		renderItemAmmo(itemstack, i + 91 + 10, j1, mc);
	    }
	}

	RenderHelper.disableStandardItemLighting();
	GlStateManager.disableRescaleNormal();
	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    }

    /**
     * Renders the gun reload bar for a single item stack if it is an instanceof
     * ItemGun
     */
    @SideOnly(Side.CLIENT)
    private static void renderItemAmmo(ItemStack stack, int xPosition, int yPosition, Minecraft mc)
    {
	if (!stack.isEmpty())
	{
	    if (stack.getItem() instanceof Reloadable)
	    {
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();

		double ammo = ((Reloadable) stack.getItem()).getCooldownForDisplay(stack);

		if (ammo != 0)
		{
		    int i = Math.round(13.0F - (float) ammo * 13.0F);
		    draw(bufferbuilder, xPosition + 2, yPosition + 13 - 2, 13, 2, 0, 0, 0, 255);
		    draw(bufferbuilder, xPosition + 2, yPosition + 13 - 2, i, 1, 177, 220, 255, 255);
		}

		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
		GlStateManager.enableLighting();
		GlStateManager.enableDepth();
	    }
	}
    }

    /**
     * Draw with the WorldRenderer Sourced from some vanilla rendering class
     */
    @SideOnly(Side.CLIENT)
    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
	renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	renderer.pos(x + 0, y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
	renderer.pos(x + 0, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
	renderer.pos(x + width, y + height, 0.0D).color(red, green, blue, alpha).endVertex();
	renderer.pos(x + width, y + 0, 0.0D).color(red, green, blue, alpha).endVertex();
	Tessellator.getInstance().draw();
    }

    /**
     * Renders the maelstrom armor bar
     */
    @SideOnly(Side.CLIENT)
    public static void renderArmorBar(Minecraft mc, RenderGameOverlayEvent.Post event, EntityPlayer player)
    {
	if (event.getType().equals(RenderGameOverlayEvent.ElementType.ARMOR))
	{
	    mc.getTextureManager().bindTexture(ICONS);

	    int startX = event.getResolution().getScaledWidth() / 2 - 91;
	    int startY = event.getResolution().getScaledHeight() - GuiIngameForge.left_height;

	    // Add config file
	    startX += ModConfig.gui.maelstrom_armor_bar_offset_x;
	    startY += ModConfig.gui.maelstrom_armor_bar_offset_y;

	    int maelstromArmor = ArmorHandler.getMaelstromArmorBars(player);

	    // Draw the maelstrom armor bar
	    // Specific numbers taken from the GuiIngame armor section
	    if (maelstromArmor > 0)
	    {
		for (int i = 0; i < 10; i++)
		{
		    int x = startX + i * 8;
		    int armorPos = i * 2 + 1;

		    // Calculate the animation cycles
		    int animationLength = 9;
		    int framesPerTick = 3;
		    int y = 9 * (Math.floorDiv(player.ticksExisted, framesPerTick) % animationLength);

		    if (armorPos < maelstromArmor)
		    {
			mc.ingameGUI.drawTexturedModalRect(x, startY, 0, y, 9, 9);
		    }

		    if (armorPos == maelstromArmor)
		    {
			mc.ingameGUI.drawTexturedModalRect(x, startY, 9, y, 9, 9);
		    }

		    if (armorPos > maelstromArmor)
		    {
			mc.ingameGUI.drawTexturedModalRect(x, startY, 18, 0, 9, 9);
		    }
		}
	    }

	    GuiIngameForge.left_height += 10;

	    mc.getTextureManager().bindTexture(mc.ingameGUI.ICONS);
	}
    }

    @SideOnly(Side.CLIENT)
    public static void renderManaBar(Minecraft mc, RenderGameOverlayEvent.Post event, EntityPlayer player)
    {
	if (event.getType().equals(RenderGameOverlayEvent.ElementType.AIR))
	{
	    mc.getTextureManager().bindTexture(ICONS);
	    int width = event.getResolution().getScaledWidth();
	    int height = event.getResolution().getScaledHeight();

	    GlStateManager.enableBlend();
	    int top = height - GuiIngameForge.right_height;

	    float mana = player.getCapability(ManaProvider.MANA, null).getMana();
	    int fullBarX = 8 * 10 + 1;
	    int left = width / 2 + 91 - fullBarX - 6;
	    int framesPerTick = 4;
	    int[] frames = { 0, 1, 2, 3, 2, 1, 0 };
	    int frame = Math.floorDiv(player.ticksExisted, framesPerTick) % frames.length;
	    int animationY = 21 + frames[frame] * 5;

	    left += ModConfig.gui.maelstrom_mana_bar_offset_x;
	    top += ModConfig.gui.maelstrom_mana_bar_offset_y;

	    // Draw the empty bar
	    mc.ingameGUI.drawTexturedModalRect(left, top, 31, 10, 96, 9);
	    // Draw the inner bar with mana
	    mc.ingameGUI.drawTexturedModalRect(left + 6, top + 2, 37, animationY, Math.round(((mana / Mana.MAX_MANA) * fullBarX)), 5);
	    GuiIngameForge.right_height += 10;

	    GlStateManager.disableBlend();
	    mc.getTextureManager().bindTexture(mc.ingameGUI.ICONS);
	}
    }
}
