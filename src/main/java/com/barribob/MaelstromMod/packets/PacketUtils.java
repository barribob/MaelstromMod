package com.barribob.MaelstromMod.packets;

import com.barribob.MaelstromMod.sounds.DarkNexusWindSound;
import com.barribob.MaelstromMod.util.handlers.ParticleManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSweepAttack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class PacketUtils
{
    /**
     * Used to work around side errors
     */
    public static EntityPlayer getPlayer()
    {
	return Minecraft.getMinecraft().player;
    }

    public static World getWorld()
    {
	return Minecraft.getMinecraft().world;
    }

    public static void spawnSweepParticles(MessageModParticles message)
    {
	Particle particle = new ParticleSweepAttack.Factory().createParticle(0, Minecraft.getMinecraft().world, message.xCoord, message.yCoord, message.zCoord,
		message.xOffset, message.yOffset, message.zOffset);
	particle.setRBGColorF(message.particleArguments[0], message.particleArguments[1], message.particleArguments[2]);
	Minecraft.getMinecraft().effectRenderer.addEffect(particle);
    }

    public static void spawnEffect(MessageModParticles message)
    {
	ParticleManager.spawnEffect(Minecraft.getMinecraft().world, new Vec3d(message.xCoord, message.yCoord, message.zCoord), new Vec3d(message.particleArguments[0], message.particleArguments[1], message.particleArguments[2]));
    }

    public static void playDarkNexusWindSound() {
	EntityPlayer player = PacketUtils.getPlayer();
	Minecraft.getMinecraft().getSoundHandler().playSound(new DarkNexusWindSound((EntityPlayerSP) player));
    }
}
