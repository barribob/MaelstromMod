package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.items.armor.ModArmorBase;
import com.barribob.MaelstromMod.util.ModDamageSource;
import com.barribob.MaelstromMod.util.Reference;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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
     * Renders the maelstrom armor bar
     */
    @SubscribeEvent
    public static void onGuiPostRender(RenderGameOverlayEvent.Post event)
    {
	Minecraft mc = Minecraft.getMinecraft();
	if (mc.getRenderViewEntity() instanceof EntityPlayer)
	{
	    EntityPlayer player = (EntityPlayer) mc.getRenderViewEntity();

	    // If in creative mode or something, don't draw
	    if (!mc.playerController.shouldDrawHUD())
	    {
		return;
	    }

	    mc.getTextureManager().bindTexture(ICONS);

	    int startX = event.getResolution().getScaledWidth() / 2 - 91;
	    int healthYPos = event.getResolution().getScaledHeight() - 39;
            
	    // Factor in max health and aborbtion pushing the bars up
	    int absorbtion = MathHelper.ceil(player.getAbsorptionAmount());
            float maxHealth = (float)player.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
            int l1 = MathHelper.ceil((maxHealth + (float)absorbtion) / 2.0F / 10.0F);
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
    }

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
