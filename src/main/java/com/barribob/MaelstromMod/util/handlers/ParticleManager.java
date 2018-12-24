package com.barribob.MaelstromMod.util.handlers;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSpell;
import net.minecraft.client.particle.ParticleSuspendedTown;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

/**
 * 
 * A place to handle all of the regularly spawned particles rather than copy and pasting multiple times
 *
 */
public class ParticleManager
{
    /**
     * Spawns the little square purple particles
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromParticle(World worldIn, Random rand, Vec3d pos)
    {
	Particle particle = new ParticleSuspendedTown.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
	particle.setRBGColorF(0.5f, 0.3f, 0.5f);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Spawns purple spiral particles
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromPotionParticle(World worldIn, Random rand, Vec3d pos)
    {
	Particle particle = new ParticleSpell.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0.0D, 0.1D, 0.0D);
	particle.setRBGColorF(0.5f, 0.3f, 0.5f);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
}
