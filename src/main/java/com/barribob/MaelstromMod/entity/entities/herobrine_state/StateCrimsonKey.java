package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.init.ModProfessions;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class StateCrimsonKey extends HerobrineState implements IMerchant
{
    protected final MerchantRecipeList buyingList = new MerchantRecipeList();
    protected EntityPlayer buyingPlayer;
    protected boolean gtfo = false;
    private boolean leftClickMessage = false;

    public StateCrimsonKey(Herobrine herobrine)
    {
	super(herobrine);
	for (EntityVillager.ITradeList list : ModProfessions.HEROBRINE_CRIMSON_KEY.getTrades(0))
	{
	    list.addMerchantRecipe(this, this.buyingList, this.herobrine.getRNG());
	}
    }

    @Override
    public String getNbtString()
    {
	return "crimson_key";
    }

    @Override
    public void rightClick(EntityPlayer player)
    {
	if (!this.gtfo)
	{
	    this.messageToPlayers.accept("herobrine_9");
	    this.gtfo = true;
	}
	if (herobrine.isEntityAlive() && this.buyingPlayer == null)
	{
	    this.setCustomer(player);
	    player.displayVillagerTradeGui(this);
	}
    }

    @Override
    public void leftClick(Herobrine herobrine)
    {
	if (!this.leftClickMessage)
	{
	    messageToPlayers.accept("herobrine_11");
	    leftClickMessage = true;
	}
	super.leftClick(herobrine);
    }

    @Override
    public void setCustomer(EntityPlayer player)
    {
	this.buyingPlayer = player;
    }

    @Override
    public EntityPlayer getCustomer()
    {
	return this.buyingPlayer;
    }

    @Override
    public MerchantRecipeList getRecipes(EntityPlayer player)
    {
	return this.buyingList;
    }

    @Override
    public void setRecipes(MerchantRecipeList recipeList)
    {
    }

    @Override
    public void useRecipe(MerchantRecipe recipe)
    {
    }

    @Override
    public void verifySellingItem(ItemStack stack)
    {
    }

    @Override
    public ITextComponent getDisplayName()
    {
	ITextComponent itextcomponent = new TextComponentTranslation("herobrine_trading", new Object[0]);
	itextcomponent.getStyle().setInsertion(herobrine.getCachedUniqueIdString());
	return itextcomponent;
    }

    @Override
    public World getWorld()
    {
	return this.world;
    }

    @Override
    public BlockPos getPos()
    {
	return new BlockPos(herobrine);
    }
}
