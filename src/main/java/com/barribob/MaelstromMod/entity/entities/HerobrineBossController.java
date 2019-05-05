package com.barribob.MaelstromMod.entity.entities;

import java.util.function.Consumer;

import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import com.barribob.MaelstromMod.util.TimedMessager;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/*
 * Controls the herobrine fight, with dialogue and ending
 */
public class HerobrineBossController extends EntityLeveledMob
{
    private static final String[] INTRO_MESSAGES = { "Hello there adventurer.", "Before I allow you to begin your adventure,", "I'll have to make sure you are able to fight.",
	    "If you don't impress me, you'll die.", "" };
    private static final int[] INTRO_MESSAGE_TIMES = { 80, 140, 200, 260, 320 };
    private static final String[] EXIT_MESSAGES = { "Good, you have the skill,", "I will give you the key to the first dimension,", "I will see you in the final dimension.",
	    "" };
    private static final int[] EXIT_MESSAGE_TIMES = { 80, 140, 200, 260 };

    private final BossInfoServer bossInfo = (BossInfoServer) (new BossInfoServer(this.getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.NOTCHED_20));
    private TimedMessager messager;
    private EntityHerobrineOne herobrine;
    private byte particles = 7;
    private Vec3d initialPosition = Vec3d.ZERO;
    private String uuidSave = "herobrine";

    /**
     * Functional Programming in Java WOOOOOOOOOOOOOT!!!!!!!!
     */
    private Consumer<String> spawnHerobrine = (s) -> {
	this.setInvisible(true);
	herobrine = new EntityHerobrineOne(world);
	herobrine.copyLocationAndAnglesFrom(this);
	herobrine.setRotationYawHead(this.rotationYawHead);
	if (!world.isRemote)
	{
	    herobrine.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(herobrine)), (IEntityLivingData) null);
	    world.spawnEntity(herobrine);
	}
    };

    private Consumer<String> dropKey = (s) -> {
	this.dropItem(ModItems.AZURE_KEY, 1);
	world.setEntityState(this, this.particles);
	this.setDead();
    };

    private Consumer<String> messageToPlayers = (message) -> {
	if (message != "")
	{
	    for (EntityPlayer player : this.bossInfo.getPlayers())
	    {
		player.sendMessage(new TextComponentString(TextFormatting.DARK_PURPLE + "Herobrine: " + TextFormatting.WHITE + message));
	    }
	}
    };

    @Override
    protected void initEntityAI()
    {
	super.initEntityAI();
	this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 20.0F));
    }

    public HerobrineBossController(World worldIn)
    {
	super(worldIn);
	this.messager = new TimedMessager(INTRO_MESSAGES, INTRO_MESSAGE_TIMES, this.spawnHerobrine);
	this.setSize(0.5f, 1.0f);
    }

    // Hold the entity in the same position
    public void setPosition(double x, double y, double z)
    {
	super.setPosition(x, y, z);
	if (this.initialPosition == Vec3d.ZERO)
	{
	    this.initialPosition = ModUtils.entityPos(this);
	}
	else if (this.initialPosition != null)
	{
	    super.setPosition(initialPosition.x, initialPosition.y, initialPosition.z);
	}
    }
    
    @Override
    protected boolean canDespawn()
    {
	return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
	return false;
    }
    
    @Override
    public boolean canRenderOnFire()
    {
	return false;
    }

    @Override
    public void onUpdate()
    {
	super.onUpdate();
	
	this.messager.Update(world, messageToPlayers);

	if (herobrine != null)
	{
	    this.bossInfo.setPercent(herobrine.getHealth() / herobrine.getMaxHealth());

	    // If the herobrine falls off, teleport it back
	    if (herobrine.posY < 0)
	    {
		herobrine.copyLocationAndAnglesFrom(this);
	    }

	    // When herobrine is defeated
	    if (herobrine.getHealth() <= 0.0)
	    {
		this.setInvisible(false);
		this.messager = new TimedMessager(EXIT_MESSAGES, EXIT_MESSAGE_TIMES, dropKey);
		this.bossInfo.setPercent(1);
		this.herobrine = null;
	    }

	}
    }

    /**
     * Sets the custom name tag for this entity
     */
    public void setCustomNameTag(String name)
    {
	super.setCustomNameTag(name);
	this.bossInfo.setName(this.getDisplayName());
    }

    /**
     * Add the given player to the list of players tracking this entity. For
     * instance, a player may track a boss in order to view its associated boss bar.
     */
    public void addTrackingPlayer(EntityPlayerMP player)
    {
	super.addTrackingPlayer(player);
	this.bossInfo.addPlayer(player);
    }

    /**
     * Removes the given player from the list of players tracking this entity. See
     * {@link Entity#addTrackingPlayer} for more information on tracking.
     */
    public void removeTrackingPlayer(EntityPlayerMP player)
    {
	super.removeTrackingPlayer(player);
	this.bossInfo.removePlayer(player);
    }

    /**
     * Handler for {@link World#setEntityState}
     */
    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id)
    {
	if (id == this.particles)
	{
	    int particleAmount = 100;
	    for (int i = 0; i < particleAmount; i++)
	    {
		ParticleManager.spawnDarkFlames(this.world, rand, ModUtils.entityPos(this).add(ModRandom.randVec().scale(2f)).add(new Vec3d(0, 1, 0)),
			ModRandom.randVec().scale(0.5f));
	    }
	}
	else
	{
	    super.handleStatusUpdate(id);
	}
    }

    @Override
    protected void updateAttributes()
    {
	this.setBaseMaxHealth(20);
    }
}
