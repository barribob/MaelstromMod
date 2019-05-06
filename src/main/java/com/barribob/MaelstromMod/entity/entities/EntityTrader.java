package com.barribob.MaelstromMod.entity.entities;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.World;

/**
 * 
 * For Mod entities whose main job is to be a trader of some sort
 * Sourced from the EntityVillager
 *
 */
public abstract class EntityTrader extends EntityLeveledMob implements IMerchant
{
    protected MerchantRecipeList buyingList;
    @Nullable
    protected EntityPlayer buyingPlayer;

    public EntityTrader(World worldIn)
    {
	super(worldIn);
    }

    /**
     * Generate the buying list for the villager (the trades and items)
     */
    protected void populateBuyingList()
    {
	if (this.buyingList == null)
	{
	    this.buyingList = new MerchantRecipeList();
	}

	List<EntityVillager.ITradeList> trades = this.getTrades();

	if (trades != null)
	{
	    for (EntityVillager.ITradeList list : trades)
	    {
		list.addMerchantRecipe(this, this.buyingList, this.rand);
	    }
	}
    }

    /**
     * Return the initial trades for the villager
     */
    protected abstract List<EntityVillager.ITradeList> getTrades();

    /**
     * When the entity is right clicked
     */
    public boolean processInteract(EntityPlayer player, EnumHand hand)
    {
	ItemStack itemstack = player.getHeldItem(hand);
	boolean flag = itemstack.getItem() == Items.NAME_TAG;

	if (flag)
	{
	    itemstack.interactWithEntity(player, this, hand);
	    return true;
	}
	else if (this.isEntityAlive() && !this.isTrading() && !this.isChild() && !player.isSneaking() && this.getAttackTarget() == null)
	{
	    if (this.buyingList == null)
	    {
		this.populateBuyingList();
	    }

	    if (hand == EnumHand.MAIN_HAND)
	    {
		player.addStat(StatList.TALKED_TO_VILLAGER);
	    }

	    this.onTraderInteract(player);

	    if (!this.world.isRemote && !this.buyingList.isEmpty())
	    {
		this.setCustomer(player);
		player.displayVillagerTradeGui(this);
	    }
	    else if (this.buyingList.isEmpty())
	    {
		return super.processInteract(player, hand);
	    }

	    return true;
	}
	else
	{
	    return super.processInteract(player, hand);
	}
    }

    protected void onTraderInteract(EntityPlayer player)
    {
    }

    public boolean isTrading()
    {
	return this.buyingPlayer != null;
    }

    /**
     * The following few methods are used in the IMerchant class to handle the
     * trading interface
     */
    @Nullable
    public EntityPlayer getCustomer()
    {
	return this.buyingPlayer;
    }

    public void setCustomer(@Nullable EntityPlayer player)
    {
	this.buyingPlayer = player;
    }

    @Nullable
    public MerchantRecipeList getRecipes(EntityPlayer player)
    {
	if (this.buyingList == null)
	{
	    this.populateBuyingList();
	}

	return this.buyingList;
    }

    @Override
    public void setRecipes(MerchantRecipeList recipeList)
    {
    }

    public void useRecipe(MerchantRecipe recipe)
    {
	recipe.incrementToolUses();
	this.livingSoundTime = -this.getTalkInterval();
	this.playSound(SoundEvents.ENTITY_VILLAGER_YES, this.getSoundVolume(), this.getSoundPitch());

	if (recipe.getRewardsExp())
	{
	    this.world.spawnEntity(new EntityXPOrb(this.world, this.posX, this.posY + 0.5D, this.posZ, 5));
	}
    }

    /**
     * Notifies the merchant of a possible merchantrecipe being fulfilled or not.
     * Usually, this is just a sound byte being played depending if the suggested
     * itemstack is not null.
     */
    public void verifySellingItem(ItemStack stack)
    {
	if (!this.world.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20)
	{
	    this.livingSoundTime = -this.getTalkInterval();
	    this.playSound(stack.isEmpty() ? SoundEvents.ENTITY_VILLAGER_NO : SoundEvents.ENTITY_VILLAGER_YES, this.getSoundVolume(), this.getSoundPitch());
	}
    }

    public World getWorld()
    {
	return this.world;
    }

    public BlockPos getPos()
    {
	return new BlockPos(this);
    }
    
    /**
     * Get the formatted ChatComponent that will be used for the sender's username in chat
     */
    public ITextComponent getDisplayName()
    {
        Team team = this.getTeam();
        String s = this.getCustomNameTag();

        if (s != null && !s.isEmpty())
        {
            TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.formatPlayerName(team, s));
            textcomponentstring.getStyle().setHoverEvent(this.getHoverEvent());
            textcomponentstring.getStyle().setInsertion(this.getCachedUniqueIdString());
            return textcomponentstring;
        }
        else
        {
            if (this.buyingList == null)
            {
                this.populateBuyingList();
            }

	    ITextComponent itextcomponent = new TextComponentTranslation(this.getVillagerName(), new Object[0]);
	    itextcomponent.getStyle().setHoverEvent(this.getHoverEvent());
	    itextcomponent.getStyle().setInsertion(this.getCachedUniqueIdString());

	    if (team != null)
	    {
		itextcomponent.getStyle().setColor(team.getColor());
	    }

	    return itextcomponent;
            
        }
    }
    
    /**
     * The name displayed when the trading gui is up
     */
    protected abstract String getVillagerName();

    public void writeEntityToNBT(NBTTagCompound compound)
    {
	super.writeEntityToNBT(compound);

	if (this.buyingList != null)
	{
	    compound.setTag("Offers", this.buyingList.getRecipiesAsTags());
	}
    }

    public void readEntityFromNBT(NBTTagCompound compound)
    {
	super.readEntityFromNBT(compound);
	if (compound.hasKey("Offers", 10))
	{
	    NBTTagCompound nbttagcompound = compound.getCompoundTag("Offers");
	    this.buyingList = new MerchantRecipeList(nbttagcompound);
	}
    }
}
