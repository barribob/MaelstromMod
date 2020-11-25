package com.barribob.MaelstromMod.entity.action;

import com.barribob.MaelstromMod.Main;
import com.barribob.MaelstromMod.entity.entities.EntityLeveledMob;
import com.barribob.MaelstromMod.packets.MessageModParticles;
import com.barribob.MaelstromMod.particle.EnumModParticles;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.ModUtils;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;

public class ActionAerialTeleport implements IAction {
    Vec3d teleportColor;

    public ActionAerialTeleport(Vec3d teleportColor) {
        this.teleportColor = teleportColor;
    }

    @Override
    public void performAction(EntityLeveledMob actor, EntityLivingBase target) {
        for(int i = 0; i < 50; i++) {
            Vec3d pos = ModRandom.randVec().normalize().scale(12)
                    .add(target.getPositionVector());

            boolean canSee = actor.world.rayTraceBlocks(target.getPositionEyes(1), pos, false, true, false) == null;
            Vec3d prevPos = actor.getPositionVector();
            if(canSee && ModUtils.attemptTeleport(pos, actor)){
                ModUtils.lineCallback(prevPos, pos, 50, (particlePos, j) ->
                        Main.network.sendToAllTracking(new MessageModParticles(EnumModParticles.EFFECT, particlePos, Vec3d.ZERO, teleportColor), actor));
                actor.world.setEntityState(actor, ModUtils.SECOND_PARTICLE_BYTE);
                break;
            }
        }
    }
}
