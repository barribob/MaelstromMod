package com.barribob.MaelstromMod.util.handlers;

import java.util.Random;

import com.barribob.MaelstromMod.util.ModRandom;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleExplosion;
import net.minecraft.client.particle.ParticleExplosionLarge;
import net.minecraft.client.particle.ParticleSmokeNormal;
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
	setMaelstromColor(particle);
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
	setMaelstromColor(particle);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
    
    /**
     * Spawns purple explosion particles
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromExplosion(World worldIn, Random rand, Vec3d pos)
    {
	Particle particle = new ParticleExplosionLarge.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, ModRandom.getFloat(0.1f), 0.0f, ModRandom.getFloat(0.1f));
	setMaelstromColor(particle);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
    
    /**
     * Spawns large smoke particles
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromLargeSmoke(World worldIn, Random rand, Vec3d pos)
    {
	Particle particle = new ParticleExplosion.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, ModRandom.getFloat(0.1f), 0.0f, ModRandom.getFloat(0.1f));
	setMaelstromColor(particle);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
    
    /**
     * Spawns purple smoke
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromSmoke(World worldIn, Random rand, Vec3d pos)
    {
	Particle particle = new ParticleSmokeNormal.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0.0D, 0.01D, 0.0D);
	setMaelstromColor(particle);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }
    
    /**
     * Helper function to vary and unify the colors
     * @param particle
     */
    private static void setMaelstromColor(Particle particle)
    {
	float f = ModRandom.getFloat(0.4f);
	particle.setRBGColorF(0.3f + f, 0.2f + f, 0.4f + f);
    }
}
