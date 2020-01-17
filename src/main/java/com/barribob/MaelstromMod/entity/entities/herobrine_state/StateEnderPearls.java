package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.init.ModProfessions;
import com.barribob.MaelstromMod.util.TimedMessager;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

public class StateEnderPearls extends HerobrineState implements IMerchant
{
    protected final MerchantRecipeList buyingList = new MerchantRecipeList();
    protected EntityPlayer buyingPlayer;
    private boolean leftClickMessage = false;
    private TimedMessager messager;

    public StateEnderPearls(Herobrine herobrine)
    {
	super(herobrine);
	for (EntityVillager.ITradeList list : ModProfessions.HEROBRINE_ENDER_PEARLS.getTrades(0))
	{
	    list.addMerchantRecipe(this, this.buyingList, this.herobrine.getRNG());
	}
	messager = new TimedMessager(new String[] { "herobrine_pearl_0", "herobrine_pearl_1", "" }, new int[] { 40, 100, 100 }, (s) -> {
	});
    }

    @Override
    public void update()
    {
	messager.Update(world, messageToPlayers);
    }

    @Override
    public String getNbtString()
    {
	return "ender_pearl";
    }

    @Override
    public void rightClick(EntityPlayer player)
    {
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
	    messageToPlayers.accept("herobrine_pearl_2");
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
	herobrine.teleportOutside();
	herobrine.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
	herobrine.state = new StateFirstBattle(herobrine);
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
