package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.items.ItemGun;
import com.barribob.MaelstromMod.items.armor.ModArmorBase;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber()
public class ModEventHandler
{
    public static final ResourceLocation ICONS = new ResourceLocation(Reference.MOD_ID + ":textures/gui/mod_icons.png");

    @SubscribeEvent
    public static void onLivingHurt(LivingHurtEvent event)
    {
	// Factor in maelstrom armor into damage source
	if (ModDamageSource.isMaelstromDamage(event.getSource()))
	{
	    float reductionPerArmor = 0.04f;
	    float totalReductionAmount = 1 - (getMaelstromArmor(event.getEntity()) * reductionPerArmor);
	    event.setAmount(event.getAmount() * totalReductionAmount);

	    for (ItemStack equipment : event.getEntity().getArmorInventoryList())
	    {
		if (equipment.getItem() instanceof ModArmorBase)
		{
		    equipment.damageItem(1, event.getEntityLiving());
		}
	    }
	}
    }

    /**
     * Renders the maelstrom armor bar and the gun reload bar
     */
    @SubscribeEvent
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
		renderArmorBar(mc, event, player);
	    }
	    
	    renderGunReload(mc, event, player);
	    
	    GlStateManager.disableBlend();
	}
    }

    /*
     * Sourced from the render hotbar method in GuiIngame to display a cooldown bar
     */
    private static void renderGunReload(Minecraft mc, RenderGameOverlayEvent.Post event, EntityPlayer player)
    {
	GameSettings gamesettings = mc.gameSettings;

	if (gamesettings.thirdPersonView == 0)
	{
	    if (gamesettings.showDebugInfo && !gamesettings.hideGUI && !player.hasReducedDebug() && !gamesettings.reducedDebugInfo)
	    {
		return;
	    }
	    
            GlStateManager.enableRescaleNormal();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
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
    }

    /**
     * Renders the gun reload bar for a single item stack if it is an instanceof ItemGun
     */
    private static void renderItemAmmo(ItemStack stack, int xPosition, int yPosition, Minecraft mc)
    {
	if (!stack.isEmpty())
	{
	    if (stack.getItem() instanceof ItemGun)
	    {
		GlStateManager.disableLighting();
		GlStateManager.disableDepth();
		GlStateManager.disableTexture2D();
		GlStateManager.disableAlpha();
		GlStateManager.disableBlend();
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();

		double ammo = ((ItemGun) stack.getItem()).getCooldownForDisplay(stack);

		if (ammo != 0)
		{
		    int i = Math.round(13.0F - (float) ammo * 13.0F);
		    draw(bufferbuilder, xPosition + 2, yPosition + 13 - 2, 13, 2, 0, 0, 0, 255);
		    draw(bufferbuilder, xPosition + 2, yPosition + 13 - 2, i, 1, 0, 0, 255, 255);
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
     * Draw with the WorldRenderer
     * Sourced from some vanilla rendering class
     */
    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha)
    {
	renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
	renderer.pos((double) (x + 0), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
	renderer.pos((double) (x + 0), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
	renderer.pos((double) (x + width), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
	renderer.pos((double) (x + width), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
	Tessellator.getInstance().draw();
    }

    /**
     * Renders the maelstrom armor bar
     */
    private static void renderArmorBar(Minecraft mc, RenderGameOverlayEvent.Post event, EntityPlayer player)
    {
	mc.getTextureManager().bindTexture(ICONS);

	int startX = event.getResolution().getScaledWidth() / 2 - 91;
	int healthYPos = event.getResolution().getScaledHeight() - 39;

	// Factor in max health and aborbtion pushing the bars up
	int absorbtion = MathHelper.ceil(player.getAbsorptionAmount());
	float maxHealth = (float) player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
	int l1 = MathHelper.ceil((maxHealth + (float) absorbtion) / 2.0F / 10.0F);
	int i2 = Math.max(10 - (l1 - 2), 3);
	int startY = healthYPos - (l1 - 1) * i2 - 10;

	// Add 10 for the normal armor bar
	startY -= player.getTotalArmorValue() > 0 ? 10 : 0;

	int maelstromArmor = getMaelstromArmor(player);

	// Draw the maelstrom armor bar
	// Specific numbers taken from the GuiIngame armor section
	if (maelstromArmor > 0)
	{
	    for (int i = 0; i < 10; i++)
	    {
		int x = startX + i * 8;
		int armorPos = i * 2 + 1;

		if (armorPos < maelstromArmor)
		{
		    mc.ingameGUI.drawTexturedModalRect(x, startY, 18, 0, 9, 9);
		}

		if (armorPos == maelstromArmor)
		{
		    mc.ingameGUI.drawTexturedModalRect(x, startY, 9, 0, 9, 9);
		}

		if (armorPos > maelstromArmor)
		{
		    mc.ingameGUI.drawTexturedModalRect(x, startY, 0, 0, 9, 9);
		}
	    }
	}

	mc.getTextureManager().bindTexture(mc.ingameGUI.ICONS);
    }

    /**
     * Get the total maelstrom armor of an entity
     */
    private static int getMaelstromArmor(Entity entity)
    {
	int totalArmor = 0;

	for (ItemStack equipment : entity.getArmorInventoryList())
	{
	    if (equipment.getItem() instanceof ModArmorBase)
	    {
		totalArmor += ((ModArmorBase) equipment.getItem()).getMaelstromArmor();
	    }
	}

	return totalArmor;
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onFogDensityRender(EntityViewRenderEvent.FogDensity event)
    {
	// if (event.getEntity().dimension == DimensionInit.DIMENSION_AZURE_ID)
	// {
	// int fogStartY = 60;
	// float maxFog = 0.1f;
	// float fogDensity = 0.01f;
	//
	// /**
	// * alters fog based on the height of the player
	// */
	// if (event.getEntity().posY < fogStartY)
	// {
	// fogDensity += (float) (fogStartY - event.getEntity().posY) * (maxFog /
	// fogStartY);
	// }
	//
	// event.setDensity(fogDensity);
	// event.setCanceled(true);
	// }
    }
}
