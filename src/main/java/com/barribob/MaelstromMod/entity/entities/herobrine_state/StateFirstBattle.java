package com.barribob.MaelstromMod.entity.entities.herobrine_state;

import com.barribob.MaelstromMod.entity.entities.EntityHerobrineOne;
import com.barribob.MaelstromMod.entity.entities.Herobrine;
import com.barribob.MaelstromMod.util.TimedMessager;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;

public class StateFirstBattle extends HerobrineState {
    private static final String[] EXIT_MESSAGES = {"herobrine_battle_2", "herobrine_battle_3", "herobrine_battle_4", "herobrine_battle_5", "herobrine_battle_6", ""};
    private static final int[] EXIT_MESSAGE_TIMES = {100, 200, 300, 400, 500, 501};
    private EntityHerobrineOne herobrineBoss;
    private TimedMessager messager;
    private boolean leftClickMessage = false;
    private int idleCounter;

    public StateFirstBattle(Herobrine herobrine) {
        super(herobrine);
        messager = new TimedMessager(new String[]{"herobrine_battle_0", "herobrine_battle_1", ""}, new int[]{60, 120, 121}, (s) -> {
            herobrine.playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
            spawnHerobrine();
        });
    }

    private void spawnHerobrine() {
        herobrine.setInvisible(true);
        herobrineBoss = new EntityHerobrineOne(world);
        herobrineBoss.setLocationAndAngles(herobrine.posX, herobrine.posY, herobrine.posZ - 5, herobrine.rotationYaw, herobrine.rotationPitch);
        herobrineBoss.setRotationYawHead(herobrine.rotationYawHead);
        if (!world.isRemote) {
            herobrineBoss.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(herobrineBoss)), (IEntityLivingData) null);
            world.spawnEntity(herobrineBoss);
        }
    }

    ;

    @Override
    public void update() {
        messager.Update(world, messageToPlayers);

        if (herobrineBoss != null) {
            herobrine.bossInfo.setPercent(herobrineBoss.getHealth() / herobrineBoss.getMaxHealth());

            // If the herobrine falls off, teleport it back
            if (herobrineBoss.getDistanceSq(herobrine) > Math.pow(50, 2)) {
                herobrineBoss.fallDistance = 0; // Don't take any fall damage
                herobrineBoss.setLocationAndAngles(herobrine.posX, herobrine.posY + 1, herobrine.posZ - 5, herobrine.rotationYaw, herobrine.rotationPitch);
            }

            // Teleport the boss back in case it gets stuck somewhere
            if (herobrine.getAttackTarget() == null) {
                idleCounter++;
                if (idleCounter > 200) {
                    herobrineBoss.setLocationAndAngles(herobrine.posX, herobrine.posY + 1, herobrine.posZ - 5, herobrine.rotationYaw, herobrine.rotationPitch);
                    idleCounter = 0;
                }
            }

            // When herobrine is defeated
            if (herobrineBoss.getHealth() <= 0.0) {
                herobrine.teleportOutside();
                herobrine.setInvisible(false);
                messager = new TimedMessager(EXIT_MESSAGES, EXIT_MESSAGE_TIMES, (s) -> {
                    herobrine.state = new StateCliffKey(herobrine);
                });
                herobrine.bossInfo.setPercent(1);
                herobrineBoss = null;
            }
        }
    }

    @Override
    public void leftClick(Herobrine herobrine) {
        if (!this.leftClickMessage && herobrineBoss == null) {
            messageToPlayers.accept("herobrine_battle_7");
            leftClickMessage = true;
        }
        super.leftClick(herobrine);
    }

    @Override
    public String getNbtString() {
        return "first_encounter";
    }
}
