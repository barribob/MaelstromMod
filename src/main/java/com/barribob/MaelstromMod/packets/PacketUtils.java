package com.barribob.MaelstromMod.packets;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleSweepAttack;
import net.minecraft.entity.player.EntityPlayer;
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
}
