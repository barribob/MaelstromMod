package com.barribob.MaelstromMod.util.handlers;

import com.barribob.MaelstromMod.particle.EffectParticle;
import com.barribob.MaelstromMod.particle.ModParticle;
import com.barribob.MaelstromMod.util.ModColors;
import com.barribob.MaelstromMod.util.ModRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.*;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;

/**
 * A place to handle all of the regularly spawned particles rather than copy and
 * pasting multiple times
 */
public class ParticleManager {
    /**
     * Spawns the little square purple particles
     *
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromParticle(World worldIn, Random rand, Vec3d pos) {
        Particle particle = new ParticleSuspendedTown.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0.0D, 0.0D, 0.0D);
        setMaelstromColor(particle);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Spawns purple spiral particles
     *
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromPotionParticle(World worldIn, Random rand, Vec3d pos, boolean isLight) {
        Particle particle = new ParticleSpell.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0.0D, 0.1D, 0.0D);

        if (isLight) {
            setMaelstromLightColor(particle);
        } else {
            setMaelstromColor(particle);
        }

        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Spawns purple explosion particles
     *
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromExplosion(World worldIn, Random rand, Vec3d pos) {
        Particle particle = new ParticleExplosionLarge.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, ModRandom.getFloat(0.05f), 0.0f, ModRandom.getFloat(0.05f));
        setMaelstromColor(particle);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnColoredExplosion(World worldIn, Vec3d pos, Vec3d baseColor) {
        Particle particle = new ParticleExplosionLarge.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, ModRandom.getFloat(0.05f), 0.0f, ModRandom.getFloat(0.05f));
        baseColor = ModColors.variateColor(baseColor, 0.2f);
        particle.setRBGColorF((float) baseColor.x, (float) baseColor.y, (float) baseColor.z);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Spawns large smoke particles
     *
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromLargeSmoke(World worldIn, Random rand, Vec3d pos) {
        Particle particle = new ParticleExplosion.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, ModRandom.getFloat(0.05f), 0.0f, ModRandom.getFloat(0.05f));
        setMaelstromColor(particle);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Spawns purple smoke
     *
     * @param worldIn
     * @param rand
     * @param pos
     */
    public static void spawnMaelstromSmoke(World worldIn, Random rand, Vec3d pos, boolean isLight) {
        Particle particle = new ParticleSmokeNormal.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0.0D, 0.01D, 0.0D);

        if (isLight) {
            setMaelstromLightColor(particle);
        } else {
            setMaelstromColor(particle);
        }

        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnColoredSmoke(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d vel) {
        Particle particle = new ParticleSmokeNormal.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);

        baseColor = ModColors.variateColor(baseColor, 0.2f);
        particle.setRBGColorF((float) baseColor.x, (float) baseColor.y, (float) baseColor.z);

        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnCustomSmoke(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 2, ModRandom.range(20, 30), false);
        particle.setParticleTextureRange(0, 7, 1);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnSwirl(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion, int age) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, age, true);
        particle.setParticleTextureRange(16, 8, 2);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnSwirl2(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, ModRandom.range(10, 20), true);
        particle.setParticleTextureRange(7, 9, 2);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnWisp(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, ModRandom.range(7, 15), false);
        particle.setParticleTextureRange(24, 8, 1);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnFluff(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, ModRandom.range(10, 20), false);
        particle.setParticleTextureRange(32, 10, 1);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnSplit(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, ModRandom.range(7, 20), false);
        particle.setParticleTextureRange(42, 6, 1);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnCircles(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, ModRandom.range(10, 20), true);
        particle.setParticleTextureRange(48, 7, 2);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnDust(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion, int age) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, age, true);
        particle.setParticleTextureRange(64, 14, 2);
        spawnParticleWithColor(particle, baseColor);
    }

    public static void spawnSmoke2(World worldIn, Vec3d pos, Vec3d baseColor, Vec3d motion) {
        ModParticle particle = new ModParticle(worldIn, pos, motion, 3, ModRandom.range(15, 25), true);
        particle.setParticleTextureRange(80, 23, 2);
        spawnParticleWithColor(particle, baseColor);
    }

    private static void spawnParticleWithColor(Particle particle, Vec3d baseColor) {
        baseColor = ModColors.variateColor(baseColor, 0.2f);
        particle.setRBGColorF((float) baseColor.x, (float) baseColor.y, (float) baseColor.z);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    /**
     * Spawns blackish-purplish flames
     */
    public static void spawnDarkFlames(World worldIn, Random rand, Vec3d pos) {
        spawnDarkFlames(worldIn, rand, pos, Vec3d.ZERO);
    }

    public static void spawnDarkFlames(World worldIn, Random rand, Vec3d pos, Vec3d vel) {
        Particle particle = new ParticleFlame.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);

        float f = ModRandom.getFloat(0.1f);
        particle.setRBGColorF(0.1f + f, 0, 0.1f + f);

        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnColoredFire(World worldIn, Random rand, Vec3d pos, Vec3d color) {
        Particle particle = new ParticleFlame.Factory().createParticle(0, worldIn, pos.x, pos.y, pos.z, 0, 0, 0);
        color = ModColors.variateColor(color, 0.2f);
        particle.setRBGColorF((float) color.x, (float) color.y, (float) color.z);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnEffect(World world, Vec3d pos, Vec3d baseColor) {
        Particle particle = new EffectParticle.Factory().createParticle(0, world, pos.x, pos.y, pos.z, 0, 0, 0);
        baseColor = ModColors.variateColor(baseColor, 0.2f);
        particle.setRBGColorF((float) baseColor.x, (float) baseColor.y, (float) baseColor.z);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnBreak(World world, Vec3d pos, Item item, Vec3d vel) {
        Particle particle = new ParticleBreaking.Factory().createParticle(0, world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z, Item.getIdFromItem(item));
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnFirework(World world, Vec3d pos, Vec3d color) {
        spawnFirework(world, pos, color, Vec3d.ZERO);
    }

    public static void spawnFirework(World world, Vec3d pos, Vec3d color, Vec3d vel) {
        Particle particle = new ParticleFirework.Factory().createParticle(0, world, pos.x, pos.y, pos.z, vel.x, vel.y, vel.z);
        particle.setRBGColorF((float) color.x, (float) color.y, (float) color.z);
        Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnParticleSphere(World world, Vec3d pos, float radius) {
        for (int i = 0; i < 1000; i++) {
            Vec3d unit = new Vec3d(0, 1, 0);
            unit = unit.rotatePitch((float) (Math.PI * ModRandom.getFloat(1)));
            unit = unit.rotateYaw((float) (Math.PI * ModRandom.getFloat(1)));
            unit = unit.normalize().scale(radius);
            ParticleManager.spawnEffect(world, pos.add(unit), ModColors.MAELSTROM);
        }
        ParticleManager.spawnEffect(world, pos, ModColors.RED);
    }

    /**
     * Helper function to vary and unify the colors
     *
     * @param particle
     */
    private static void setMaelstromColor(Particle particle) {
        float f = ModRandom.getFloat(0.2f);
        particle.setRBGColorF(0.3f + f, 0.2f + f, 0.4f + f);
    }

    /**
     * Helper function to vary and unify the colors
     *
     * @param particle
     */
    private static void setMaelstromLightColor(Particle particle) {
        float f = ModRandom.getFloat(0.2f);
        particle.setRBGColorF(0.8f + f, 0.5f + f, 0.8f + f);
    }
}
