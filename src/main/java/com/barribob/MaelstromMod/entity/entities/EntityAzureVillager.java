package com.barribob.MaelstromMod.entity.entities;

import java.util.List;

import javax.annotation.Nullable;

import com.barribob.MaelstromMod.init.ModProfessions;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityVillager.ITradeList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 
 * Closely sourced from the EntityMob, and EntityIllager to create a mob
 * with combat and trading functionalties
 *
 */
public class EntityAzureVillager extends EntityTrader implements IMerchant
{
    // Used in animation to determine if the entity should render in attack pose
    protected static final DataParameter<Byte> ATTACKING = EntityDataManager.<Byte>createKey(EntityAzureVillager.class, DataSerializers.BYTE);
    private static final String[] CHAT_MESSAGES = {
	    "We've been having a problem with the maelstrom that's invaded this dimension.",
	    "You seem like a warrior, would you defeat the maelstrom invaders for us?",
	    "We'll reward you handsomely for taking out the maelstrom.",
	    "Have a look around the mineshaft for any useful gear and items.",
	    "Under the maelstrom growths, there is a core that spawns the maelstrom. Remove those!",
	    "Keep an eye out for the maelstrom fortress. That's where the boss is."
    };
    
    private static int message_counter = 0;

    protected void entityInit()
    {
	super.entityInit();
	this.dataManager.register(ATTACKING, Byte.valueOf((byte) 0));
    }

    public EntityAzureVillager(World worldIn)
    {
	super(worldIn);
	this.setSize(0.6F, 1.95F);
    }

    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
	this.tasks.addTask(4, new EntityAIAttackMelee(this, 1.0D, false));
        this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
	this.tasks.addTask(8, new EntityAIWander(this, 0.6D));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
        this.tasks.addTask(9, new EntityAIWanderAvoidWater(this, 0.6D));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
	this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] { EntityAzureVillager.class }));
    }

    protected void applyEntityAttributes()
    {
	super.applyEntityAttributes();
	this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3499999940395355D);
	this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(12.0D);
    }
    
    @Override
    protected void updateAttributes()
    {
	this.setBaseAttack(1);
    }

    protected ResourceLocation getLootTable()
    {
	return LootTableList.ENTITIES_VILLAGER;
    }
    
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn)
    {
        return SoundEvents.ENTITY_VILLAGER_HURT;
    }
    
    @Override
    protected SoundEvent getAmbientSound()
    {
        return SoundEvents.ENTITY_VILLAGER_AMBIENT;
    }
    
    @Override
    protected SoundEvent getDeathSound()
    {
        return SoundEvents.ENTITY_VILLAGER_DEATH;
    }

    @SideOnly(Side.CLIENT)
    protected boolean isAggressive(int mask)
    {
	int i = ((Byte) this.dataManager.get(ATTACKING)).byteValue();
	return (i & mask) != 0;
    }

    protected void setAggressive(int mask, boolean value)
    {
	int i = ((Byte) this.dataManager.get(ATTACKING)).byteValue();

	if (value)
	{
	    i = i | mask;
	}
	else
	{
	    i = i & ~mask;
	}

	this.dataManager.set(ATTACKING, Byte.valueOf((byte) (i & 255)));
    }

    @SideOnly(Side.CLIENT)
    public boolean isAggressive()
    {
	return this.isAggressive(1);
    }

    public void setAggressive(boolean p_190636_1_)
    {
	this.setAggressive(1, p_190636_1_);
    }

    protected void updateAITasks()
    {
	super.updateAITasks();
	this.setAggressive(this.getAttackTarget() != null);
    }

    /**
     * Taken from the EntityMob class
     */
    public boolean attackEntityAsMob(Entity entityIn)
    {
	float f = (float) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
	int i = 0;

	if (entityIn instanceof EntityLivingBase)
	{
	    f += EnchantmentHelper.getModifierForCreature(this.getHeldItemMainhand(), ((EntityLivingBase) entityIn).getCreatureAttribute());
	    i += EnchantmentHelper.getKnockbackModifier(this);
	}

	boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), f);

	if (flag)
	{
	    if (i > 0 && entityIn instanceof EntityLivingBase)
	    {
		((EntityLivingBase) entityIn).knockBack(this, (float) i * 0.5F, (double) MathHelper.sin(this.rotationYaw * 0.017453292F),
			(double) (-MathHelper.cos(this.rotationYaw * 0.017453292F)));
		this.motionX *= 0.6D;
		this.motionZ *= 0.6D;
	    }

	    int j = EnchantmentHelper.getFireAspectModifier(this);

	    if (j > 0)
	    {
		entityIn.setFire(j * 4);
	    }

	    if (entityIn instanceof EntityPlayer)
	    {
		EntityPlayer entityplayer = (EntityPlayer) entityIn;
		ItemStack itemstack = this.getHeldItemMainhand();
		ItemStack itemstack1 = entityplayer.isHandActive() ? entityplayer.getActiveItemStack() : ItemStack.EMPTY;

		if (!itemstack.isEmpty() && !itemstack1.isEmpty() && itemstack.getItem().canDisableShield(itemstack, itemstack1, entityplayer, this)
			&& itemstack1.getItem().isShield(itemstack1, entityplayer))
		{
		    float f1 = 0.25F + (float) EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;

		    if (this.rand.nextFloat() < f1)
		    {
			entityplayer.getCooldownTracker().setCooldown(itemstack1.getItem(), 100);
			this.world.setEntityState(entityplayer, (byte) 30);
		    }
		}
	    }

	    this.applyEnchantments(this, entityIn);
	}

	return flag;
    }

    /**
     * Called only once on an entity when first time spawned, via egg, mob spawner,
     * natural spawning etc, but not called when entity is reloaded from nbt. Mainly
     * used for initializing attributes and inventory
     */
    @Nullable
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata)
    {
	IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
	this.setEquipmentBasedOnDifficulty(difficulty);
	this.setEnchantmentBasedOnDifficulty(difficulty);
	return ientitylivingdata;
    }

    /**
     * Gives armor or weapon for entity based on given DifficultyInstance
     */
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
	this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
    }

    @Override
    protected void onTraderInteract(EntityPlayer player)
    {
        // Display chat messages
        if(!player.world.isRemote)
        {
            player.sendMessage(new TextComponentString(TextFormatting.DARK_BLUE + "Villager: " + TextFormatting.WHITE + CHAT_MESSAGES[message_counter]));
            
            message_counter++;
            if(message_counter >= CHAT_MESSAGES.length)
            {
        	message_counter = 0;	
            }
        }
    }

    @Override
    protected List<ITradeList> getTrades()
    {
	return ModProfessions.AZURE_WEAPONSMITH.getTrades(0);
    }
    
    @Override
    protected String getVillagerName()
    {
        return "Azure Villager";
    }
    
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