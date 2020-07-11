package com.barribob.MaelstromMod.entity.particleSpawners;

import com.barribob.MaelstromMod.entity.util.EntityParticleSpawner;
import com.barribob.MaelstromMod.items.ISweepAttackParticles;
import com.barribob.MaelstromMod.util.ModRandom;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ParticleSpawnerSwordSwing extends EntityParticleSpawner {
    public ParticleSpawnerSwordSwing(World worldIn) {
        super(worldIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    protected void spawnParticles() {
        if (Minecraft.getMinecraft().player.getHeldItemMainhand().getItem() instanceof ISweepAttackParticles) {
            ISweepAttackParticles particleSword = (ISweepAttackParticles) Minecraft.getMinecraft().player.getHeldItemMainhand().getItem();
            for (int i = 0; i < 5; i++) {
                ParticleManager.spawnEffect(world, new Vec3d(posX, posY + 1, posZ).add(ModRandom.randVec().scale(particleSword.getSize())), particleSword.getColor());
            }
        }
    }
}
