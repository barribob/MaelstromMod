package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import java.util.Collection;
import java.util.function.Consumer;

import com.barribob.MaelstromMod.entity.entities.EntityHerobrineOne;
import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.init.ModItems;
import com.barribob.MaelstromMod.util.TimedMessager;

import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public class StateFirstEncounter extends HerobrineState
{
    private static final String[] INTRO_MESSAGES = { "herobrine_1", "herobrine_2", "herobrine_3", "herobrine_4", "" };
    private static final int[] INTRO_MESSAGE_TIMES = { 80, 140, 200, 260, 320 };
    private static final String[] EXIT_MESSAGES = { "herobrine_5", "herobrine_6", "herobrine_7", "" };
    private static final int[] EXIT_MESSAGE_TIMES = { 80, 140, 200, 260 };
    private EntityHerobrineOne herobrineBoss;
    private TimedMessager messager;
    private boolean leftClickMessage = false;
    private int idleCounter;

    private Consumer<String> spawnHerobrine = (s) -> {
	herobrineBoss = new EntityHerobrineOne(world);
	herobrineBoss.setLocationAndAngles(herobrine.posX, herobrine.posY, herobrine.posZ - 5, herobrine.rotationYaw, herobrine.rotationPitch);
	herobrineBoss.setRotationYawHead(herobrine.rotationYawHead);
	if (!world.isRemote)
	{
	    herobrineBoss.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(herobrineBoss)), (IEntityLivingData) null);
	    world.spawnEntity(herobrineBoss);
	}
    };

    private Consumer<String> dropKey = (s) -> {
	// Default position
	Vec3d pos = new Vec3d(herobrine.posX, herobrine.posY + 1, herobrine.posZ - 5);
	Collection<EntityPlayerMP> players = herobrine.bossInfo.getPlayers();
	for (EntityPlayerMP player : players)
	{
	    pos = player.getPositionVector(); // Set the item coords to any player
	    break;
	}
	EntityItem entityitem = new EntityItem(herobrine.world, pos.x, pos.y, pos.z, new ItemStack(ModItems.AZURE_KEY));
	herobrine.world.spawnEntity(entityitem);
	herobrine.state = new StateCliffKey(herobrine);
    };

    public StateFirstEncounter(Herobrine herobrine)
    {
	super(herobrine);
	messager = new TimedMessager(INTRO_MESSAGES, INTRO_MESSAGE_TIMES, spawnHerobrine);
    }

    @Override
    public void update()
    {
	messager.Update(world, messageToPlayers);

	if (herobrineBoss != null)
	{
	    herobrine.bossInfo.setPercent(herobrineBoss.getHealth() / herobrineBoss.getMaxHealth());

	    // If the herobrine falls off, teleport it back
	    if (herobrineBoss.getDistanceSq(herobrine) > Math.pow(50, 2))
	    {
		herobrineBoss.fallDistance = 0; // Don't take any fall damage
		herobrineBoss.setLocationAndAngles(herobrine.posX, herobrine.posY + 1, herobrine.posZ - 5, herobrine.rotationYaw, herobrine.rotationPitch);
	    }

	    // Teleport the boss back in case it gets stuck somewhere
	    if (herobrine.getAttackTarget() == null)
	    {
		idleCounter++;
		if (idleCounter > 200)
		{
		    herobrineBoss.setLocationAndAngles(herobrine.posX, herobrine.posY + 1, herobrine.posZ - 5, herobrine.rotationYaw, herobrine.rotationPitch);
		    idleCounter = 0;
		}
	    }

	    // When herobrine is defeated
	    if (herobrineBoss.getHealth() <= 0.0)
	    {
		messager = new TimedMessager(EXIT_MESSAGES, EXIT_MESSAGE_TIMES, dropKey);
		herobrine.bossInfo.setPercent(1);
		herobrineBoss = null;
	    }
	}
    }

    @Override
    public void leftClick(Herobrine herobrine)
    {
	if (!this.leftClickMessage)
	{
	    messageToPlayers.accept("herobrine_8");
	    leftClickMessage = true;
	}
	super.leftClick(herobrine);
    }

    @Override
    public String getNbtString()
    {
	return "first_encounter";
    }
}
